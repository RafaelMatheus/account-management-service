package com.wallet.accountmanagementservice.core.strategy;

import com.wallet.accountmanagementservice.adapter.config.PropertiesConfiguration;
import com.wallet.accountmanagementservice.core.domain.AccountDomain;
import com.wallet.accountmanagementservice.core.domain.TransactionDomain;
import com.wallet.accountmanagementservice.core.domain.TransactionRabbitMqDomain;
import com.wallet.accountmanagementservice.core.enumerated.TransactionType;
import com.wallet.accountmanagementservice.core.exception.IinsufficientBalanceException;
import com.wallet.accountmanagementservice.core.port.AccountPort;
import com.wallet.accountmanagementservice.core.port.RabbitMqPort;

import java.math.BigDecimal;

public class TransferStrategy extends AbstractStrategy {
    public TransferStrategy(AccountPort port, RabbitMqPort rabbitMqPort, PropertiesConfiguration propertiesConfiguration) {
        super(port, rabbitMqPort, propertiesConfiguration);
    }

    @Override
    public AccountDomain process(TransactionDomain transactionDomain) {
        var originAccount = port.findByAccountNumber(transactionDomain.originAccountNumber());
        var destinationAccount = port.findByAccountNumber(transactionDomain.destinationAccountNumber());

        if (!hasSufficientBalance(originAccount, transactionDomain.value())) {
            throw new IinsufficientBalanceException();
        }

        destinationAccount.setBalance(destinationAccount.getBalance().add(transactionDomain.value()));

        var message = toTransactionRabbitDomainDeposit(originAccount, destinationAccount, transactionDomain.value());
        sendToQueueTransaction(message);

        return port.save(destinationAccount);
    }

    @Override
    public TransactionType getType() {
        return TransactionType.TRANSFER;
    }

    private TransactionRabbitMqDomain toTransactionRabbitDomainDeposit(AccountDomain originAccount, AccountDomain destinationAccount, BigDecimal value) {
        return new TransactionRabbitMqDomain(TransactionType.TRANSFER, originAccount.getAccountNumber(), destinationAccount.getAccountNumber(), value);
    }
}
