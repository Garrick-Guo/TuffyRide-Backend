package com.cs362.tuffyride.exception;
/*
throw a UserNotExist exception when the given user credential is invalid
 */
public class UserNotExistException extends RuntimeException {
    public UserNotExistException(String message) {
        super(message);
    }
}
