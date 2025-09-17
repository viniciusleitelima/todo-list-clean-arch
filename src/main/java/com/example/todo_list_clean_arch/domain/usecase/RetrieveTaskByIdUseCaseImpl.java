package com.example.todo_list_clean_arch.domain.usecase;

import com.example.todo_list_clean_arch.adapter.repository.TaskRepository;
import com.example.todo_list_clean_arch.domain.exception.BusinessException;
import com.example.todo_list_clean_arch.domain.model.Task;
import com.example.todo_list_clean_arch.infra.mapper.TaskMapper;
import com.example.todo_list_clean_arch.infra.persistence.entity.TaskEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RetrieveTaskByIdUseCaseImpl implements RetrieveTaskByIdUseCase{

    private static final Logger logger = LoggerFactory.getLogger(RetrieveTaskByIdUseCaseImpl.class);

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public RetrieveTaskByIdUseCaseImpl(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }
    @Override
    public Task execute(String id) {
        logger.info("Iniciando busca de task: id={}", id);
        try {
            TaskEntity taskEntity = findTaskById(id);
            Task task = taskMapper.toModel(taskEntity);
            logger.info("Task encontrada com sucesso para o id: {}", taskEntity.getId());
            return task;
        } catch (BusinessException e) {
            logger.error("Erro de negócio ao recuperar task, error={}",e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("Erro inesperado ao recuperar task, error={}",e.getMessage(), e);
            throw new BusinessException("task.retrieve.failed");
        }
    }

    private TaskEntity findTaskById(String id){
        return taskRepository.findById(id).orElseThrow(()->{
            logger.info("Task nao encontrada para recuperação: id={}", id);
            return new BusinessException("task.retrieve.not.found", id);
        });
    }
}
