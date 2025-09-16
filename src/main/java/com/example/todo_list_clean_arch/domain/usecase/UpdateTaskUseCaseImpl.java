package com.example.todo_list_clean_arch.domain.usecase;

import com.example.todo_list_clean_arch.adapter.repository.TaskRepository;
import com.example.todo_list_clean_arch.domain.exception.BusinessException;
import com.example.todo_list_clean_arch.domain.model.Task;
import com.example.todo_list_clean_arch.infra.mapper.TaskMapper;
import com.example.todo_list_clean_arch.infra.persistence.entity.TaskEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateTaskUseCaseImpl implements UpdateTaskUseCase {


    private static final Logger logger = LoggerFactory.getLogger(UpdateTaskUseCaseImpl.class);

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public UpdateTaskUseCaseImpl(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public Task execute(Long id, Task task) {
        logger.info("Iniciando atualização de task: id={}", id);
        try {
            TaskEntity taskEntity = findTaskById(id);
            updateTaskEntity(taskEntity, task);

            TaskEntity updatedTaskEntity = taskRepository.save(taskEntity);
            Task updatedTask = taskMapper.toModel(updatedTaskEntity);
            logger.info("Task atualizada com sucesso para o id: {}", updatedTask.getId());
            return updatedTask;
        } catch (BusinessException e) {
            logger.error("Erro de negócio ao atualizar task, error={}",e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("Erro inesperado ao atualizar task, error={}",e.getMessage(), e);
            throw new BusinessException("task.update.failed");
        }
    }

    private TaskEntity findTaskById(Long id){
        return taskRepository.findById(id).orElseThrow(()->{
            logger.info("Task nao encontrada para atualização: id={}", id);
            return new BusinessException("task.update.not.found", id);
        });
    }

    private void updateTaskEntity(TaskEntity taskEntity, Task task) {
        taskMapper.updateEntity(taskEntity, task);
    }
}
