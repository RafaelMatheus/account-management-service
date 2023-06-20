package com.wallet.accountmanagementservice.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wallet.accountmanagementservice.adapter.dtos.response.AccountResponse;
import com.wallet.accountmanagementservice.adapter.entity.AccountEntity;
import com.wallet.accountmanagementservice.core.port.impl.AccountPortRepository;
import com.wallet.accountmanagementservice.integration.config.MongoIntegrationConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

import static com.wallet.accountmanagementservice.base.BaseTestFactory.getAccountRequest;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureDataMongo
class AccountControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private AccountPortRepository portRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @AfterEach
    void clean() throws IOException {
        MongoIntegrationConfig.stopMong();
    }

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext) throws IOException {
        MongoIntegrationConfig.startMongo();

        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    void shouldCreateAnNewAccountWithSuccessful() throws Exception {
        var objectMapper = new ObjectMapper();

        var response = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/accounts/")
                        .content(objectMapper.writeValueAsString(getAccountRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(200))
                .andReturn();
        var accountResponse = objectMapper.readValue(response.getResponse().getContentAsString(), AccountResponse.class);

        var responseFromDatabase = portRepository.findByAccountNumber(accountResponse.getAccountNumber()).get();
        var entityResponse = mongoTemplate.findAll(AccountEntity.class);

        assertAll(() -> assertNotNull(responseFromDatabase.getAccountNumber()),
                () -> assertNotNull(accountResponse.getAccountNumber()),
                () -> assertNotNull(entityResponse.get(0).getAccountNumber()));

    }
}
