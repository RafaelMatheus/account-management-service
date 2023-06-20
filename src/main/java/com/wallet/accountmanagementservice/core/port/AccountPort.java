package com.wallet.accountmanagementservice.core.port;

import com.wallet.accountmanagementservice.core.domain.AccountDomain;

import java.util.Optional;

public interface AccountPort {
    AccountDomain save(AccountDomain accountDomain);

    Optional<AccountDomain> findByAccountNumber(String accountNumber);
}
