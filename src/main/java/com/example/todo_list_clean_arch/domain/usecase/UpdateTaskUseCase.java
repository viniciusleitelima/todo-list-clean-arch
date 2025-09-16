package com.example.todo_list_clean_arch.domain.usecase;

import com.example.todo_list_clean_arch.domain.model.Task;

public interface UpdateTaskUseCase {

    Task execute(Long id, Task task);
}
