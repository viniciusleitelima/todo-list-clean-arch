package com.example.todo_list_clean_arch.domain.repository;

import com.example.todo_list_clean_arch.domain.model.Task;
import java.util.List;

public interface TaskRepository {
    Task save(Task task);
    Task findById(String id);
    List<Task> findAll();
    void delete(Task task);
    Task updateTask(Task updateTask, String id);
}
