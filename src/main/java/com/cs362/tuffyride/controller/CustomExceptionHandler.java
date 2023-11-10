package com.cs362.tuffyride.controller;

import com.cs362.tuffyride.exception.UserAlreadyExistException;
import com.cs362.tuffyride.exception.UserNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice //make Spring use CustomExceptionHandler when there's any exception in the controller code to centralize exception handling logic.
public class CustomExceptionHandler {
    @ExceptionHandler(UserAlreadyExistException.class) //match each exception to the corresponding handler function.
    public final ResponseEntity<String> handleUserAlreadyExistExceptions(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(UserNotExistException.class)
    public final ResponseEntity<String> handleUserNotExistExceptions(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }


}
