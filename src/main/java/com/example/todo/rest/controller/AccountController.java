package com.example.todo.rest.controller;

import com.example.todo.domain.models.Account;
import com.example.todo.rest.vo.AccountRequest;
import com.example.todo.rest.vo.AccountResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


public interface AccountController {
    @GetMapping
    ResponseEntity<List<Account>> findAll();

    @PostMapping
    ResponseEntity<AccountResponse> create(@RequestBody AccountRequest request);
}