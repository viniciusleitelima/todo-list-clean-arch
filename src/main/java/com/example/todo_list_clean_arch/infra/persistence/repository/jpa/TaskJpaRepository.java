package com.example.todo_list_clean_arch.infra.persistence.repository.jpa;

import com.example.todo_list_clean_arch.infra.persistence.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskJpaRepository extends JpaRepository<TaskEntity, String> {
}
