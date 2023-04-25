package com.andreidodu.controller.common;

import com.andreidodu.dto.ServerResultDTO;
import com.andreidodu.exception.ApplicationException;
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

    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<ServerResultDTO> handleApplicationException(PSQLException e) {
        return ResponseEntity.status(500).body(new ServerResultDTO(2, e.getMessage()));
    }
}