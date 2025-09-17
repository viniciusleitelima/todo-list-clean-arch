package com.example.todo_list_clean_arch.adapter.repository;

import com.example.todo_list_clean_arch.infra.persistence.entity.TaskEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepository {
    TaskEntity save(TaskEntity taskEntity);
    Optional<TaskEntity> findById(String id);
    List<TaskEntity> findAll();
    void delete(TaskEntity taskEntity);
}
