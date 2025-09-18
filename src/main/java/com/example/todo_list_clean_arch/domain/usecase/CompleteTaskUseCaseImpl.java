package com.example.todo_list_clean_arch.domain.usecase;

import com.example.todo_list_clean_arch.adapter.repository.TaskRepository;
import com.example.todo_list_clean_arch.domain.enums.StatusEnum;
import com.example.todo_list_clean_arch.domain.exception.BusinessException;
import com.example.todo_list_clean_arch.domain.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompleteTaskUseCaseImpl implements CompleteTaskUseCase{

    private static final Logger logger = LoggerFactory.getLogger(CompleteTaskUseCaseImpl.class);

    private final TaskRepository taskRepository;

    public CompleteTaskUseCaseImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task execute(String id) {
        logger.info("Iniciando a completude da task");
        try {
            Task task = taskRepository.findById(id);
            task.setStatus(StatusEnum.DONE);
            Task taskSaved = taskRepository.save(task);
            logger.info("Task com a completude salva com sucesso para o id: {}", taskSaved.getId());
            return taskSaved;
        } catch (BusinessException e) {
            logger.error("Erro de negócio ao completar task: id={}, error={}", id, e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("Erro inesperado ao completar task: id={}, error={}", id, e.getMessage(), e);
            throw new BusinessException("task.complete.failed");
        }
    }
}
