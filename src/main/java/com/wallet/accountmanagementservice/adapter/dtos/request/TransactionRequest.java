package com.wallet.accountmanagementservice.adapter.dtos.request;

import com.wallet.accountmanagementservice.core.enumerated.TransactionType;

import java.math.BigDecimal;

public record TransactionRequest(
        String destinationAccountNumber,
        String originAccountNumber,
        BigDecimal value,
        TransactionType type
) {
}
