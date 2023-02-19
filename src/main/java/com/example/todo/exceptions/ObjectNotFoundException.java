package com.example.todo.exceptions;

import lombok.Getter;

public class ObjectNotFoundException extends RuntimeException {
    @Getter
    private final Issue issue;

    public ObjectNotFoundException(Issue issue) {
        this.issue = issue;
    }
}
