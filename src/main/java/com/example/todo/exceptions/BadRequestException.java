package com.example.todo.exceptions;

import lombok.Getter;

public class BadRequestException extends RuntimeException {
    @Getter
    private final Issue issue;

    public BadRequestException(Issue issue) {
        this.issue = issue;
    }
}
