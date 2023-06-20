package com.wallet.accountmanagementservice.service;

import com.wallet.accountmanagementservice.core.exception.AccountNotFoundException;
import com.wallet.accountmanagementservice.core.port.impl.AccountPortRepository;
import com.wallet.accountmanagementservice.core.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.wallet.accountmanagementservice.base.BaseTestFactory.ACCOUNT_NUMBER;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {
    @InjectMocks
    private AccountServiceImpl accountService;
    @Mock
    private AccountPortRepository accountPortRepository;

    @Test
    void shouldThrowAnExceptionWhenNotFoundAccount() {
        when(accountPortRepository.findByAccountNumber(any())).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> accountService.getAccountInformation(ACCOUNT_NUMBER));
    }
}