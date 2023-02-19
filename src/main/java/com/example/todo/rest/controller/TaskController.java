package com.example.todo.rest.controller;

import com.example.todo.domain.models.Task;
import com.example.todo.rest.vo.CreateTaskRequest;
import com.example.todo.rest.vo.TaskResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface TaskController {
    @GetMapping
    ResponseEntity<List<Task>> findAll();

    @PostMapping
    ResponseEntity<TaskResponse> create(@RequestBody @Valid CreateTaskRequest request);
}
