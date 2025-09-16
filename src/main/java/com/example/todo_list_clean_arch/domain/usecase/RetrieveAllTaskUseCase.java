package com.example.todo_list_clean_arch.domain.usecase;

import com.example.todo_list_clean_arch.domain.model.Task;

import java.util.List;

public interface RetrieveAllTaskUseCase {
    List<Task> execute();
}
