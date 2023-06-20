package com.wallet.accountmanagementservice.strategies;

import com.wallet.accountmanagementservice.core.domain.AccountDomain;
import com.wallet.accountmanagementservice.core.domain.TransactionDomain;
import com.wallet.accountmanagementservice.core.enumerated.TransactionType;
import com.wallet.accountmanagementservice.core.exception.InsufficientBalanceException;
import com.wallet.accountmanagementservice.core.port.RabbitMqPort;
import com.wallet.accountmanagementservice.core.port.impl.AccountPortRepository;
import com.wallet.accountmanagementservice.core.service.AccountService;
import com.wallet.accountmanagementservice.core.strategy.WithdrawStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;

import static com.wallet.accountmanagementservice.base.BaseTestFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WithdrawStrategyTest {
    @InjectMocks
    private WithdrawStrategy withdrawStrategy;
    @Mock
    private AccountPortRepository accountPortRepository;
    @Mock
    private RabbitMqPort rabbitMqPort;
    @Mock
    private AccountService accountService;

    @Test
    void shouldCalculateDepositAndSendToRabbitMq() {
        var domain = getAccountDomain2();
        when(accountService.getAccountInformation(ACCOUNT_NUMBER2)).thenReturn(domain);
        doNothing().when(rabbitMqPort).send(any(), any(), any());
        ReflectionTestUtils.setField(withdrawStrategy, "propertiesConfiguration", getPropertiesTransactionConfiguration());
        var transactionDomain = new TransactionDomain(null, ACCOUNT_NUMBER2, BigDecimal.TEN, TransactionType.WITHDRAW, null);

        withdrawStrategy.process(transactionDomain);

        ArgumentCaptor<AccountDomain> accountDomainArgumentCaptor = ArgumentCaptor.forClass(AccountDomain.class);

        verify(accountPortRepository).save(accountDomainArgumentCaptor.capture());

        var valueFromCapture = accountDomainArgumentCaptor.getValue();

        assertAll(() -> assertEquals(domain.getAccountNumber(), valueFromCapture.getAccountNumber()),
                () -> assertEquals(domain.getBalance(), valueFromCapture.getBalance()),
                () -> assertEquals(domain.getHolderName(), valueFromCapture.getHolderName()),
                () -> assertEquals(domain.getPhoneNumber(), valueFromCapture.getPhoneNumber()),
                () -> assertEquals(domain.getHolderTaxId(), valueFromCapture.getHolderTaxId()));
    }

    @Test
    void shouldReturnErrorWhenOriginAccountHasAnyLimit() {
        var transactionDomain = new TransactionDomain(null, ACCOUNT_NUMBER2, BigDecimal.TEN, TransactionType.WITHDRAW, null);

        var domain = getAccountDomain2();
        domain.setBalance(BigDecimal.ZERO);
        when(accountService.getAccountInformation(ACCOUNT_NUMBER2)).thenReturn(domain);

        assertThrows(InsufficientBalanceException.class, () -> withdrawStrategy.process(transactionDomain));
    }

}
