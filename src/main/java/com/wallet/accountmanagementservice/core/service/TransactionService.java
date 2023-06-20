package com.wallet.accountmanagementservice.core.service;

import com.wallet.accountmanagementservice.core.domain.AccountDomain;
import com.wallet.accountmanagementservice.core.domain.TransactionDomain;

public interface TransactionService {
    AccountDomain executeAccountTransaction(TransactionDomain toTransactionDomain);
}
