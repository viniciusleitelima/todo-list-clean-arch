package com.example.todo_list_clean_arch.domain.mapper;

import com.example.todo_list_clean_arch.domain.model.Task;
import com.example.todo_list_clean_arch.infra.dto.TaskDTO;
import com.example.todo_list_clean_arch.infra.persistence.entity.TaskEntity;

public interface TaskMapper {

    Task toModel(TaskEntity taskEntity);

    TaskEntity toEntity(Task task);

    Task toModel(TaskDTO taskDTO);

    TaskDTO fromModel(Task task);

    TaskDTO fromEntity(TaskEntity taskEntity);

    void updateEntity(TaskEntity entity, Task task);

}
