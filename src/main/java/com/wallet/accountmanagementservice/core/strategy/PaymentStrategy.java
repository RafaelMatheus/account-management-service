package com.wallet.accountmanagementservice.core.strategy;

import com.wallet.accountmanagementservice.adapter.config.PropertiesConfiguration;
import com.wallet.accountmanagementservice.core.domain.AccountDomain;
import com.wallet.accountmanagementservice.core.domain.PaymentRabbitMqDomain;
import com.wallet.accountmanagementservice.core.domain.TransactionDomain;
import com.wallet.accountmanagementservice.core.domain.TransactionRabbitMqDomain;
import com.wallet.accountmanagementservice.core.enumerated.TransactionType;
import com.wallet.accountmanagementservice.core.exception.IinsufficientBalanceException;
import com.wallet.accountmanagementservice.core.port.AccountPort;
import com.wallet.accountmanagementservice.core.port.RabbitMqPort;

import java.math.BigDecimal;

public class PaymentStrategy extends AbstractStrategy {
    public PaymentStrategy(AccountPort port, RabbitMqPort rabbitMqPort, PropertiesConfiguration propertiesConfiguration) {
        super(port, rabbitMqPort, propertiesConfiguration);
    }

    @Override
    public AccountDomain process(TransactionDomain transactionDomain) {
        var account = port.findByAccountNumber(transactionDomain.originAccountNumber());

        if (!hasSufficientBalance(account, transactionDomain.value())) {
            throw new IinsufficientBalanceException();
        }

        account.setBalance(account.getBalance().min(transactionDomain.value()));

        var toResponse = port.save(account);
        var message = toPaymentRabbitDomainWithdraw(transactionDomain, account.getHolderTaxId());

        sendToQueuePayment(message);
        return toResponse;

    }

    @Override
    public TransactionType getType() {
        return TransactionType.PAYMENT;
    }

    private PaymentRabbitMqDomain toPaymentRabbitDomainWithdraw(TransactionDomain transactionDomain, String taxId) {
        return new PaymentRabbitMqDomain(TransactionType.PAYMENT, transactionDomain.originAccountNumber(),
                transactionDomain.value(), transactionDomain.barcode(), taxId);
    }
}
