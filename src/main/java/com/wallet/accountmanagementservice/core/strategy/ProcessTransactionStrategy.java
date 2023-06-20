package com.wallet.accountmanagementservice.core.strategy;

import com.wallet.accountmanagementservice.core.domain.AccountDomain;
import com.wallet.accountmanagementservice.core.domain.TransactionDomain;
import com.wallet.accountmanagementservice.core.enumerated.TransactionType;

public interface ProcessTransactionStrategy {
    AccountDomain process(TransactionDomain transactionDomain);

    TransactionType getType();
}
