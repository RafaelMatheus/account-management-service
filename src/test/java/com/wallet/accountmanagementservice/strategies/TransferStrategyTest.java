package com.wallet.accountmanagementservice.strategies;

import com.wallet.accountmanagementservice.core.domain.AccountDomain;
import com.wallet.accountmanagementservice.core.domain.TransactionDomain;
import com.wallet.accountmanagementservice.core.enumerated.TransactionType;
import com.wallet.accountmanagementservice.core.exception.InsufficientBalanceException;
import com.wallet.accountmanagementservice.core.port.RabbitMqPort;
import com.wallet.accountmanagementservice.core.port.impl.AccountPortRepository;
import com.wallet.accountmanagementservice.core.service.AccountService;
import com.wallet.accountmanagementservice.core.strategy.TransferStrategy;
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
class TransferStrategyTest {
    @InjectMocks
    private TransferStrategy transferStrategy;
    @Mock
    private AccountPortRepository accountPortRepository;
    @Mock
    private RabbitMqPort rabbitMqPort;
    @Mock
    private AccountService accountService;

    @Test
    void shouldCalculateTrasnferAndSendToRabbitMq() {
        var domain = getAccountDomain();
        var domain2 = getAccountDomain2();
        when(accountService.getAccountInformation(ACCOUNT_NUMBER)).thenReturn(domain);
        when(accountService.getAccountInformation(ACCOUNT_NUMBER2)).thenReturn(domain2);
        doNothing().when(rabbitMqPort).send(any(), any(), any());
        ReflectionTestUtils.setField(transferStrategy, "propertiesConfiguration", getPropertiesTransactionConfiguration());

        var transactionDomain = new TransactionDomain(ACCOUNT_NUMBER, ACCOUNT_NUMBER2, BigDecimal.TEN, TransactionType.TRANSFER, null);

        transferStrategy.process(transactionDomain);

        ArgumentCaptor<AccountDomain> accountDomainArgumentCaptor = ArgumentCaptor.forClass(AccountDomain.class);

        verify(accountPortRepository, times(2)).save(accountDomainArgumentCaptor.capture());

        var valueFromCapture = accountDomainArgumentCaptor.getValue();

        assertAll(() -> assertEquals(domain.getAccountNumber(), valueFromCapture.getAccountNumber()),
                () -> assertEquals(domain.getBalance(), valueFromCapture.getBalance()),
                () -> assertEquals(domain.getHolderName(), valueFromCapture.getHolderName()),
                () -> assertEquals(domain.getPhoneNumber(), valueFromCapture.getPhoneNumber()),
                () -> assertEquals(domain.getHolderTaxId(), valueFromCapture.getHolderTaxId()));
    }

    @Test
    void shouldReturnErrorWhenOriginAccountHasAnyLimit() {
        var transactionDomain = new TransactionDomain(ACCOUNT_NUMBER, ACCOUNT_NUMBER2, BigDecimal.TEN, TransactionType.TRANSFER, null);

        var domain = getAccountDomain();
        var domain2 = getAccountDomain2();
        domain2.setBalance(BigDecimal.ZERO);
        when(accountService.getAccountInformation(ACCOUNT_NUMBER)).thenReturn(domain);
        when(accountService.getAccountInformation(ACCOUNT_NUMBER2)).thenReturn(domain2);

        assertThrows(InsufficientBalanceException.class, () -> transferStrategy.process(transactionDomain));
    }

    @Test
    void shouldReturnDepositWhenGetType() {
        assertEquals(TransactionType.TRANSFER, transferStrategy.getType());
    }
}
