package com.salagoz.todoapp.service;

import com.salagoz.todoapp.auth.TokenManager;
import com.salagoz.todoapp.exception.LoginException;
import com.salagoz.todoapp.exception.UsernameExistException;
import com.salagoz.todoapp.model.LoginRequest;
import com.salagoz.todoapp.model.SignUpRequest;
import com.salagoz.todoapp.model.Todo;
import com.salagoz.todoapp.model.UserEntity;
import com.salagoz.todoapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenManager tokenManager;
    private final UserRepository userRepository;

    @Override
    public UserEntity registerUser(SignUpRequest signUpRequest) {
        checkUsernameExist(signUpRequest.getUsername());
        UserEntity userEntity = UserEntity
                .builder()
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .todos(new ArrayList<>())
                .build();
        return userRepository.save(userEntity);
    }

    @Override
    public void checkUsernameExist(String username) {
        boolean isUsernameExist = userRepository.existsByUsername(username);
        if (isUsernameExist) throw new UsernameExistException("Username exist");
    }

    @Override
    public String login(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            return tokenManager.generateToken(loginRequest.getUsername());
        } catch (Exception e) {
            throw new LoginException(e.getMessage());
        }
    }

    @Override
    public UserEntity addTodo(String username, Todo todo) {
        UserEntity userEntity = getUser(username);
        userEntity.getTodos().add(todo);
        return userRepository.save(userEntity);
    }

    @Override
    public UserEntity changeDoneStatus(String username, Todo todo) {
        UserEntity userEntity = getUser(username);
        List<Todo> userTodos = userEntity.getTodos();
        int index = userTodos.indexOf(todo);
        userTodos.get(index).setDone(!todo.isDone());
        return userRepository.save(userEntity);
    }

    @Override
    public UserEntity deleteTodo(String username, Todo todo) {
        UserEntity userEntity = getUser(username);
        List<Todo> userTodos = userEntity.getTodos();
        userTodos.remove(todo);
        return userRepository.save(userEntity);
    }

    @Override
    public UserEntity getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with user name : " + username)
                );
    }

    @Override
    public void deleteAllTodos(String username) {
        UserEntity user = getUser(username);
        user.setTodos(new ArrayList<>());
        userRepository.save(user);
    }
}
