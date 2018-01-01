package org.test.homework.service;

import java.math.BigDecimal;

public interface BalanceManager {

    BigDecimal get(String personId);

    void add(String personId, BigDecimal amount);
}
