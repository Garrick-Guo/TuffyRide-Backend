package com.cs362.tuffyride.exception;

public class RideNotExistException extends RuntimeException {
    public RideNotExistException(String message) {
        super(message);
    }
}
