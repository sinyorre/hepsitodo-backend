package com.salagoz.todoapp.service;

import com.salagoz.todoapp.exception.TodoNotFoundException;
import com.salagoz.todoapp.model.Todo;
import com.salagoz.todoapp.model.UserEntity;
import com.salagoz.todoapp.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TodoServiceTest {
    @InjectMocks
    TodoServiceImpl todoService;

    @Mock
    TodoRepository todoRepository;

    @Mock
    UserService userService;

    @Test
    void shouldSaveTodo() {
        // Given
        String dummyUsername = "test-user";
        Todo dummyTodo = Todo
                .builder()
                .title("test")
                .description("test")
                .done(false)
                .build();

        UserEntity dummyUser = UserEntity
                .builder()
                .build();
        when(todoRepository.save(any(Todo.class)))
                .thenReturn(dummyTodo);
        when(userService.addTodo(anyString(), any(Todo.class)))
                .thenReturn(dummyUser);

        // When
        Todo todo = todoService.saveTodo(dummyUsername, dummyTodo);

        // Then
        assertAll("Should save todo",
                () -> verify(todoRepository, times(1)).save(any(Todo.class)),
                () -> verify(userService, times(1)).addTodo(anyString(), any(Todo.class)),
                () -> assertEquals(dummyTodo.getTitle(), todo.getTitle()),
                () -> assertEquals(dummyTodo.getDescription(), todo.getDescription())
        );
    }

    @Test
    void shouldGetTodos() {
        // Given
        String dummyUsername = "test-user";
        Todo dummyTodo1 = Todo
                .builder()
                .title("test1")
                .description("test1")
                .done(false)
                .build();
        Todo dummyTodo2 = Todo
                .builder()
                .title("test2")
                .description("test2")
                .done(true)
                .build();
        UserEntity dummyUser = UserEntity.builder()
                .username("test")
                .email("test")
                .todos(new ArrayList<Todo>() {{
                    add(dummyTodo1);
                    add(dummyTodo2);
                }})
                .build();

        when(userService.getUser(anyString()))
                .thenReturn(dummyUser);

        // When
        List<Todo> todos = todoService.getTodos(dummyUsername);

        // Then
        assertAll("Should get todos",
                () -> verify(userService, times(1)).getUser(anyString()),
                () -> assertEquals(2, todos.size()),
                () -> assertEquals(dummyTodo1.getTitle(), todos.get(0).getTitle()),
                () -> assertEquals(dummyTodo2.isDone(), todos.get(1).isDone())
        );
    }

    @Test
    void shouldChangeDoneStatus() {
        // Given
        String dummyUsername = "test-user";
        String dummyTodoId = "test-id";
        Todo dummyTodo = Todo
                .builder()
                .title("test")
                .description("test")
                .done(false)
                .build();
        Todo updatedDummyTodo = Todo
                .builder()
                .title("test")
                .description("test")
                .done(true)
                .build();
        UserEntity dummyUser = UserEntity.builder().build();
        when(todoRepository.findById(anyString()))
                .thenReturn(Optional.of(dummyTodo));
        when(userService.changeDoneStatus(anyString(), any(Todo.class)))
                .thenReturn(dummyUser);
        when(todoRepository.save(any(Todo.class)))
                .thenReturn(updatedDummyTodo);

        // When
        Todo todo = todoService.changeDoneStatus(dummyUsername, dummyTodoId);

        // Then
        assertAll("Should change done status",
                () -> verify(todoRepository, times(1)).findById(anyString()),
                () -> verify(userService, times(1)).changeDoneStatus(anyString(), any(Todo.class)),
                () -> verify(todoRepository, times(1)).save(any(Todo.class)),
                () -> assertEquals(todo.isDone(), updatedDummyTodo.isDone()),
                () -> assertEquals(todo.getTitle(), dummyTodo.getTitle()),
                () -> assertEquals(todo.getDescription(), dummyTodo.getDescription())
        );
    }

    @Test
    void shouldGetTodo() {
        // Given
        String dummyTodoId = "test-id";
        Todo dummyTodo = Todo
                .builder()
                .title("test")
                .description("test")
                .done(false)
                .build();
        when(todoRepository.findById(anyString()))
                .thenReturn(Optional.of(dummyTodo));

        // When
        Todo todo = todoService.getTodoById(dummyTodoId);

        // Then
        assertAll("Should get todo",
                () -> verify(todoRepository, times(1)).findById(anyString()),
                () -> assertEquals(todo.isDone(), dummyTodo.isDone()),
                () -> assertEquals(todo.getTitle(), dummyTodo.getTitle()),
                () -> assertEquals(todo.getDescription(), dummyTodo.getDescription())
        );
    }


    @Test
    void shouldThrowTodoNotFoundException_WhenGetTodo() {
        // Given
        String dummyTodoId = "test-id";
        when(todoRepository.findById(anyString()))
                .thenReturn(Optional.empty());

        // When
        Exception exception = assertThrows(TodoNotFoundException.class, () -> {
            todoService.getTodoById(dummyTodoId);
        });

        // Then
        String expectedMessage = "Todo not found with id : " + dummyTodoId;
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void shouldDeleteTodo(){
        // Given
        String dummyUsername = "test-user";
        String dummyTodoId = "test-id";
        Todo dummyTodo = Todo
                .builder()
                .title("test")
                .description("test")
                .done(false)
                .build();

        when(todoRepository.findById(anyString()))
                .thenReturn(Optional.of(dummyTodo));
        when(userService.deleteTodo(anyString(), any(Todo.class)))
                .thenReturn(UserEntity.builder().build());

        // When
        todoService.deleteTodo(dummyUsername, dummyTodoId);

        // Then
        assertAll("Should delete todo",
                () -> verify(todoRepository, times(1)).delete(any(Todo.class))
        );
    }
}