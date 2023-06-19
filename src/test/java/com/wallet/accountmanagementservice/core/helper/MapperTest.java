package com.wallet.accountmanagementservice.core.helper;

import org.junit.jupiter.api.Test;

import static com.wallet.accountmanagementservice.base.BaseTestFactory.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MapperTest {
    @Test
    void shouldMapperFromAccountDomainToAccountEntity() {
        var accountDomain = getAccountDomain();

        var response = Mapper.toEntity(accountDomain);

        assertAll(
                () -> assertEquals(ID, response.getId()),
                () -> assertEquals(TAX_ID, response.getHolderTaxId()),
                () -> assertEquals(HOLDER_NAME, response.getHolderName()),
                () -> assertEquals(PHONE_NUMBER, response.getPhoneNumber()),
                () -> assertEquals(BALANCE, response.getBalance()),
                () -> assertEquals(ACCOUNT_NUMBER, response.getAccountNumber()));
    }

    @Test
    void shouldMapperFromAccountEntityToAccountDomain() {
        var accountDomain = getAccountEntity();

        var response = Mapper.toDomain(accountDomain);

        assertAll(
                () -> assertEquals(ID, response.getId()),
                () -> assertEquals(TAX_ID, response.getHolderTaxId()),
                () -> assertEquals(HOLDER_NAME, response.getHolderName()),
                () -> assertEquals(PHONE_NUMBER, response.getPhoneNumber()),
                () -> assertEquals(BALANCE, response.getBalance()),
                () -> assertEquals(ACCOUNT_NUMBER, response.getAccountNumber()));
    }
}
