package com.wallet.accountmanagementservice.adapter.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitTransactionConfig {
    private final PropertiesConfiguration env;

    public RabbitTransactionConfig(PropertiesConfiguration env) {
        this.env = env;
    }

    @Bean
    public DirectExchange exchange() {
        return ExchangeBuilder.directExchange(env.getTransaction().getRabbit().getExchangeName())
                .build();
    }

    @Bean
    public DirectExchange deadLetterExchange() {
        return ExchangeBuilder
                .directExchange(env.getTransaction().getRabbit().getDeadLetterExchange())
                .build();
    }

    @Bean
    public Queue transactionQueue() {
        return QueueBuilder.durable(env.getTransaction().getRabbit().getQueueName())
                .deadLetterExchange(env.getTransaction().getRabbit().getDeadLetterExchange())
                .deadLetterRoutingKey(env.getTransaction().getRabbit().getDeadLetterRoutingKey())
                .build();
    }

    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable(env.getTransaction().getRabbit().getDeadLetterQueueName())
                .build();
    }

    @Bean
    public Binding deadLetterBiding() {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with(env.getTransaction().getRabbit().getDeadLetterRoutingKey());
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(transactionQueue()).to(exchange()).with(env.getTransaction().getRabbit().getRoutingKey());
    }


    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }
}
