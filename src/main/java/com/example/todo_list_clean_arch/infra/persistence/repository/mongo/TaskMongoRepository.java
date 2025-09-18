package com.example.todo_list_clean_arch.infra.persistence.repository.mongo;

import com.example.todo_list_clean_arch.infra.persistence.document.TaskDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskMongoRepository extends MongoRepository<TaskDocument, String> {
}
