package com.example.todo.rest.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor()
@Builder(setterPrefix = "with")
public class AccountResponse {
    private Long id;
    private String name;
    private String email;
}
