package com.zamora.security.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ControllerAdvice
public class ResponseEntityExceptionHandler extends org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Error> handleAllException(Exception ex, WebRequest request) throws Exception {
        Error error = new Error( LocalDate.now(), ex.getMessage(), request.getDescription(false));
    return new ResponseEntity<Error>(error, HttpStatus.NOT_FOUND);
    }
}
