package com.example.todo_list_clean_arch.infra.config;

import com.example.todo_list_clean_arch.adapter.repository.TaskRepository;
import com.example.todo_list_clean_arch.infra.mapper.TaskMapper;
import com.example.todo_list_clean_arch.infra.persistence.repository.jpa.TaskJpaRepository;
import com.example.todo_list_clean_arch.infra.persistence.repository.mongo.TaskMongoRepository;
import com.example.todo_list_clean_arch.infra.persistence.repository.jpa.TaskRepositoryJpaImpl;
import com.example.todo_list_clean_arch.infra.persistence.repository.mongo.TaskRepositoryMongoImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DatabaseConfig {

    @Bean
    @ConditionalOnProperty(value = "app.db.type", havingValue = "jpa", matchIfMissing = true)
    @Primary
    public TaskRepository jpaTaskRepository(TaskJpaRepository taskRepository, TaskMapper taskMapper) {
        return new TaskRepositoryJpaImpl(taskRepository, taskMapper);
    }

    @Bean
    @ConditionalOnProperty(value = "app.db.type", havingValue = "mongo")
    public TaskRepository mongoTaskRepository(TaskMongoRepository taskRepository, TaskMapper taskMapper) {
        return new TaskRepositoryMongoImpl(taskRepository, taskMapper);

    }
}
