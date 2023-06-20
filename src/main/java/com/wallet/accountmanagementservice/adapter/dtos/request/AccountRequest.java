package com.wallet.accountmanagementservice.adapter.dtos.request;

import javax.validation.constraints.NotEmpty;

public record AccountRequest(
        @NotEmpty
        String holderTaxId,
        @NotEmpty
        String holderName,
        @NotEmpty
        String phoneNumber
) {
}
