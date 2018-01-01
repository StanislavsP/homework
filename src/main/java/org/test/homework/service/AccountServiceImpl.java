package org.test.homework.service;

import java.util.List;

import org.test.homework.domain.AccountOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.test.homework.repository.AccountOperationRepository;

@Service
public class AccountServiceImpl implements AccountService {

    private final CountryResolver countryResolver;
    private final RateLimiter rateLimiter;
    private final BalanceManager balanceManager;
    private final AccountOperationRepository repository;

    @Autowired
    public AccountServiceImpl(CountryResolver countryResolver,
                              RateLimiter rateLimiter,
                              BalanceManager balanceManager,
                              AccountOperationRepository repository) {
        this.countryResolver = countryResolver;
        this.rateLimiter = rateLimiter;
        this.balanceManager = balanceManager;
        this.repository = repository;
    }

    @Override
    public AccountOperation createAccountOperation(AccountOperation accountOperation, String ip) {
        String country = countryResolver.resolve(ip);
        accountOperation.setCountry(country);
        rateLimiter.check(country);
        balanceManager.add(accountOperation.getPersonId(), accountOperation.getAmount());
        return repository.save(accountOperation);
    }

    @Override
    public List<AccountOperation> getAllOperations() {
        return repository.findAllByOrderByCreatedAsc();
    }

    @Override
    public List<AccountOperation> getOperationsByUser(String personId) {
        return repository.findByPersonIdIgnoreCaseOrderByCreatedAsc(personId);
    }
}