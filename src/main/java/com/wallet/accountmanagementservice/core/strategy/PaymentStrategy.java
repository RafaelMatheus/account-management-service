package com.wallet.accountmanagementservice.core.strategy;

import com.wallet.accountmanagementservice.adapter.config.PropertiesConfiguration;
import com.wallet.accountmanagementservice.core.domain.AccountDomain;
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
    public AccountDomain process(String destinationAccountNumber, String originAccountNumber, BigDecimal value) {
        var account = port.findByAccountNumber(originAccountNumber);

        if (!hasSufficientBalance(account, value)) {
            throw new IinsufficientBalanceException();
        }

        account.setBalance(account.getBalance().min(value));

        var toResponse = port.save(account);
        var message = toTransactionRabbitDomainWithdraw(account, value);

        sendToQueueTransaction(message);
        return toResponse;

    }

    @Override
    public TransactionType getType() {
        return TransactionType.WITHDRAW;
    }

    private TransactionRabbitMqDomain toTransactionRabbitDomainWithdraw(AccountDomain originAccount, BigDecimal value) {
        return new TransactionRabbitMqDomain(TransactionType.WITHDRAW, originAccount.getAccountNumber(), null, value);
    }
}
