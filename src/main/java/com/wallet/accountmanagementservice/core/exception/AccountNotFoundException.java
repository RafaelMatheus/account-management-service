package com.wallet.accountmanagementservice.core.exception;

public class AccountNotFoundException extends RuntimeException{
    public AccountNotFoundException(){
        super("We can not find your account, please try again other moment.");
    }
}
