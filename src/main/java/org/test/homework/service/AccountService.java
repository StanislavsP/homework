package org.test.homework.service;

import org.test.homework.domain.AccountOperation;

import java.util.List;

public interface AccountService {

    AccountOperation createAccountOperation(AccountOperation accountOperation, String ip);
    
    List<AccountOperation> getAllOperations();

    List<AccountOperation> getOperationsByUser(String personId);
}