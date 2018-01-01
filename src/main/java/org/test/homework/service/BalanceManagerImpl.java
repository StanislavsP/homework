package org.test.homework.service;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static java.math.BigDecimal.ZERO;

@Component
public class BalanceManagerImpl implements BalanceManager {

    private final ConcurrentMap<String, BigDecimal> balances = new ConcurrentHashMap<>();

    @Override
    public BigDecimal get(String personId) {
        return balances.getOrDefault(personId, ZERO);
    }

    @Override
    public synchronized void add(String personId, BigDecimal amount) {
        BigDecimal currentBalance = get(personId);
        BigDecimal newBalance = currentBalance.add(amount);
        if (newBalance.compareTo(ZERO) < 0) {
            throw new IllegalStateException("Available amount " + currentBalance + " less then withdrawal amount " + amount + " for " + personId);
        }
        balances.put(personId, newBalance);
    }
}
