package com.example.todo.domain.services.impl;

import com.example.todo.domain.models.Task;
import com.example.todo.domain.repositories.TaskRepository;
import com.example.todo.domain.services.TaskService;
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
}
