package com.wallet.accountmanagementservice.core.strategy;

import com.wallet.accountmanagementservice.adapter.config.PropertiesConfiguration;
import com.wallet.accountmanagementservice.core.domain.AccountDomain;
import com.wallet.accountmanagementservice.core.domain.TransactionDomain;
import com.wallet.accountmanagementservice.core.domain.TransactionRabbitMqDomain;
import com.wallet.accountmanagementservice.core.enumerated.TransactionType;
import com.wallet.accountmanagementservice.core.exception.InsufficientBalanceException;
import com.wallet.accountmanagementservice.core.port.AccountPort;
import com.wallet.accountmanagementservice.core.port.RabbitMqPort;
import com.wallet.accountmanagementservice.core.service.AccountService;

import java.math.BigDecimal;

public class TransferStrategy extends AbstractStrategy {
    private final AccountService accountService;

    public TransferStrategy(AccountPort port, RabbitMqPort rabbitMqPort, PropertiesConfiguration propertiesConfiguration, AccountService accountService) {
        super(port, rabbitMqPort, propertiesConfiguration);
        this.accountService = accountService;
    }

    @Override
    public AccountDomain process(TransactionDomain transactionDomain) {

        var originAccount = accountService.getAccountInformation(transactionDomain.originAccountNumber());

        var destinationAccount = accountService.getAccountInformation(transactionDomain.destinationAccountNumber());

        if (!hasSufficientBalance(originAccount, transactionDomain.value())) {
            throw new InsufficientBalanceException();
        }

        originAccount.setBalance(originAccount.getBalance().subtract(transactionDomain.value()));
        destinationAccount.setBalance(destinationAccount.getBalance().add(transactionDomain.value()));

        var message = toTransactionRabbitDomainDeposit(originAccount, destinationAccount, transactionDomain.value());
        sendToQueueTransaction(message);

        port.save(originAccount);
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
