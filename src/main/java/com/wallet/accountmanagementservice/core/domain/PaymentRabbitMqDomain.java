package com.wallet.accountmanagementservice.core.domain;

import com.wallet.accountmanagementservice.core.enumerated.TransactionType;

import java.math.BigDecimal;

public class PaymentRabbitMqDomain {
    private TransactionType transactionType;
    private String originAccount;
    private BigDecimal amount;
    private String barcode;
    private String taxId;

    public PaymentRabbitMqDomain(TransactionType transactionType, String originAccount, BigDecimal amount, String barcode, String taxId) {
        this.transactionType = transactionType;
        this.originAccount = originAccount;
        this.amount = amount;
        this.barcode = barcode;
        this.taxId = taxId;
    }

    public PaymentRabbitMqDomain() {
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public String getOriginAccount() {
        return originAccount;
    }

    public void setOriginAccount(String originAccount) {
        this.originAccount = originAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }
}
