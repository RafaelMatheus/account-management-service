package com.wallet.accountmanagementservice.adapter.controller;

import com.wallet.accountmanagementservice.adapter.dtos.request.AccountRequest;
import com.wallet.accountmanagementservice.adapter.dtos.request.TransactionRequest;
import com.wallet.accountmanagementservice.adapter.dtos.response.AccountResponse;
import com.wallet.accountmanagementservice.core.helper.Mapper;
import com.wallet.accountmanagementservice.core.service.AccountService;
import com.wallet.accountmanagementservice.core.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/accounts")
public class AccountController {
    private final AccountService accountService;
    private final TransactionService transactionService;

    public AccountController(AccountService accountService, TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @PostMapping
    public AccountResponse generateAccount(@RequestBody @Valid AccountRequest request) {
        return Mapper.toResponse(accountService.generateAccount(Mapper.toDomain(request)));
    }

    @GetMapping
    public AccountResponse getAccountInformationByAccountNumber(String accountNumber) {
        return Mapper.toResponse(accountService.getAccountInformation(accountNumber));
    }

    @PostMapping("/transaction")
    public void executeAccountTransaction(@RequestBody @Valid TransactionRequest transactionRequest) {
        transactionService.executeAccountTransaction(Mapper.toTransactionDomain(transactionRequest));
    }
}
