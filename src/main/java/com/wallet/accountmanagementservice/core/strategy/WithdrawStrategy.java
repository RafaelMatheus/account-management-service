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

public class WithdrawStrategy extends AbstractStrategy {
    private final AccountService accountService;

    public WithdrawStrategy(AccountPort port, RabbitMqPort rabbitMqPort, PropertiesConfiguration propertiesConfiguration, AccountService accountService) {
        super(port, rabbitMqPort, propertiesConfiguration);
        this.accountService = accountService;
    }

    @Override
    public AccountDomain process(TransactionDomain transactionDomain) {
        var account = accountService.getAccountInformation(transactionDomain.originAccountNumber());

        if (!hasSufficientBalance(account, transactionDomain.value())) {
            throw new InsufficientBalanceException();
        }

        account.setBalance(account.getBalance().subtract(transactionDomain.value()));

        var toResponse = port.save(account);
        var message = toTransactionRabbitDomainWithdraw(account, transactionDomain.value());

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
