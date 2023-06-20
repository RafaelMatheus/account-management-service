package com.wallet.accountmanagementservice.core.exception;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(){
        super("Sorry you do not balance for this transaction.");
    }
}
