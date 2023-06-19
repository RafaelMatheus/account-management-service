package com.wallet.accountmanagementservice.adapter.controller;

import com.wallet.accountmanagementservice.adapter.dtos.request.AccountRequest;
import com.wallet.accountmanagementservice.adapter.dtos.request.TransactionRequest;
import com.wallet.accountmanagementservice.adapter.dtos.response.AccountResponse;
import com.wallet.accountmanagementservice.core.helper.Mapper;
import com.wallet.accountmanagementservice.core.service.AccountService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public AccountResponse generateAccount(@RequestBody AccountRequest request) {
        return Mapper.toResponse(accountService.generateAccount(Mapper.toDomain(request)));
    }

    @GetMapping("/{accountNumber}")
    public AccountResponse getAccountInformationByAccountNumber(@PathVariable String accountNumber) {
        return Mapper.toResponse(accountService.getAccountInformation(accountNumber));
    }

    @PostMapping("/transaction")
    public void executeAccountTransaction(@RequestBody TransactionRequest transactionRequest) {
        accountService.executeAccountTransaction(Mapper.toTransactionDomain(transactionRequest));
    }
}
