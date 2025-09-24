package com.example.todo_list_clean_arch.domain.usecase;

import com.example.todo_list_clean_arch.domain.repository.TaskRepository;
import com.example.todo_list_clean_arch.domain.exception.BusinessException;
import com.example.todo_list_clean_arch.domain.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateTaskUseCaseImpl implements UpdateTaskUseCase {

    private static final Logger logger = LoggerFactory.getLogger(UpdateTaskUseCaseImpl.class);

    private final TaskRepository taskRepository;

    public UpdateTaskUseCaseImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task execute(String id, Task oldTask) {
        logger.info("Iniciando atualização de task: id={}", id);
        try {
            Task updatedTask = taskRepository.updateTask(oldTask, id);
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

}
