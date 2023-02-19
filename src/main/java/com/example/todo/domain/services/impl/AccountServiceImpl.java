package com.example.todo.domain.services.impl;

import com.example.todo.domain.models.Account;
import com.example.todo.domain.repositories.AccountRepository;
import com.example.todo.domain.services.AccountService;
import com.example.todo.exceptions.BadRequestException;
import com.example.todo.exceptions.Issue;
import com.example.todo.exceptions.IssueEnum;
import com.example.todo.exceptions.ObjectNotFoundException;
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

    @Override
    public void delete(Long id) {
        findById(id);
        accountRepository.deleteById(id);
    }

    @Override
    public AccountResponse update(AccountRequest request, Long id) {

        var account = findById(id);

        if(emailExists(request.getEmail()) && !account.getEmail().equalsIgnoreCase(request.getEmail())) {
            throw new BadRequestException(
                    new Issue(IssueEnum.ARGUMENT_NOT_VALID, List.of(String.format("The email: %s is in use", request.getEmail())))
            );
        }

        account.setName(request.getName());
        account.setEmail(request.getEmail());
        account.setPassword(request.getPassword());

        var accountPersisted = accountRepository.save(account);

        return AccountResponse
                .builder()
                .withId(accountPersisted.getId())
                .withName(accountPersisted.getName())
                .withEmail(accountPersisted.getEmail())
                .build();
    }

    private Account findById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() ->
                    new ObjectNotFoundException(
                            new Issue(IssueEnum.OBJECT_NOT_FOUND, List.of(String.format("Account with id: %s not found", id)))
                    ));
    }

    private boolean emailExists(String email) {
        return accountRepository.findByEmail(email).isPresent();
    }
}
