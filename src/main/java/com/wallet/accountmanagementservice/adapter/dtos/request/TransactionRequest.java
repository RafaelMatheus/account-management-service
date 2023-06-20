package com.wallet.accountmanagementservice.adapter.dtos.request;

import com.wallet.accountmanagementservice.core.enumerated.TransactionType;

import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public record TransactionRequest(
        String destinationAccountNumber,
        String originAccountNumber,
        @Positive
        BigDecimal value,
        TransactionType type,
        String barcode
) {
}
