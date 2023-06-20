package com.wallet.accountmanagementservice.core.service;

import com.wallet.accountmanagementservice.core.domain.AccountDomain;

public interface AccountService {
    AccountDomain generateAccount(AccountDomain accountDomain);

    AccountDomain getAccountInformation(String accountNumber);
}
