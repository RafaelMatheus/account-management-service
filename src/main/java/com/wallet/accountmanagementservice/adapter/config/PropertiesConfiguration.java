package com.wallet.accountmanagementservice.adapter.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties
@Configuration
public class PropertiesConfiguration {
    private TransactionTypeConfig transaction;
    private TransactionTypeConfig payment;

    public TransactionTypeConfig getPayment() {
        return payment;
    }

    public void setPayment(TransactionTypeConfig payment) {
        this.payment = payment;
    }

    public TransactionTypeConfig getTransaction() {
        return transaction;
    }

    public void setTransaction(TransactionTypeConfig transaction) {
        this.transaction = transaction;
    }

    public static class TransactionTypeConfig {
        private RabbitConfig rabbit;

        public RabbitConfig getRabbit() {
            return rabbit;
        }

        public void setRabbit(RabbitConfig rabbit) {
            this.rabbit = rabbit;
        }

    }

    public static class RabbitConfig {
        private String queueName;
        private String exchangeName;
        private String routingKey;
        private String deadLetterRoutingKey;
        private String deadLetterExchange;
        private String deadLetterQueueName;

        public String getQueueName() {
            return queueName;
        }

        public void setQueueName(String queueName) {
            this.queueName = queueName;
        }

        public String getExchangeName() {
            return exchangeName;
        }

        public void setExchangeName(String exchangeName) {
            this.exchangeName = exchangeName;
        }

        public String getRoutingKey() {
            return routingKey;
        }

        public void setRoutingKey(String routingKey) {
            this.routingKey = routingKey;
        }

        public String getDeadLetterRoutingKey() {
            return deadLetterRoutingKey;
        }

        public void setDeadLetterRoutingKey(String deadLetterRoutingKey) {
            this.deadLetterRoutingKey = deadLetterRoutingKey;
        }

        public String getDeadLetterExchange() {
            return deadLetterExchange;
        }

        public void setDeadLetterExchange(String deadLetterExchange) {
            this.deadLetterExchange = deadLetterExchange;
        }

        public String getDeadLetterQueueName() {
            return deadLetterQueueName;
        }

        public void setDeadLetterQueueName(String deadLetterQueueName) {
            this.deadLetterQueueName = deadLetterQueueName;
        }
    }
}
