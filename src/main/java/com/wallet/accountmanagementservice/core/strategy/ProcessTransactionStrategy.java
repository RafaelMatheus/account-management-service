package com.wallet.accountmanagementservice.core.strategy;

import com.wallet.accountmanagementservice.core.domain.AccountDomain;
import com.wallet.accountmanagementservice.core.enumerated.TransactionType;

import java.math.BigDecimal;

public interface ProcessTransactionStrategy {
    AccountDomain process(String destinationAccountNumber, String originAccountNumber, BigDecimal value);

    TransactionType getType();
}
