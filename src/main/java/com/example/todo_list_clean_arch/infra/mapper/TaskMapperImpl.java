package com.example.todo_list_clean_arch.infra.mapper;

import com.example.todo_list_clean_arch.domain.enums.StatusEnum;
import com.example.todo_list_clean_arch.domain.model.Task;
import com.example.todo_list_clean_arch.infra.dto.TaskDTO;
import com.example.todo_list_clean_arch.infra.persistence.document.TaskDocument;
import com.example.todo_list_clean_arch.infra.persistence.entity.TaskEntity;

import java.time.LocalDateTime;

public class TaskMapperImpl implements TaskMapper {
    @Override
    public Task toModel(TaskEntity taskEntity) {
        Task task = new Task();
        task.setId(taskEntity.getId());
        task.setAuthor(taskEntity.getAuthor());
        task.setStatus(StatusEnum.valueOf(taskEntity.getStatus()));
        task.setTitle(taskEntity.getTitle());
        task.setDueDate(taskEntity.getDueDate());
        task.setCreatedAt(taskEntity.getCreatedAt());
        task.setUpdatedAt(taskEntity.getUpdatedAt());
        return task;
    }

    @Override
    public Task toModel(TaskDocument taskDocument) {
        Task task = new Task();
        task.setId(taskDocument.getId());
        task.setAuthor(taskDocument.getAuthor());
        task.setStatus(StatusEnum.valueOf(taskDocument.getStatus()));
        task.setTitle(taskDocument.getTitle());
        task.setDueDate(taskDocument.getDueDate());
        task.setCreatedAt(taskDocument.getCreatedAt());
        task.setUpdatedAt(taskDocument.getUpdatedAt());
        return task;
    }

    @Override
    public TaskEntity toEntity(Task task) {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setId(task.getId());
        taskEntity.setAuthor(task.getAuthor());
        taskEntity.setStatus(task.getStatus().name());
        taskEntity.setTitle(task.getTitle());
        taskEntity.setDueDate(task.getDueDate());
        taskEntity.setCreatedAt(task.getCreatedAt());
        taskEntity.setUpdatedAt(task.getUpdatedAt());
        return taskEntity;
    }

    @Override
    public TaskDocument toDocument(Task task) {
        TaskDocument taskDocument = new TaskDocument();
        taskDocument.setId(task.getId());
        taskDocument.setAuthor(task.getAuthor());
        taskDocument.setStatus(task.getStatus().name());
        taskDocument.setTitle(task.getTitle());
        taskDocument.setDueDate(task.getDueDate());
        taskDocument.setCreatedAt(task.getCreatedAt());
        taskDocument.setUpdatedAt(task.getUpdatedAt());
        return taskDocument;
    }

    @Override
    public Task toModel(TaskDTO taskDTO) {
        Task task = new Task();
        task.setId(taskDTO.getId());
        task.setAuthor(taskDTO.getAuthor());
        task.setStatus(taskDTO.getStatus());
        task.setTitle(taskDTO.getTitle());
        task.setDueDate(taskDTO.getDueDate());
        task.setUpdatedAt(taskDTO.getUpdatedAt());
        task.setCreatedAt(taskDTO.getCreatedAt());
        return task;
    }

    @Override
    public TaskDTO fromModel(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(task.getId());
        taskDTO.setAuthor(task.getAuthor());
        taskDTO.setStatus(task.getStatus());
        taskDTO.setTitle(task.getTitle());
        taskDTO.setDueDate(task.getDueDate());
        taskDTO.setCreatedAt(task.getCreatedAt());
        taskDTO.setUpdatedAt(task.getUpdatedAt());
        return taskDTO;
    }

    @Override
    public void updateEntity(TaskEntity entity, Task task) {
        if (entity == null || task == null) {
            return;
        }
        if (task.getAuthor() != null) {
            entity.setAuthor(task.getAuthor());
        }
        if (task.getTitle() != null) {
            entity.setTitle(task.getTitle());
        }
        if (task.getStatus() != null) {
            entity.setStatus(task.getStatus().name());
        }
        if (task.getDueDate() != null) {
            entity.setDueDate(task.getDueDate());
        }
        entity.setUpdatedAt(LocalDateTime.now());
    }

    @Override
    public void updateDocument(TaskDocument taskDocument, Task task) {
        if (taskDocument == null || task == null) {
            return;
        }
        if (task.getAuthor() != null) {
            taskDocument.setAuthor(task.getAuthor());
        }
        if (task.getTitle() != null) {
            taskDocument.setTitle(task.getTitle());
        }
        if (task.getStatus() != null) {
            taskDocument.setStatus(task.getStatus().name());
        }
        if (task.getDueDate() != null) {
            taskDocument.setDueDate(task.getDueDate());
        }
        taskDocument.setUpdatedAt(LocalDateTime.now());
    }

}
