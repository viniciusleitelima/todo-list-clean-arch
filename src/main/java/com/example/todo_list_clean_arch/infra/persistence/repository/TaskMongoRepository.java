package com.example.todo_list_clean_arch.infra.persistence.repository;

import com.example.todo_list_clean_arch.infra.persistence.entity.TaskEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskMongoRepository extends MongoRepository<TaskEntity, String> {
}
