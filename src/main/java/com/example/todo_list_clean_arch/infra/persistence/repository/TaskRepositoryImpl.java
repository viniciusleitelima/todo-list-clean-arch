package com.example.todo_list_clean_arch.infra.persistence.repository;

import com.example.todo_list_clean_arch.adapter.repository.TaskRepository;
import com.example.todo_list_clean_arch.infra.persistence.entity.TaskEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TaskRepositoryImpl implements TaskRepository {

    private final TaskJpaRepository jpaRepository;

    public TaskRepositoryImpl(TaskJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public TaskEntity save(TaskEntity taskEntity) {
        return jpaRepository.save(taskEntity);
    }

    @Override
    public Optional<TaskEntity> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<TaskEntity> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public void delete(TaskEntity taskEntity) { jpaRepository.delete(taskEntity);}
}
