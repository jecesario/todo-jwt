package com.example.todo.rest.controller;

import com.example.todo.domain.models.Task;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface TaskController {
    @GetMapping
    ResponseEntity<List<Task>> findAll();
}
