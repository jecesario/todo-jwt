package com.example.todo.exceptions;

import lombok.Getter;

public class InvalidJwtException extends RuntimeException {
    @Getter
    private final Issue issue;

    public InvalidJwtException(Issue issue) {
        this.issue = issue;
    }
}
