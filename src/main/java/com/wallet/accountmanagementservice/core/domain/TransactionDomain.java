package com.wallet.accountmanagementservice.core.domain;

import com.wallet.accountmanagementservice.core.enumerated.TransactionType;

import java.math.BigDecimal;

public record TransactionDomain(
        String destinationAccountNumber,
        String originAccountNumber,
        BigDecimal value,
        TransactionType type,
        String barcode
) {
}
