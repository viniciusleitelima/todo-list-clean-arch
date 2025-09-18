package com.example.todo_list_clean_arch.domain.usecase;

import com.example.todo_list_clean_arch.adapter.repository.TaskRepository;
import com.example.todo_list_clean_arch.domain.exception.BusinessException;
import com.example.todo_list_clean_arch.domain.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeleteUseCaseImpl implements DeleteTaskUseCase{

    private static final Logger logger = LoggerFactory.getLogger(DeleteUseCaseImpl.class);

    private final TaskRepository taskRepository;

    public DeleteUseCaseImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void execute(String id) {
        logger.info("Iniciando exclusão de task");
        try {
            Task task = taskRepository.findById(id);
            taskRepository.delete(task);
            logger.info("Task excluida com sucesso para o id: {}", id);
        } catch (BusinessException e) {
            logger.error("Erro de negócio ao excluir task: id={}, error={}", id, e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("Erro inesperado ao excluir task: id={}, error={}", id, e.getMessage(), e);
            throw new BusinessException("task.delete.failed");
        }
    }
}
