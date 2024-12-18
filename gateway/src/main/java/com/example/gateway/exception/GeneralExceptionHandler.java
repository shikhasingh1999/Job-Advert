package com.example.gateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {

//    @ExceptionHandler(WrongCredentialsException.class)
//    public ResponseEntity<?> usernameOrPasswordInvalidException(WrongCredentialsException wrongCredentialsException) {
//        Map<String, String> errors = new HashMap<>();
//        errors.put("error", wrongCredentialsException.getMessage());
//        return new ResponseEntity<>(errors, HttpStatus.UNAUTHORIZED);
//    }
}
