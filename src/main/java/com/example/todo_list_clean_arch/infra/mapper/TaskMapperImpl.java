package com.example.todo_list_clean_arch.infra.mapper;

import com.example.todo_list_clean_arch.domain.enums.StatusEnum;
import com.example.todo_list_clean_arch.domain.mapper.TaskMapper;
import com.example.todo_list_clean_arch.domain.model.Task;
import com.example.todo_list_clean_arch.infra.dto.TaskDTO;
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
    public TaskDTO fromEntity(TaskEntity taskEntity) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setAuthor(taskEntity.getAuthor());
        taskDTO.setId(taskEntity.getId());
        taskDTO.setStatus(StatusEnum.valueOf(taskEntity.getStatus()));
        taskDTO.setTitle(taskEntity.getTitle());
        taskDTO.setDueDate(taskEntity.getDueDate());
        taskDTO.setCreatedAt(taskEntity.getCreatedAt());
        taskDTO.setUpdatedAt(taskEntity.getUpdatedAt());
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

}
