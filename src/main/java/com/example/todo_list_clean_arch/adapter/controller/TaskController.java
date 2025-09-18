package com.example.todo_list_clean_arch.adapter.controller;

import com.example.todo_list_clean_arch.infra.mapper.TaskMapper;
import com.example.todo_list_clean_arch.domain.model.Task;
import com.example.todo_list_clean_arch.domain.usecase.*;
import com.example.todo_list_clean_arch.infra.dto.TaskDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tasks")
public class TaskController {

    private final CreateTaskUseCase createTaskUseCase;
    private final DeleteTaskUseCase deleteTaskUseCase;
    private final RetrieveTaskByIdUseCase retrieveTaskByIdUseCase;
    private final RetrieveAllTaskUseCase retrieveAllTaskUseCase;
    private final UpdateTaskUseCase updateTaskUseCase;
    private final CompleteTaskUseCase completeTaskUseCase;
    private final TaskMapper taskMapper;

    public TaskController(CreateTaskUseCase createTaskUseCase, DeleteTaskUseCase deleteTaskUseCase, RetrieveTaskByIdUseCase retrieveTaskByIdUseCase, RetrieveAllTaskUseCase retrieveAllTaskUseCase, UpdateTaskUseCase updateTaskUseCase, TaskMapper taskMapper, CompleteTaskUseCase completeTaskUseCase) {
        this.createTaskUseCase = createTaskUseCase;
        this.deleteTaskUseCase = deleteTaskUseCase;
        this.retrieveTaskByIdUseCase = retrieveTaskByIdUseCase;
        this.retrieveAllTaskUseCase = retrieveAllTaskUseCase;
        this.updateTaskUseCase = updateTaskUseCase;
        this.taskMapper = taskMapper;
        this.completeTaskUseCase = completeTaskUseCase;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDTO create(@RequestBody TaskDTO taskDTO) {
        Task task = taskMapper.toModel(taskDTO);
        Task createdTask = createTaskUseCase.execute(task);
        return taskMapper.fromModel(createdTask);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskDTO findById(@PathVariable String id){
        Task task = retrieveTaskByIdUseCase.execute(id);
        return taskMapper.fromModel(task);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TaskDTO> findAll(){
        List<Task> taskList = retrieveAllTaskUseCase.execute();
        return taskList.stream().map(taskMapper::fromModel).toList();
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskDTO update(@PathVariable String id, @RequestBody TaskDTO taskDTO) {
        Task task = taskMapper.toModel(taskDTO);
        Task updateTask = updateTaskUseCase.execute(id, task);
        return taskMapper.fromModel(updateTask);
    }

    @PostMapping(value = "/{id}/complete")
    @ResponseStatus(HttpStatus.OK)
    public TaskDTO complete(@PathVariable String id) {
        Task task = completeTaskUseCase.execute(id);
        return taskMapper.fromModel(task);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable String id) {
        deleteTaskUseCase.execute(id);
    }
}
