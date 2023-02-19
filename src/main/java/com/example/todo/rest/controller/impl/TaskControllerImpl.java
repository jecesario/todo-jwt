package com.example.todo.rest.controller.impl;

import com.example.todo.domain.models.Task;
import com.example.todo.domain.services.TaskService;
import com.example.todo.rest.controller.TaskController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "task")
public class TaskControllerImpl implements TaskController {

    private final TaskService taskService;

    public TaskControllerImpl(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public ResponseEntity<List<Task>> findAll() {
        return ResponseEntity.ok(taskService.findAll());
    }
}
