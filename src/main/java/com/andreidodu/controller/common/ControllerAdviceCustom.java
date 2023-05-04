package com.andreidodu.controller.common;

import com.andreidodu.dto.ServerResultDTO;
import com.andreidodu.exception.ApplicationException;
import com.andreidodu.exception.ValidationException;
import jakarta.validation.ConstraintViolationException;
import org.postgresql.util.PSQLException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdviceCustom {
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ServerResultDTO> handleApplicationException(ApplicationException e) {
        return ResponseEntity.status(500).body(new ServerResultDTO(1, e.getMessage()));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ServerResultDTO> handleApplicationException(ValidationException e) {
        return ResponseEntity.status(400).body(new ServerResultDTO(4, e.getMessage()));
    }

    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<ServerResultDTO> handlePSQLException(PSQLException e) {
        return ResponseEntity.status(500).body(new ServerResultDTO(2, e.getMessage()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ServerResultDTO> handleConstaintViolationException(ConstraintViolationException e) {
        return ResponseEntity.status(500).body(new ServerResultDTO(3, e.getConstraintViolations().toString()));
    }
}