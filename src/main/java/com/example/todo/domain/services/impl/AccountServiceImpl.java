package com.example.todo.domain.services.impl;

import com.example.todo.domain.models.Account;
import com.example.todo.domain.repositories.AccountRepository;
import com.example.todo.domain.services.AccountService;
import com.example.todo.rest.vo.AccountRequest;
import com.example.todo.rest.vo.AccountResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public AccountResponse create(AccountRequest request) {

        var account = Account
                .builder()
                .withName(request.getName())
                .withEmail(request.getEmail())
                .withPassword(request.getPassword())
                .build();

        var accountPersisted = accountRepository.save(account);

        return AccountResponse
                .builder()
                .withId(accountPersisted.getId())
                .withName(accountPersisted.getName())
                .withEmail(accountPersisted.getEmail())
                .build();
    }
}
