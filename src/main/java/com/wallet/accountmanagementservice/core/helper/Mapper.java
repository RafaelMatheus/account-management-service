package com.wallet.accountmanagementservice.core.helper;

import com.wallet.accountmanagementservice.adapter.dtos.request.AccountRequest;
import com.wallet.accountmanagementservice.adapter.dtos.request.TransactionRequest;
import com.wallet.accountmanagementservice.adapter.dtos.response.AccountResponse;
import com.wallet.accountmanagementservice.adapter.entity.AccountEntity;
import com.wallet.accountmanagementservice.core.domain.AccountDomain;
import com.wallet.accountmanagementservice.core.domain.TransactionDomain;

public class Mapper {
    private Mapper() {
    }

    public static AccountEntity toEntity(AccountDomain domain) {
        var accountEntity = new AccountEntity();

        accountEntity.setHolderName(domain.getHolderName());
        accountEntity.setAccountNumber(domain.getAccountNumber());
        accountEntity.setHolderTaxId(domain.getHolderTaxId());
        accountEntity.setBalance(domain.getBalance());
        accountEntity.setId(domain.getId());
        accountEntity.setPhoneNumber(domain.getPhoneNumber());
        return accountEntity;
    }

    public static AccountDomain toDomain(AccountEntity entity) {
        var accountDomain = new AccountDomain();

        accountDomain.setHolderName(entity.getHolderName());
        accountDomain.setHolderTaxId(entity.getHolderTaxId());
        accountDomain.setBalance(entity.getBalance());
        accountDomain.setId(entity.getId());
        accountDomain.setAccountNumber(entity.getAccountNumber());
        accountDomain.setPhoneNumber(entity.getPhoneNumber());

        return accountDomain;
    }

    public static AccountDomain toDomain(AccountRequest request) {
        var accountDomain = new AccountDomain();

        accountDomain.setHolderName(request.holderName());
        accountDomain.setHolderTaxId(request.holderTaxId());
        accountDomain.setPhoneNumber(request.phoneNumber());
        return accountDomain;
    }

    public static AccountResponse toResponse(AccountDomain accountDomain) {
        return new AccountResponse(accountDomain.getHolderTaxId(), accountDomain.getHolderName(),
                accountDomain.getPhoneNumber(), accountDomain.getBalance(),
                accountDomain.getAccountNumber());
    }

    public static TransactionDomain toTransactionDomain(TransactionRequest transactionRequest) {
        return new TransactionDomain(transactionRequest.destinationAccountNumber(),
                transactionRequest.originAccountNumber(),
                transactionRequest.value(),
                transactionRequest.type(),
                transactionRequest.barcode());
    }
}
