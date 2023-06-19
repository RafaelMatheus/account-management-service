package com.wallet.accountmanagementservice.core.strategy;

import com.wallet.accountmanagementservice.core.enumerated.TransactionType;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TransactionFactory {
    private final Map<TransactionType, ProcessTransactionStrategy> strategies;

    public TransactionFactory(Set<ProcessTransactionStrategy> processTransactionStrategies) {
        strategies = new HashMap<>();
        processTransactionStrategies.forEach(strategy -> this.strategies.put(strategy.getType(), strategy));
    }

    public ProcessTransactionStrategy get(TransactionType transactionType) {
        return strategies.get(transactionType);
    }
}
