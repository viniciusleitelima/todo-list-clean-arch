package com.example.todo_list_clean_arch.infra.mapper;

import com.example.todo_list_clean_arch.domain.model.Task;
import com.example.todo_list_clean_arch.infra.dto.TaskDTO;
import com.example.todo_list_clean_arch.infra.persistence.document.TaskDocument;
import com.example.todo_list_clean_arch.infra.persistence.entity.TaskEntity;

public interface TaskMapper {

    Task toModel(TaskEntity taskEntity);

    Task toModel(TaskDocument taskDocument);

    Task toModel(TaskDTO taskDTO);

    TaskEntity toEntity(Task task);

    TaskDocument toDocument(Task task);

    TaskDTO fromModel(Task task);

    void updateEntity(TaskEntity entity, Task task);
    void updateDocument(TaskDocument taskDocument, Task task);

}
