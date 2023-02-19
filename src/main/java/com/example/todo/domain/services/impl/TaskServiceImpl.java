package com.example.todo.domain.services.impl;

import com.example.todo.domain.models.Task;
import com.example.todo.domain.repositories.TaskRepository;
import com.example.todo.domain.services.TaskService;
import com.example.todo.rest.vo.CreateTaskRequest;
import com.example.todo.rest.vo.TaskResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public TaskResponse create(CreateTaskRequest request) {

        var task = Task
                .builder()
                .withTitle(request.getTitle())
                .withStatus(Boolean.FALSE)
                .build();

        var persistedTask = taskRepository.save(task);

        return TaskResponse
                .builder()
                .withId(persistedTask.getId())
                .withTitle(persistedTask.getTitle())
                .withStatus(persistedTask.getStatus())
                .build();
    }
}
