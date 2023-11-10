package com.cs362.tuffyride.exception;
/*
deal with duplicate usernames, throw an exception when a user tries to reuse an existing username.
 */
public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String message) {
        super(message);
    }
}
