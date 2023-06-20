package com.wallet.accountmanagementservice.core.service.impl;

import com.wallet.accountmanagementservice.core.domain.AccountDomain;
import com.wallet.accountmanagementservice.core.exception.AccountNotFoundException;
import com.wallet.accountmanagementservice.core.port.AccountPort;
import com.wallet.accountmanagementservice.core.service.AccountService;

import java.math.BigDecimal;
import java.util.UUID;

public class AccountServiceImpl implements AccountService {
    private final AccountPort port;

    public AccountServiceImpl(AccountPort port) {
        this.port = port;
    }

    @Override
    public AccountDomain generateAccount(AccountDomain accountDomain) {
        accountDomain.setAccountNumber(UUID.randomUUID().toString());
        accountDomain.setBalance(BigDecimal.ZERO);
        return port.save(accountDomain);
    }

    @Override
    public AccountDomain getAccountInformation(String accountNumber) {
        return port.findByAccountNumber(accountNumber)
                .orElseThrow(AccountNotFoundException::new);
    }
}
