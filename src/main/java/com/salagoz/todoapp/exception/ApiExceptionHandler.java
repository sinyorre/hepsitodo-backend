package com.salagoz.todoapp.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleException(Exception exception) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        ApiException apiException = new ApiException(
                exception.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler(value = TodoNotFoundException.class)
    public ResponseEntity<Object> handleProductNotFoundException(TodoNotFoundException exception) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        ApiException apiException = new ApiException(
                exception.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler(value = UsernameExistException.class)
    public ResponseEntity<Object> handleUsernameExistException(UsernameExistException exception) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        ApiException apiException = new ApiException(
                exception.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler(value = LoginException.class)
    public ResponseEntity<Object> handleLoginExistException(LoginException exception) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        ApiException apiException = new ApiException(
                exception.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(apiException, badRequest);
    }

}
