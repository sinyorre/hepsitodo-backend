package com.salagoz.todoapp.service;

import com.salagoz.todoapp.exception.TodoNotFoundException;
import com.salagoz.todoapp.model.Todo;
import com.salagoz.todoapp.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {
    private final TodoRepository todoRepository;
    private final UserService userService;

    @Override
    public Todo saveTodo(String username, Todo todo) {
        Todo savedTodo = todoRepository.save(todo);
        userService.addTodo(username, savedTodo);
        return savedTodo;
    }

    @Override
    public List<Todo> getTodos(String username) {
        return userService.getUser(username).getTodos();
    }

    @Override
    public Todo changeDoneStatus(String username, String todoId) {
        Todo todo = getTodoById(todoId);
        userService.changeDoneStatus(username, todo);
        todo.setDone(!todo.isDone());
        return todoRepository.save(todo);
    }

    @Override
    public void deleteTodo(String username, String todoId) {
        Todo todo = getTodoById(todoId);
        userService.deleteTodo(username, todo);
        todoRepository.delete(todo);
    }

    public Todo getTodoById(String todoId) {
        return todoRepository.findById(todoId)
                .orElseThrow(() ->
                        new TodoNotFoundException("Todo not found with id : " + todoId)
                );
    }
}
