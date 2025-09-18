package com.example.todo_list_clean_arch.infra.config;

import com.example.todo_list_clean_arch.adapter.repository.TaskRepository;
import com.example.todo_list_clean_arch.infra.mapper.TaskMapper;
import com.example.todo_list_clean_arch.domain.usecase.*;
import com.example.todo_list_clean_arch.infra.mapper.TaskMapperImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TaskConfig {

    @Bean
    public TaskMapper taskMapper() {
        return new TaskMapperImpl();
    }

    @Bean
    public CreateTaskUseCase createTaskUseCase(TaskRepository taskRepository) {
        return new CreateTaskUseCaseImpl(taskRepository);
    }

    @Bean
    public DeleteTaskUseCase deleteTaskUseCase(TaskRepository taskRepository) {
        return new DeleteUseCaseImpl(taskRepository);
    }

    @Bean
    public RetrieveAllTaskUseCase retrieveAllTaskUseCase(TaskRepository taskRepository) {
        return new RetrieveAllTaskUseCaseImpl(taskRepository);
    }

    @Bean
    public RetrieveTaskByIdUseCase retrieveTaskByIdUseCase(TaskRepository taskRepository) {
        return new RetrieveTaskByIdUseCaseImpl(taskRepository);
    }

    @Bean
    public UpdateTaskUseCase updateTaskUseCase(TaskRepository taskRepository) {
        return new UpdateTaskUseCaseImpl(taskRepository);
    }

    @Bean
    public CompleteTaskUseCase completeTaskUseCase(TaskRepository taskRepository){
        return new CompleteTaskUseCaseImpl(taskRepository);
    }
}
