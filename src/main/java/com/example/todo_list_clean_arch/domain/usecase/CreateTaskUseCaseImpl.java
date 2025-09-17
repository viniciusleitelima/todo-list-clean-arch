package com.example.todo_list_clean_arch.domain.usecase;

import com.example.todo_list_clean_arch.adapter.repository.TaskRepository;
import com.example.todo_list_clean_arch.domain.exception.BusinessException;
import com.example.todo_list_clean_arch.domain.mapper.TaskMapper;
import com.example.todo_list_clean_arch.domain.model.Task;
import com.example.todo_list_clean_arch.infra.persistence.entity.TaskEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.UUID;

public class CreateTaskUseCaseImpl implements CreateTaskUseCase{

    private static final Logger logger = LoggerFactory.getLogger(CreateTaskUseCaseImpl.class);

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public CreateTaskUseCaseImpl(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }


    @Override
    public Task execute(Task task) {
        logger.info("Iniciando criação de task");
        try {
            task.setId(UUID.randomUUID().toString());
            task.setCreatedAt(LocalDateTime.now());
            task.setUpdatedAt(LocalDateTime.now());
            TaskEntity taskEntity = taskMapper.toEntity(task);
            TaskEntity taskEntitySaved = taskRepository.save(taskEntity);
            Task taskSaved = taskMapper.toModel(taskEntitySaved);
            logger.info("Task salva com sucesso para o id: {}", taskEntitySaved.getId());
            return taskSaved;
        } catch (BusinessException e) {
            logger.error("Erro de negócio ao criar task, error={}",e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("Erro inesperado ao criar task, error={}",e.getMessage(), e);
            throw new BusinessException("task.creation.failed");
        }

    }
}
