package com.example.todo.rest.controller.impl;

import com.example.todo.domain.models.Account;
import com.example.todo.domain.services.AccountService;
import com.example.todo.rest.controller.AccountController;
import com.example.todo.rest.vo.AccountRequest;
import com.example.todo.rest.vo.AccountResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "account")
public class AccountControllerImpl implements AccountController {

    private final AccountService accountService;

    public AccountControllerImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public ResponseEntity<List<Account>> findAll() {
        return ResponseEntity.ok(accountService.findAll());
    }

    @Override
    public ResponseEntity<AccountResponse> create(AccountRequest request) {
        var accountResponse = accountService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(accountResponse);
    }

    @Override
    public ResponseEntity<AccountResponse> update(AccountRequest request, Long id) {
        var accountResponse = accountService.update(request, id);
        return ResponseEntity.ok(accountResponse);
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        accountService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
