package com.example.todo.rest.controller.impl;

import com.example.todo.domain.models.Account;
import com.example.todo.domain.services.AccountService;
import com.example.todo.exceptions.InvalidJwtException;
import com.example.todo.exceptions.Issue;
import com.example.todo.exceptions.IssueEnum;
import com.example.todo.rest.controller.AccountController;
import com.example.todo.rest.vo.AccountRequest;
import com.example.todo.rest.vo.AccountResponse;
import com.example.todo.rest.vo.CredentialsRequest;
import com.example.todo.rest.vo.JwtTokenResponse;
import com.example.todo.security.jwt.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "account")
public class AccountControllerImpl implements AccountController {

    private final AccountService accountService;
    private final JwtService jwtService;

    public AccountControllerImpl(AccountService accountService, JwtService jwtService) {
        this.accountService = accountService;
        this.jwtService = jwtService;
    }

    @Override
    public ResponseEntity<List<AccountResponse>> findAll() {
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

    @Override
    public JwtTokenResponse auth(CredentialsRequest request) {

        try {
            var account = Account
                            .builder()
                            .withEmail(request.getEmail())
                            .withPassword(request.getPassword())
                            .build();

            accountService.auth(account);

            var token = jwtService.generateToken(account);

            return JwtTokenResponse
                    .builder()
                    .withType("Bearer")
                    .withToken(token)
                    .build();

        } catch (Exception e) {
            throw new InvalidJwtException(
                    new Issue(IssueEnum.HEADER_REQUIRED_ERROR,  List.of("An error has occurred while generate JWT Token"))
            );
        }
    }

}
