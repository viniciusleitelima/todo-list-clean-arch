package com.example.todo_list_clean_arch.domain.usecase;

import com.example.todo_list_clean_arch.adapter.repository.TaskRepository;
import com.example.todo_list_clean_arch.domain.enums.StatusEnum;
import com.example.todo_list_clean_arch.domain.exception.BusinessException;
import com.example.todo_list_clean_arch.domain.model.Task;
import com.example.todo_list_clean_arch.infra.mapper.TaskMapper;
import com.example.todo_list_clean_arch.infra.persistence.entity.TaskEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompleteTaskUseCaseImpl implements CompleteTaskUseCase{

    private static final Logger logger = LoggerFactory.getLogger(CompleteTaskUseCaseImpl.class);

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public CompleteTaskUseCaseImpl(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public Task execute(Long id) {
        logger.info("Iniciando a completude da task");
        try {
            TaskEntity taskEntity = findTaskById(id);
            taskEntity.setStatus(StatusEnum.DONE.name());
            TaskEntity taskEntitySaved = taskRepository.save(taskEntity);
            Task taskSaved = taskMapper.toModel(taskEntitySaved);
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

    private TaskEntity findTaskById(Long id){
        return taskRepository.findById(id).orElseThrow(()->{
            logger.info("Task nao encontrada para finalização: id={}", id);
            return new BusinessException("task.complete.not.found", id);
        });
    }
}
