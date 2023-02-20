package com.example.todo.exceptions;

public enum IssueEnum {
    ARGUMENT_NOT_VALID("An error has occurred while validate the request body"),
    OBJECT_NOT_FOUND("The request object cannot be found in database"),
    HEADER_REQUIRED_ERROR("An error has occurred while validate required headers");

    private final String message;

    IssueEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
