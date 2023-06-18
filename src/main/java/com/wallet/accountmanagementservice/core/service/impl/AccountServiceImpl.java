package com.wallet.accountmanagementservice.core.service.impl;

import com.wallet.accountmanagementservice.core.domain.AccountDomain;
import com.wallet.accountmanagementservice.core.domain.TransactionDomain;
import com.wallet.accountmanagementservice.core.port.AccountPort;
import com.wallet.accountmanagementservice.core.service.AccountService;
import com.wallet.accountmanagementservice.core.strategy.TransactionFactory;

import java.math.BigDecimal;
import java.util.UUID;

public class AccountServiceImpl implements AccountService {
    private final AccountPort port;
    private final TransactionFactory factory;

    public AccountServiceImpl(AccountPort port, TransactionFactory factory) {
        this.port = port;
        this.factory = factory;
    }

    @Override
    public AccountDomain generateAccount(AccountDomain accountDomain) {
        accountDomain.setAccountNumber(UUID.randomUUID().toString());
        accountDomain.setBalance(BigDecimal.ZERO);
        return port.save(accountDomain);
    }

    @Override
    public AccountDomain getAccountInformation(String accountNumber) {
        return port.findByAccountNumber(accountNumber);
    }

    @Override
    public AccountDomain executeAccountTransaction(TransactionDomain toTransactionDomain) {
        return factory.get(toTransactionDomain.type()).process(toTransactionDomain);
    }
}
