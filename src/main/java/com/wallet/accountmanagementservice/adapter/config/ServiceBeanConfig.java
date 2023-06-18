package com.wallet.accountmanagementservice.adapter.config;

import com.wallet.accountmanagementservice.adapter.repository.AccountRepository;
import com.wallet.accountmanagementservice.core.port.AccountPort;
import com.wallet.accountmanagementservice.core.port.RabbitMqPort;
import com.wallet.accountmanagementservice.core.port.impl.AccountPortRepository;
import com.wallet.accountmanagementservice.core.port.impl.RabbitMqPortImpl;
import com.wallet.accountmanagementservice.core.service.AccountService;
import com.wallet.accountmanagementservice.core.service.impl.AccountServiceImpl;
import com.wallet.accountmanagementservice.core.strategy.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
public class ServiceBeanConfig {
    @Bean
    public AccountService accountService(AccountPort port, TransactionFactory factory) {
        return new AccountServiceImpl(port, factory);
    }

    @Bean
    public TransactionFactory factory(Set<ProcessTransactionStrategy> processTransactionStrategies) {
        return new TransactionFactory(processTransactionStrategies);
    }

    @Bean
    public AccountPort accountPort(AccountRepository repository) {
        return new AccountPortRepository(repository);
    }

    @Bean
    public RabbitMqPort rabbitMqPort(RabbitTemplate rabbitTemplate) {
        return new RabbitMqPortImpl(rabbitTemplate);
    }

    @Bean
    public Set<ProcessTransactionStrategy> strategies(AccountPort port, RabbitMqPort rabbitMqPort, PropertiesConfiguration propertiesConfiguration) {
        var depositStrategy = new DepositStrategy(port, rabbitMqPort, propertiesConfiguration);
        var withdrawStrategy = new WithdrawStrategy(port, rabbitMqPort, propertiesConfiguration);
        var transferStrategy = new TransferStrategy(port, rabbitMqPort, propertiesConfiguration);
        var paymentStrategy = new PaymentStrategy(port, rabbitMqPort, propertiesConfiguration);
        return Set.of(depositStrategy, withdrawStrategy, transferStrategy, paymentStrategy);
    }
}
