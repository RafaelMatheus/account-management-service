package com.wallet.accountmanagementservice.adapter.handler.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CustomErrorResponse(
        String path,
        String message,
        HttpStatus status,
        List<FieldErrorResponse> fieldError
) {
}
