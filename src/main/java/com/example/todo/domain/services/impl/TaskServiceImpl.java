package com.example.todo.domain.services.impl;

import com.example.todo.domain.models.Task;
import com.example.todo.domain.repositories.TaskRepository;
import com.example.todo.domain.services.TaskService;
import com.example.todo.exceptions.Issue;
import com.example.todo.exceptions.IssueEnum;
import com.example.todo.exceptions.ObjectNotFoundException;
import com.example.todo.rest.vo.TaskRequest;
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
    public TaskResponse create(TaskRequest request) {

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

    @Override
    public void delete(Long id) {
        findById(id);
        taskRepository.deleteById(id);
    }

    @Override
    public TaskResponse update(TaskRequest request, Long id) {

        var task = findById(id);
        task.setTitle(request.getTitle());

        var persistedTask = taskRepository.save(task);

        return TaskResponse
                .builder()
                .withId(persistedTask.getId())
                .withTitle(persistedTask.getTitle())
                .withStatus(persistedTask.getStatus())
                .build();
    }

    @Override
    public TaskResponse toggle(Long id) {

        var task = findById(id);
        task.setStatus(!task.getStatus());

        var persistedTask = taskRepository.save(task);

        return TaskResponse
                .builder()
                .withId(persistedTask.getId())
                .withTitle(persistedTask.getTitle())
                .withStatus(persistedTask.getStatus())
                .build();
    }

    private Task findById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(()->
                        new ObjectNotFoundException(
                                new Issue(IssueEnum.OBJECT_NOT_FOUND, List.of(String.format("Task with id: %s not found", id)))
                        ));
    }
}
