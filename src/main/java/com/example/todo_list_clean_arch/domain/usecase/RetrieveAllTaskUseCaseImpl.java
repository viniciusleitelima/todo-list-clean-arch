package com.example.todo_list_clean_arch.domain.usecase;

import com.example.todo_list_clean_arch.adapter.repository.TaskRepository;
import com.example.todo_list_clean_arch.domain.exception.BusinessException;
import com.example.todo_list_clean_arch.domain.model.Task;
import com.example.todo_list_clean_arch.infra.mapper.TaskMapper;
import com.example.todo_list_clean_arch.infra.persistence.entity.TaskEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class RetrieveAllTaskUseCaseImpl implements RetrieveAllTaskUseCase {

    private static final Logger logger = LoggerFactory.getLogger(RetrieveAllTaskUseCaseImpl.class);

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public RetrieveAllTaskUseCaseImpl(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }
    @Override
    public List<Task> execute() {
        logger.info("Iniciando busca de todas as tasks");
        try {
            List<TaskEntity> taskEntityList = taskRepository.findAll();
            List<Task> taskList = taskEntityList.stream().map(taskMapper::toModel).toList();
            logger.info("Foram encontradas o total de: {} tasks", taskList.size());
            return taskList;
        } catch (Exception e) {
            logger.error("Erro ao buscar tasks: error={}", e.getMessage(), e);
            throw new BusinessException("task.retrieval.failed");
        }
    }
}
