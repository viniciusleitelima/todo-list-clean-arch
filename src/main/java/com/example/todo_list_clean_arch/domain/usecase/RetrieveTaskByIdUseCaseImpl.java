package com.example.todo_list_clean_arch.domain.usecase;

import com.example.todo_list_clean_arch.adapter.repository.TaskRepository;
import com.example.todo_list_clean_arch.domain.exception.BusinessException;
import com.example.todo_list_clean_arch.domain.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RetrieveTaskByIdUseCaseImpl implements RetrieveTaskByIdUseCase{

    private static final Logger logger = LoggerFactory.getLogger(RetrieveTaskByIdUseCaseImpl.class);

    private final TaskRepository taskRepository;

    public RetrieveTaskByIdUseCaseImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    @Override
    public Task execute(String id) {
        logger.info("Iniciando busca de task: id={}", id);
        try {
            Task task = taskRepository.findById(id);
            logger.info("Task encontrada com sucesso para o id: {}", task.getId());
            return task;
        } catch (BusinessException e) {
            logger.error("Erro de negócio ao recuperar task, error={}",e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("Erro inesperado ao recuperar task, error={}",e.getMessage(), e);
            throw new BusinessException("task.retrieve.failed");
        }
    }
}
