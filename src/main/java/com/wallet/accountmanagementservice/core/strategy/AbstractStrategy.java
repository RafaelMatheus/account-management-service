package com.wallet.accountmanagementservice.core.strategy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wallet.accountmanagementservice.adapter.config.PropertiesConfiguration;
import com.wallet.accountmanagementservice.core.domain.AccountDomain;
import com.wallet.accountmanagementservice.core.domain.PaymentRabbitMqDomain;
import com.wallet.accountmanagementservice.core.domain.TransactionRabbitMqDomain;
import com.wallet.accountmanagementservice.core.exception.AccountException;
import com.wallet.accountmanagementservice.core.port.AccountPort;
import com.wallet.accountmanagementservice.core.port.RabbitMqPort;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessagePropertiesBuilder;

import java.math.BigDecimal;

public abstract class AbstractStrategy implements ProcessTransactionStrategy {
    protected final AccountPort port;
    protected final RabbitMqPort rabbitMqPort;
    protected final PropertiesConfiguration propertiesConfiguration;

    protected AbstractStrategy(AccountPort port, RabbitMqPort rabbitMqPort, PropertiesConfiguration propertiesConfiguration) {
        this.port = port;
        this.rabbitMqPort = rabbitMqPort;
        this.propertiesConfiguration = propertiesConfiguration;
    }

    protected boolean hasSufficientBalance(AccountDomain accountDomain, BigDecimal value) {
        return accountDomain.getBalance().compareTo(value) >= 0;
    }

    protected Message renderMensagemFila(Object dto) throws JsonProcessingException {
        var mapperObj = new ObjectMapper();

        return MessageBuilder.withBody(mapperObj.writeValueAsBytes(dto))
                .andProperties(MessagePropertiesBuilder.newInstance().setContentType("application/json")
                        .setContentEncodingIfAbsent("UTF-8").build())
                .build();
    }

    protected void sendToQueueTransaction(TransactionRabbitMqDomain message) {
        try {
            rabbitMqPort.send(renderMensagemFila(message),
                    propertiesConfiguration.getTransaction().getRabbit().getRoutingKey(),
                    propertiesConfiguration.getTransaction().getRabbit().getExchangeName());
        } catch (JsonProcessingException e) {
            throw new AccountException(e);
        }
    }

    protected void sendToQueuePayment(PaymentRabbitMqDomain message) {
        try {
            rabbitMqPort.send(renderMensagemFila(message),
                    propertiesConfiguration.getPayment().getRabbit().getRoutingKey(),
                    propertiesConfiguration.getPayment().getRabbit().getExchangeName());
        } catch (JsonProcessingException e) {
            throw new AccountException(e);
        }
    }
}
