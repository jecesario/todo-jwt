package com.example.todo.rest.controller;

import com.example.todo.domain.models.Task;
import com.example.todo.rest.vo.TaskRequest;
import com.example.todo.rest.vo.TaskResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface TaskController {
    @GetMapping
    ResponseEntity<List<Task>> findAll();

    @PostMapping
    ResponseEntity<TaskResponse> create(@RequestBody @Valid TaskRequest request);

    @PutMapping("/{id}")
    ResponseEntity<TaskResponse> update(@RequestBody @Valid TaskRequest request, @PathVariable Long id);

    @PutMapping("/{id}/toggle")
    ResponseEntity<TaskResponse> toggle(@PathVariable Long id);

    @DeleteMapping(value = "/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);
}
