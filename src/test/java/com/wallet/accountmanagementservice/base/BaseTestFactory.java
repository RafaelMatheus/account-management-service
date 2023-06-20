package com.wallet.accountmanagementservice.base;

import com.wallet.accountmanagementservice.adapter.config.PropertiesConfiguration;
import com.wallet.accountmanagementservice.adapter.dtos.request.AccountRequest;
import com.wallet.accountmanagementservice.adapter.entity.AccountEntity;
import com.wallet.accountmanagementservice.core.domain.AccountDomain;

import java.math.BigDecimal;
import java.util.UUID;

public class BaseTestFactory {
    private BaseTestFactory() {
    }

    public static final String ID = UUID.randomUUID().toString();
    public static final String TAX_ID = "xpto";
    public static final String HOLDER_NAME = "rafae";
    public static final String PHONE_NUMBER = "93999999999";
    public static final BigDecimal BALANCE = BigDecimal.TEN;
    public static final String ACCOUNT_NUMBER = "112311223";
    public static final String ACCOUNT_NUMBER2 = "112311223321";

    public static AccountEntity getAccountEntity() {
        return new AccountEntity(ID, TAX_ID, HOLDER_NAME, PHONE_NUMBER, BALANCE, ACCOUNT_NUMBER, null);

    }

    public static AccountDomain getAccountDomain() {
        return new AccountDomain(ID, TAX_ID, HOLDER_NAME, PHONE_NUMBER, BALANCE, ACCOUNT_NUMBER);
    }

    public static AccountDomain getAccountDomain2() {
        return new AccountDomain(ID, TAX_ID, HOLDER_NAME, PHONE_NUMBER, BALANCE, ACCOUNT_NUMBER2);
    }

    public static AccountRequest getAccountRequest() {
        return new AccountRequest(TAX_ID, HOLDER_NAME, PHONE_NUMBER);
    }

    public static PropertiesConfiguration getPropertiesTransactionConfiguration() {
        var properties = new PropertiesConfiguration();
        var transaction = new PropertiesConfiguration.TransactionTypeConfig();
        transaction.setRabbit(new PropertiesConfiguration.RabbitConfig());
        properties.setTransaction(transaction);
        return properties;
    }

    public static PropertiesConfiguration getPropertiesPaymentConfiguration() {
        var properties = new PropertiesConfiguration();
        var transaction = new PropertiesConfiguration.TransactionTypeConfig();
        transaction.setRabbit(new PropertiesConfiguration.RabbitConfig());
        properties.setPayment(transaction);
        return properties;
    }
}
