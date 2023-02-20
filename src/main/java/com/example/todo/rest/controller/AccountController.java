package com.example.todo.rest.controller;

import com.example.todo.domain.models.Account;
import com.example.todo.rest.vo.AccountRequest;
import com.example.todo.rest.vo.AccountResponse;
import com.example.todo.rest.vo.CredentialsRequest;
import com.example.todo.rest.vo.JwtTokenResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


public interface AccountController {
    @GetMapping
    ResponseEntity<List<Account>> findAll();

    @PostMapping
    ResponseEntity<AccountResponse> create(@RequestBody @Valid AccountRequest request);

    @PutMapping(value = "/{id}")
    ResponseEntity<AccountResponse> update(@RequestBody @Valid AccountRequest request, @PathVariable Long id);

    @DeleteMapping(value = "/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);

    @PostMapping(value = "/auth")
    JwtTokenResponse auth(@RequestBody @Valid CredentialsRequest request);
}
