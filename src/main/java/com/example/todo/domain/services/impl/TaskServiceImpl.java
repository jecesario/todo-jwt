package com.example.todo.domain.services.impl;

import com.example.todo.domain.models.Task;
import com.example.todo.domain.repositories.TaskRepository;
import com.example.todo.domain.services.TaskService;
import com.example.todo.exceptions.Issue;
import com.example.todo.exceptions.IssueEnum;
import com.example.todo.exceptions.ObjectNotFoundException;
import com.example.todo.rest.vo.TaskRequest;
import com.example.todo.rest.vo.TaskResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    @Override
    public List<Task> findAll() {
        log.info("Finding all tasks");
        return taskRepository.findAll();
    }

    @Override
    public TaskResponse create(TaskRequest request) {

        log.info("Starts create task: {}", request.getTitle());

        var task = Task
                .builder()
                .withTitle(request.getTitle())
                .withStatus(Boolean.FALSE)
                .build();

        var persistedTask = taskRepository.save(task);

        log.info("Task: {} saved", request.getTitle());
        return TaskResponse
                .builder()
                .withId(persistedTask.getId())
                .withTitle(persistedTask.getTitle())
                .withStatus(persistedTask.getStatus())
                .build();
    }

    @Override
    public void delete(Long id) {
        log.info("Starts delete task: {}", id);
        findById(id);
        taskRepository.deleteById(id);
        log.info("Task: {} deleted", id);
    }

    @Override
    public TaskResponse update(TaskRequest request, Long id) {

        log.info("Starts update task: {}", request.getTitle());

        var task = findById(id);
        task.setTitle(request.getTitle());

        var persistedTask = taskRepository.save(task);

        log.info("Task: {} updated", request.getTitle());

        return TaskResponse
                .builder()
                .withId(persistedTask.getId())
                .withTitle(persistedTask.getTitle())
                .withStatus(persistedTask.getStatus())
                .build();
    }

    @Override
    public TaskResponse toggle(Long id) {

        log.info("Toggling status to Task: {}", id);

        var task = findById(id);
        task.setStatus(!task.getStatus());

        var persistedTask = taskRepository.save(task);

        log.info("Task saved with status: {}", persistedTask.getStatus());

        return TaskResponse
                .builder()
                .withId(persistedTask.getId())
                .withTitle(persistedTask.getTitle())
                .withStatus(persistedTask.getStatus())
                .build();
    }

    private Task findById(Long id) {
        log.info("Finding task: {} in database", id);
        return taskRepository.findById(id)
                .orElseThrow(()->
                        new ObjectNotFoundException(
                                new Issue(IssueEnum.OBJECT_NOT_FOUND, List.of(String.format("Task with id: %s not found", id)))
                        ));
    }
}
