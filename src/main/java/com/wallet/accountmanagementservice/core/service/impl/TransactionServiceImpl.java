package com.wallet.accountmanagementservice.core.service.impl;

import com.wallet.accountmanagementservice.core.domain.AccountDomain;
import com.wallet.accountmanagementservice.core.domain.TransactionDomain;
import com.wallet.accountmanagementservice.core.service.TransactionService;
import com.wallet.accountmanagementservice.core.strategy.TransactionFactory;

public class TransactionServiceImpl implements TransactionService {
    private final TransactionFactory factory;

    public TransactionServiceImpl(TransactionFactory factory1) {
        this.factory = factory1;
    }

    @Override
    public AccountDomain executeAccountTransaction(TransactionDomain toTransactionDomain) {
        return factory.get(toTransactionDomain.type()).process(toTransactionDomain);
    }
}
