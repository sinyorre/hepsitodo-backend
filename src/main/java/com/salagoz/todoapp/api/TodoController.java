package com.salagoz.todoapp.api;

import com.salagoz.todoapp.model.Todo;
import com.salagoz.todoapp.model.TodoRequest;
import com.salagoz.todoapp.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
@Tag(name = "TodoController", description = "Manages operations on Todo")
@CrossOrigin
public class TodoController {
    private final TodoService todoService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(description = "Save a todo")
    public ResponseEntity<Todo> saveTodo(@Valid @RequestBody TodoRequest todoRequest, Principal principal) {
        String username = principal.getName();
        Todo todo = todoService.saveTodo(username, todoRequest.toModel());
        return new ResponseEntity<>(todo, HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(description = "Get all todos")
    public ResponseEntity<List<Todo>> getTodos(Principal principal) {
        String username = principal.getName();
        List<Todo> todos = todoService.getTodos(username);
        return new ResponseEntity<>(todos, HttpStatus.OK);
    }

    @PutMapping(path = "/{todoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(description = "Change the done status for todo")
    public ResponseEntity<Todo> changeDoneStatus(@PathVariable String todoId, Principal principal) {
        String username = principal.getName();
        Todo todo = todoService.changeDoneStatus(username, todoId);
        return new ResponseEntity<>(todo, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{todoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(description = "Delete todo operation")
    public ResponseEntity<String> deleteTodo(@PathVariable String todoId, Principal principal) {
        String username = principal.getName();
        todoService.deleteTodo(username, todoId);
        return new ResponseEntity<>(todoId, HttpStatus.OK);
    }
}
