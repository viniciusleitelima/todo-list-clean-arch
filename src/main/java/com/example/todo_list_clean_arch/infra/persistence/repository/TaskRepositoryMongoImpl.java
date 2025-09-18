package com.example.todo_list_clean_arch.infra.persistence.repository;

import com.example.todo_list_clean_arch.adapter.repository.TaskRepository;
import com.example.todo_list_clean_arch.domain.exception.BusinessException;
import com.example.todo_list_clean_arch.infra.mapper.TaskMapper;
import com.example.todo_list_clean_arch.domain.model.Task;
import com.example.todo_list_clean_arch.infra.persistence.document.TaskDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import java.time.LocalDateTime;
import java.util.List;

public class TaskRepositoryMongoImpl implements TaskRepository {
    private final TaskMongoRepository taskRepository;
    private final TaskMapper taskMapper;

    private static final Logger logger = LoggerFactory.getLogger(TaskRepositoryMongoImpl.class);

    public TaskRepositoryMongoImpl(TaskMongoRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }


    @Override
    public Task save(Task task) {
        TaskDocument taskDocumentSaved = taskRepository.save(taskMapper.toDocument(task));
        return taskMapper.toModel(taskDocumentSaved);
    }

    @Override
    public Task findById(String id) {
         TaskDocument taskDocumentRetrieve = taskRepository.findById(id).orElseThrow(()->{
            logger.info("Task nao encontrada para recuperação: id={}", id);
            return new BusinessException("task.retrieve.not.found", id);
        });
        return taskMapper.toModel(taskDocumentRetrieve);
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll().stream().map(taskMapper::toModel).toList();
    }

    @Override
    public void delete(Task task) {
        taskRepository.delete(taskMapper.toDocument(task));
    }

    @Override
    public Task updateTask(Task updateTask, String id) {
        Task oldTask = this.findById(id);

        if (updateTask.getAuthor() != null) {
            oldTask.setAuthor(updateTask.getAuthor());
        }
        if (updateTask.getTitle() != null) {
            oldTask.setTitle(updateTask.getTitle());
        }
        if (updateTask.getStatus() != null) {
            oldTask.setStatus(updateTask.getStatus());
        }
        if (updateTask.getDueDate() != null) {
            oldTask.setDueDate(updateTask.getDueDate());
        }
        oldTask.setUpdatedAt(LocalDateTime.now());
        return this.save(oldTask);
    }

}
