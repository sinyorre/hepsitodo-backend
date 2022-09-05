package com.salagoz.todoapp.service;

import com.salagoz.todoapp.model.SignUpRequest;
import com.salagoz.todoapp.model.Todo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@IT
public class TodoServiceIT {

    @Autowired
    TodoService todoService;

    @Autowired
    UserService userService;

    @BeforeEach
    public void initialize() {
        try {
            SignUpRequest signUpRequest = SignUpRequest.builder()
                    .username("test-user")
                    .password("test-password")
                    .build();
            userService.registerUser(signUpRequest);
        } catch (Exception e) {
            userService.deleteAllTodos("test-user");
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void shouldSaveTodo() {
        // Given
        String dummyUsername = "test-user";
        Todo dummyTodo = Todo
                .builder()
                .title("test")
                .description("test")
                .done(false)
                .build();

        // When
        Todo todo = todoService.saveTodo(dummyUsername, dummyTodo);

        // Then
        assertNotNull(todo.getId());
    }

    @Test
    public void shouldGetTodos() {
        // Given
        String dummyUsername = "test-user";
        Todo dummyTodo = Todo
                .builder()
                .title("test")
                .description("test")
                .done(false)
                .build();

        // When
        List<Todo> todos = todoService.getTodos(dummyUsername);

        // Then
        assertEquals(0, todos.size());
    }

    @Test
    public void changeDoneStatus() {
        // Given
        String dummyUsername = "test-user";

        Todo dummyTodo = Todo
                .builder()
                .title("test")
                .description("test")
                .done(false)
                .build();

        // When
        Todo savedTodo = todoService.saveTodo(dummyUsername, dummyTodo);
        String todoId = savedTodo.getId();
        Todo todo = todoService.changeDoneStatus(dummyUsername, todoId);

        // Then
        assertTrue(todo.isDone());
    }

    @Test
    public void deleteTodo() {
        // Given
        String dummyUsername = "test-user";

        Todo dummyTodo = Todo
                .builder()
                .title("test")
                .description("test")
                .done(false)
                .build();

        // When
        Todo savedTodo = todoService.saveTodo(dummyUsername, dummyTodo);
        String todoId = savedTodo.getId();
        todoService.deleteTodo(dummyUsername, todoId);

        // Then
        assertTrue(true);
    }
}