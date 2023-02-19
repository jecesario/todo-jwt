package com.example.todo.domain.services;

import com.example.todo.domain.models.Task;
import com.example.todo.rest.vo.CreateTaskRequest;
import com.example.todo.rest.vo.TaskResponse;

import java.util.List;

public interface TaskService {
    List<Task> findAll();

    TaskResponse create(CreateTaskRequest request);

    void delete(Long id);
}
