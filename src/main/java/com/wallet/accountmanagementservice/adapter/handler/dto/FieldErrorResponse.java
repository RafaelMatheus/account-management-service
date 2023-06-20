package com.wallet.accountmanagementservice.adapter.handler.dto;

public record FieldErrorResponse(
        String field,
        String message
) {
}
