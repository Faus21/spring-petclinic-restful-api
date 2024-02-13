package com.project.s24521.config;

import com.project.s24521.DTOs.exception.ObjectBadRequestException;
import com.project.s24521.DTOs.exception.ObjectNotFoundException;
import com.project.s24521.DTOs.exception.WebAppException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler{
    @ExceptionHandler({ObjectNotFoundException.class})
    public ResponseEntity<Object> handleObjectNotFoundException(ObjectNotFoundException exception) {
        return new ResponseEntity<>(
                new WebAppException(HttpStatus.NOT_FOUND.value(), exception.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler({ObjectBadRequestException.class})
    public ResponseEntity<Object> handleObjectIsAlreadyExistException(ObjectBadRequestException exception) {
        return new ResponseEntity<>(
                new WebAppException(HttpStatus.BAD_REQUEST.value(), exception.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

}