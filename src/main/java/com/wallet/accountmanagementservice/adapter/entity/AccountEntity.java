package com.wallet.accountmanagementservice.adapter.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document("account")
public class AccountEntity {
    @Id
    private String id;
    private String holderTaxId;
    private String holderName;
    private String phoneNumber;
    private BigDecimal balance;
    private String accountNumber;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;

    public AccountEntity() {
        this.creationDate = LocalDateTime.now();
    }

    public AccountEntity(String id, String holderTaxId, String holderName, String phoneNumber, BigDecimal balance, String accountNumber, LocalDateTime creationDate) {
        this.id = id;
        this.holderTaxId = holderTaxId;
        this.holderName = holderName;
        this.phoneNumber = phoneNumber;
        this.balance = balance;
        this.accountNumber = accountNumber;
        this.creationDate = creationDate;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHolderTaxId() {
        return holderTaxId;
    }

    public void setHolderTaxId(String holderTaxId) {
        this.holderTaxId = holderTaxId;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }
}
