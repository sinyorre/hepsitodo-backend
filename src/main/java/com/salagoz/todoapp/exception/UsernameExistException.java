package com.salagoz.todoapp.exception;

public class UsernameExistException extends RuntimeException{
    public UsernameExistException(String message) {
        super(message);
    }

    public UsernameExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
