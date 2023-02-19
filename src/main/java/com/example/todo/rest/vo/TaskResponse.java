package com.example.todo.rest.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class TaskResponse {
    private Long id;
    private String title;
    private Boolean status;
}
