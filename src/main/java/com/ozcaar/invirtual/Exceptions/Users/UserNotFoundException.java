package com.ozcaar.invirtual.Exceptions.Users;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}