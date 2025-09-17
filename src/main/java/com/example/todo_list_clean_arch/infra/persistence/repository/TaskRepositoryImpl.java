package com.example.todo_list_clean_arch.infra.persistence.repository;

import com.example.todo_list_clean_arch.adapter.repository.TaskRepository;
import com.example.todo_list_clean_arch.infra.persistence.entity.TaskEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TaskRepositoryImpl implements TaskRepository {

    private final TaskJpaRepository taskJpaRepository;

    public TaskRepositoryImpl(TaskJpaRepository jpaRepository) {
        this.taskJpaRepository = jpaRepository;
    }

    @Override
    public TaskEntity save(TaskEntity taskEntity) {
        return taskJpaRepository.save(taskEntity);
    }

    @Override
    public Optional<TaskEntity> findById(String id) {
        return taskJpaRepository.findById(id);
    }

    @Override
    public List<TaskEntity> findAll() {
        return taskJpaRepository.findAll();
    }

    @Override
    public void delete(TaskEntity taskEntity) { taskJpaRepository.delete(taskEntity);}
}
