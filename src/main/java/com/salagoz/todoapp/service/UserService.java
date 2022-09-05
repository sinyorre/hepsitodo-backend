package com.salagoz.todoapp.service;

import com.salagoz.todoapp.model.LoginRequest;
import com.salagoz.todoapp.model.SignUpRequest;
import com.salagoz.todoapp.model.Todo;
import com.salagoz.todoapp.model.UserEntity;

public interface UserService {
    UserEntity registerUser(SignUpRequest signUpRequest);
    void checkUsernameExist(String username);
    String login(LoginRequest loginRequest);

    UserEntity addTodo(String username, Todo todo);
    UserEntity changeDoneStatus(String username, Todo todo);
    UserEntity deleteTodo(String username, Todo todo);
    UserEntity getUser(String username);

    void deleteAllTodos(String username);
}
