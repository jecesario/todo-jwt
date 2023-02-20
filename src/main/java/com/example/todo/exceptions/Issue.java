package com.example.todo.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class Issue {
    private final String message;
    private final List<Object> details;

    public Issue(IssueEnum issueEnum, List<Object> details) {
        this.message = issueEnum.getMessage();
        this.details = details;
    }

    public Issue(IssueEnum issueEnum, Object details) {
        this.message = issueEnum.getMessage();
        this.details = List.of(details);
    }
}
