package com.wallet.accountmanagementservice.adapter.handler.dto;

import org.springframework.http.HttpStatus;

public record CustomErrorResponse (
     String path,
     String message,
     HttpStatus status
){
}
