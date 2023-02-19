package com.example.todo.rest.exceptions;

import com.example.todo.exceptions.Issue;
import com.example.todo.exceptions.IssueEnum;
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

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Issue handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new Issue(IssueEnum.ARGUMENT_NOT_VALID, Collections.singletonList(e.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Issue handlerHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        var message = Arrays.stream(e.getMessage().split(":")).findFirst().orElse("Required request body is missing");
        return new Issue(IssueEnum.ARGUMENT_NOT_VALID, List.of(message));
    }
}
