package com.example.todo.domain.services;

import com.example.todo.domain.models.Account;
import com.example.todo.rest.vo.AccountRequest;
import com.example.todo.rest.vo.AccountResponse;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface AccountService {
    List<Account> findAll();
    AccountResponse create(AccountRequest request);

    void delete(Long id);

    AccountResponse update(AccountRequest request, Long id);

    UserDetails auth(Account account);
}
