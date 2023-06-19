package com.wallet.accountmanagementservice.core.exception;

public class AccountException extends RuntimeException {

    public AccountException(Exception e) {
        super(e);
    }
}
