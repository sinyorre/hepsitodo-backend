package com.salagoz.todoapp.service;

import com.salagoz.todoapp.auth.TokenManager;
import com.salagoz.todoapp.exception.LoginException;
import com.salagoz.todoapp.exception.UsernameExistException;
import com.salagoz.todoapp.model.LoginRequest;
import com.salagoz.todoapp.model.SignUpRequest;
import com.salagoz.todoapp.model.Todo;
import com.salagoz.todoapp.model.UserEntity;
import com.salagoz.todoapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @InjectMocks
    UserServiceImpl userService;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    TokenManager tokenManager;

    @Mock
    UserRepository userRepository;

    @Test
    void shouldRegisterUser() {
        // Given
        String dummyPassword = "test-password";
        SignUpRequest dummySignUpRequest = SignUpRequest
                .builder()
                .username("dummy-username")
                .email("dummy-email")
                .build();
        UserEntity dummyUser = UserEntity.builder()
                .username("dummy-username")
                .email("dummy-email")
                .build();
        when(userRepository.save(any(UserEntity.class)))
                .thenReturn(dummyUser);

        // When
        userService.registerUser(dummySignUpRequest);

        // Then
        assertAll("Should save todo",
                () -> verify(userRepository, times(1)).save(any(UserEntity.class))
        );
    }

    @Test
    void checkUsernameExist() {
        // Given
        String dummyUsername = "test-user";
        when(userRepository.existsByUsername(anyString()))
                .thenReturn(true);

        // When
        Exception exception = assertThrows(UsernameExistException.class, () -> {
            userService.checkUsernameExist(dummyUsername);
        });

        // Then
        String expectedMessage = "Username exist";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void userShouldLogin() {
        // Given
        LoginRequest dummyLoginRequest = LoginRequest.builder()
                .username("test-user")
                .password("test-password")
                .build();
        when(authenticationManager.authenticate(any(Authentication.class)))
                .thenReturn(null);
        when(tokenManager.generateToken(anyString()))
                .thenReturn("test-token");

        // When
        String token = userService.login(dummyLoginRequest);

        // Then
        assertAll("user should login",
                () -> assertEquals(token, "test-token")
        );
    }

    @Test
    void shouldThrowLoginException_whenLogin() {
        // Given
        LoginRequest dummyLoginRequest = LoginRequest.builder()
                .username("test-user")
                .password("test-password")
                .build();
        when(authenticationManager.authenticate(any(Authentication.class)))
                .thenThrow(new LoginException("login-exception"));

        // When
        Exception exception = assertThrows(LoginException.class, () -> {
            userService.login(dummyLoginRequest);
        });

        // Then
        String expectedMessage = "login-exception";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void shouldAddTodo() {
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
                .todos(new ArrayList<>())
                .build();
        when(userRepository.findByUsername(anyString()))
                .thenReturn(Optional.of(dummyUser));
        when(userRepository.save(any(UserEntity.class)))
                .thenReturn(dummyUser);

        // When
        userService.addTodo(dummyUsername, dummyTodo);

        // Then
        assertAll("user should add todo",
                () -> verify(userRepository, times(1)).save(any(UserEntity.class))
        );
    }

    @Test
    void shouldChangeDoneStatus() {
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
                .todos(new ArrayList<Todo>() {{
                    add(dummyTodo);
                }})
                .build();
        when(userRepository.findByUsername(anyString()))
                .thenReturn(Optional.of(dummyUser));

        // When
        userService.changeDoneStatus(dummyUsername, dummyTodo);

        // Then
        assertAll("user should change todo status",
                () -> verify(userRepository, times(1)).save(any(UserEntity.class))
        );
    }

    @Test
    void shouldDeleteTodo() {
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
                .todos(new ArrayList<Todo>() {{
                    add(dummyTodo);
                }})
                .build();
        when(userRepository.findByUsername(anyString()))
                .thenReturn(Optional.of(dummyUser));

        // When
        userService.deleteTodo(dummyUsername, dummyTodo);

        // Then
        assertAll("user should delete todo",
                () -> verify(userRepository, times(1)).save(any(UserEntity.class))
        );
    }

    @Test
    void shouldGetUser() {
        // Given
        String dummyUsername = "test-user";
        UserEntity dummyUser = UserEntity
                .builder()
                .build();
        when(userRepository.findByUsername(anyString()))
                .thenReturn(Optional.of(dummyUser));

        // When
        userService.getUser(dummyUsername);

        // Then
        assertAll("user should delete todo",
                () -> verify(userRepository, times(1)).findByUsername(anyString())
        );
    }

    @Test
    void throwUsernameNotFoundException_whenGetUser() {
        // Given
        String dummyUsername = "test-user";
        UserEntity dummyUser = UserEntity
                .builder()
                .build();
        when(userRepository.findByUsername(anyString()))
                .thenReturn(Optional.empty());

        // When
        Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
            userService.getUser(dummyUsername);
        });

        // Then
        String expectedMessage = "User not found with user name : " + dummyUsername;
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }
}