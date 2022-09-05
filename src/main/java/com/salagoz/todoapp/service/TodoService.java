package com.salagoz.todoapp.service;

import com.salagoz.todoapp.model.Todo;

import java.util.List;

public interface TodoService {
    Todo saveTodo(String username, Todo todo);

    List<Todo> getTodos(String username);

    Todo changeDoneStatus(String username, String todoId);

    void deleteTodo(String username, String todoId);
}
