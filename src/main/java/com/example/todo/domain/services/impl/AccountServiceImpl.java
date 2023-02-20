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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Account> findAll() {
        log.info("Finding all accounts");
        return accountRepository.findAll();
    }

    @Override
    public AccountResponse create(AccountRequest request) {

        log.info("Starts create account");

        if(emailExists(request.getEmail())) {
            throw new BadRequestException(
                    new Issue(IssueEnum.ARGUMENT_NOT_VALID, List.of(String.format("The email '%s' is in use", request.getEmail())))
            );
        }

        var account = Account
                .builder()
                .withName(request.getName())
                .withEmail(request.getEmail())
                .withPassword(request.getPassword())
                .build();

        var accountPersisted = accountRepository.save(account);

        log.info("Account saved");
        return AccountResponse
                .builder()
                .withId(accountPersisted.getId())
                .withName(accountPersisted.getName())
                .withEmail(accountPersisted.getEmail())
                .build();
    }

    @Override
    public void delete(Long id) {
        log.info("Starts delete account: {}", id);
        findById(id);
        accountRepository.deleteById(id);
        log.info("Account: {} deleted", id);
    }

    @Override
    public AccountResponse update(AccountRequest request, Long id) {

        log.info("Starts update account: {}", id);

        var account = findById(id);

        if(emailExists(request.getEmail()) && !account.getEmail().equalsIgnoreCase(request.getEmail())) {
            throw new BadRequestException(
                    new Issue(IssueEnum.ARGUMENT_NOT_VALID, List.of(String.format("The email '%s' is in use", request.getEmail())))
            );
        }

        account.setName(request.getName());
        account.setEmail(request.getEmail());
        account.setPassword(request.getPassword());

        var accountPersisted = accountRepository.save(account);

        log.info("Account: {} updated", id);

        return AccountResponse
                .builder()
                .withId(accountPersisted.getId())
                .withName(accountPersisted.getName())
                .withEmail(accountPersisted.getEmail())
                .build();
    }

    private Account findById(Long id) {
        log.info("Finding account: {} in database", id);
        return accountRepository.findById(id)
                .orElseThrow(() ->
                    new ObjectNotFoundException(
                            new Issue(IssueEnum.OBJECT_NOT_FOUND, List.of(String.format("Account with id: %s not found", id)))
                    ));
    }

    private boolean emailExists(String email) {
        log.info("Verifying if email is in use");
        return accountRepository.findByEmail(email).isPresent();
    }
}
