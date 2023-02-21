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
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService, UserDetailsService {

    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;

    public AccountServiceImpl(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public List<AccountResponse> findAll() {
        log.info("Finding all accounts");
        List<Account> accounts = accountRepository.findAll();

        return accounts.stream().map(account -> AccountResponse
                    .builder()
                    .withId(account.getId())
                    .withName(account.getName())
                    .withEmail(account.getEmail())
                    .build()).toList();
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
                .withPassword(passwordEncoder.encode(request.getPassword()))
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
        account.setPassword(passwordEncoder.encode(request.getPassword()));

        var accountPersisted = accountRepository.save(account);

        log.info("Account: {} updated", id);

        return AccountResponse
                .builder()
                .withId(accountPersisted.getId())
                .withName(accountPersisted.getName())
                .withEmail(accountPersisted.getEmail())
                .build();
    }

    @Override
    public UserDetails auth(Account account) {
        var userDetails = loadUserByUsername(account.getEmail());
        var passwordMatches = passwordEncoder.matches(account.getPassword(), userDetails.getPassword());

        if(passwordMatches) {
            return userDetails;
        }
        throw new BadRequestException(
                new Issue(IssueEnum.HEADER_REQUIRED_ERROR, "An error has occurred while authenticate account")
        );
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

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        var account = accountRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ObjectNotFoundException(
                                new Issue(IssueEnum.OBJECT_NOT_FOUND, List.of(String.format("Account with email '%s' not found", email)))
                        ));

        return User
                .builder()
                .username(account.getEmail())
                .password(account.getPassword())
                .roles("USER")
                .build();
    }
}
