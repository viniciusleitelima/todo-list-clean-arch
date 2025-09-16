package com.example.todo_list_clean_arch.infra.config;

import com.example.todo_list_clean_arch.adapter.repository.TaskRepository;
import com.example.todo_list_clean_arch.domain.usecase.*;
import com.example.todo_list_clean_arch.infra.mapper.TaskMapper;
import com.example.todo_list_clean_arch.infra.mapper.TaskMapperImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TaskUseCaseConfig {

    @Bean
    public TaskMapper taskMapper() {
        return new TaskMapperImpl();
    }

    @Bean
    public CreateTaskUseCase createTaskUseCase(TaskRepository taskRepository, TaskMapper taskMapper) {
        return new CreateTaskUseCaseImpl(taskRepository,taskMapper);
    }

    @Bean
    public DeleteTaskUseCase deleteTaskUseCase(TaskRepository taskRepository) {
        return new DeleteUseCaseImpl(taskRepository);
    }

    @Bean
    public RetrieveAllTaskUseCase retrieveAllTaskUseCase(TaskRepository taskRepository, TaskMapper taskMapper) {
        return new RetrieveAllTaskUseCaseImpl(taskRepository,taskMapper);
    }

    @Bean
    public RetrieveTaskByIdUseCase retrieveTaskByIdUseCase(TaskRepository taskRepository, TaskMapper taskMapper) {
        return new RetrieveTaskByIdUseCaseImpl(taskRepository,taskMapper);
    }

    @Bean
    public UpdateTaskUseCase updateTaskUseCase(TaskRepository taskRepository, TaskMapper taskMapper) {
        return new UpdateTaskUseCaseImpl(taskRepository,taskMapper);
    }

    @Bean
    public CompleteTaskUseCase completeTaskUseCase(TaskRepository taskRepository, TaskMapper taskMapper){
        return new CompleteTaskUseCaseImpl(taskRepository, taskMapper);
    }
}
