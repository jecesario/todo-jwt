package com.example.todo.domain.services;

import com.example.todo.domain.models.Task;
import com.example.todo.rest.vo.TaskRequest;
import com.example.todo.rest.vo.TaskResponse;

import java.util.List;

public interface TaskService {
    List<Task> findAll();

    TaskResponse create(TaskRequest request);

    void delete(Long id);

    TaskResponse update(TaskRequest request, Long id);

    TaskResponse toggle(Long id);
}
