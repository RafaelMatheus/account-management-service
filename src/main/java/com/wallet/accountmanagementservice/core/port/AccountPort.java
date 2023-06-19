package com.wallet.accountmanagementservice.core.port;

import com.wallet.accountmanagementservice.core.domain.AccountDomain;

public interface AccountPort {
    AccountDomain save(AccountDomain accountDomain);

    AccountDomain findByAccountNumber(String accountNumber);
}
