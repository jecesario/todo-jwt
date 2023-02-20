package com.example.todo.rest.exceptions;

import com.example.todo.exceptions.BadRequestException;
import com.example.todo.exceptions.InvalidJwtException;
import com.example.todo.exceptions.Issue;
import com.example.todo.exceptions.IssueEnum;
import com.example.todo.exceptions.ObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Issue handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        var issue = new Issue(IssueEnum.ARGUMENT_NOT_VALID,
                Collections.singletonList(e.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList()));
        log.error(issue.getMessage(), e);
        return issue;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Issue handlerHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        var message = Arrays.stream(e.getMessage().split(":")).findFirst().orElse("Required request body is missing");
        var issue = new Issue(IssueEnum.ARGUMENT_NOT_VALID, List.of(message));
        log.error(issue.getMessage(), e);
        return issue;
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Issue handlerObjectNotFoundException(ObjectNotFoundException e) {
        log.error(e.getIssue().getMessage(), e);
        return e.getIssue();
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Issue handlerBadRequestException(BadRequestException e) {
        log.error(e.getIssue().getMessage(), e);
        return e.getIssue();
    }

    @ExceptionHandler(InvalidJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Issue handlerInvalidJwtException(InvalidJwtException e) {
        log.error(e.getIssue().getMessage(), e);
        return e.getIssue();
    }
}
