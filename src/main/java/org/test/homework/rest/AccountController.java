package org.test.homework.rest;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.test.homework.domain.AccountOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.test.homework.service.AccountService;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(value="/deposit", method=POST)
    public AccountOperation deposit(@RequestParam(value="personId") String personId,
                                    @RequestParam(value="name") String name,
                                    @RequestParam(value="surname") String surname,
                                    @RequestParam(value="amount") BigDecimal amount,
                                    HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        AccountOperation deposit = new AccountOperation(personId, name, surname, amount);
        return accountService.createAccountOperation(deposit, ip);
    }

    @RequestMapping(value="/withdrawal", method=POST)
    public AccountOperation withdrawal(@RequestParam(value="personId") String personId,
                                       @RequestParam(value="amount") BigDecimal amount,
                                       HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        AccountOperation withdrawal = new AccountOperation(personId, amount.negate());
        return accountService.createAccountOperation(withdrawal, ip);
    }

    @RequestMapping(value="/operations", method=GET)
    public List<AccountOperation> getAllOperations() {
        return accountService.getAllOperations();
    }  

    @RequestMapping(value="/operations/{personId}", method=GET)
    public List<AccountOperation> getOperationsByUser(@PathVariable("personId") String personId) {
        return accountService.getOperationsByUser(personId);
    }
}