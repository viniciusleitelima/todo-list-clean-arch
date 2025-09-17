package com.example.todo_list_clean_arch.infra.persistence.repository;

import com.example.todo_list_clean_arch.adapter.repository.TaskRepository;
import com.example.todo_list_clean_arch.infra.persistence.entity.TaskEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class TaskRepositoryImpl implements TaskRepository {

    private final TaskMongoRepository taskMongoRepository;

    public TaskRepositoryImpl(TaskMongoRepository jpaRepository) {
        this.taskMongoRepository = jpaRepository;
    }

    @Override
    public TaskEntity save(TaskEntity taskEntity) {
        return taskMongoRepository.save(taskEntity);
    }

    @Override
    public Optional<TaskEntity> findById(String id) {
        return taskMongoRepository.findById(id);
    }

    @Override
    public List<TaskEntity> findAll() {
        return taskMongoRepository.findAll();
    }

    @Override
    public void delete(TaskEntity taskEntity) { taskMongoRepository.delete(taskEntity);}
}
