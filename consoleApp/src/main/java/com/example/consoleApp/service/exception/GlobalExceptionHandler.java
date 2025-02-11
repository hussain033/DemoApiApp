package com.example.consoleApp.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<String> handleAuthenticationException(Exception ex) {
        if (ex instanceof BadCredentialsException) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("bad credentials");
        } else if (ex instanceof RuntimeException) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
        }

    }
}
