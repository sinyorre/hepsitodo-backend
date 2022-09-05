package com.salagoz.todoapp.repository;

import com.salagoz.todoapp.model.Todo;
import org.springframework.data.repository.CrudRepository;

public interface TodoRepository extends CrudRepository<Todo, String> {
}
