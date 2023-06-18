package com.wallet.accountmanagementservice.core.strategy;

import com.wallet.accountmanagementservice.adapter.config.PropertiesConfiguration;
import com.wallet.accountmanagementservice.core.domain.AccountDomain;
import com.wallet.accountmanagementservice.core.domain.TransactionDomain;
import com.wallet.accountmanagementservice.core.domain.TransactionRabbitMqDomain;
import com.wallet.accountmanagementservice.core.enumerated.TransactionType;
import com.wallet.accountmanagementservice.core.port.AccountPort;
import com.wallet.accountmanagementservice.core.port.RabbitMqPort;

import java.math.BigDecimal;

public class DepositStrategy extends AbstractStrategy {

    public DepositStrategy(AccountPort port, RabbitMqPort rabbitMqPort, PropertiesConfiguration propertiesConfiguration) {
        super(port, rabbitMqPort, propertiesConfiguration);
    }

    @Override
    public AccountDomain process(TransactionDomain transactionDomain) {
        var account = port.findByAccountNumber(transactionDomain.originAccountNumber());

        account.setBalance(account.getBalance().add(transactionDomain.value()));
        var message = toTransactionRabbitDomainDeposit(account, transactionDomain.value());
        sendToQueueTransaction(message);
        return port.save(account);
    }

    @Override
    public TransactionType getType() {
        return TransactionType.DEPOSIT;
    }

    private TransactionRabbitMqDomain toTransactionRabbitDomainDeposit(AccountDomain destinationAccount, BigDecimal value) {
        return new TransactionRabbitMqDomain(TransactionType.DEPOSIT, null, destinationAccount.getAccountNumber(), value);
    }
}
