package com.wallet.accountmanagementservice.core.port.impl;

import com.wallet.accountmanagementservice.adapter.repository.AccountRepository;
import com.wallet.accountmanagementservice.core.domain.AccountDomain;
import com.wallet.accountmanagementservice.core.helper.Mapper;
import com.wallet.accountmanagementservice.core.port.AccountPort;

import java.util.Optional;

public class AccountPortRepository implements AccountPort {
    private final AccountRepository accountRepository;

    public AccountPortRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDomain save(AccountDomain accountDomain) {
        var saved = accountRepository.save(Mapper.toEntity(accountDomain));
        return Mapper.toDomain(saved);
    }

    @Override
    public Optional<AccountDomain> findByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .map(Mapper::toDomain);
    }
}
