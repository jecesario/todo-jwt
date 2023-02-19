package com.example.todo.domain.services;

import com.example.todo.domain.models.Task;

import java.util.List;

public interface TaskService {
    List<Task> findAll();
}
