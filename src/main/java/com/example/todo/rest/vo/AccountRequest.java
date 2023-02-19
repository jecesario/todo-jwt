package com.example.todo.rest.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class AccountRequest {

    @NotBlank(message = "The 'name' field is required")
    private String name;
    @NotBlank(message = "The 'email' field is required")
    @Email(message = "The 'email' must be a valid email")
    private String email;
    @NotBlank(message = "The 'password' field is required")
    private String password;
}
