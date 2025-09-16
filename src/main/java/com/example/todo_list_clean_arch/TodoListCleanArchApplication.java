package com.example.todo_list_clean_arch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TodoListCleanArchApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoListCleanArchApplication.class, args);
	}

}
