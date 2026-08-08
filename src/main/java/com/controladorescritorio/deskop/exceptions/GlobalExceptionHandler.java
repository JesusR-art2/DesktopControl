package com.controladorescritorio.deskop.exceptions;

import com.controladorescritorio.deskop.exchanges.ErrorDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

     @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorDTO> handleCustomException(CustomException ex) {
        ErrorDTO errorDTO = new ErrorDTO(
                ex.getErrorCode().name(),
                ex.getMessage(),
                Instant.now()
        );
        return ResponseEntity.status(ex.getErrorCode().getStatus()).body(errorDTO);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(","));
        ErrorDTO errorDTO = new ErrorDTO(
                "VALIDATION_ERROR",
                errorMessage,
                Instant.now()
        );
        return ResponseEntity.status(ErrorCode.VALIDATION_ERROR.getStatus()).body(errorDTO);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleGenericException(Exception ex) {
         log.error("Unhandled exception occurred: ", ex);
        ErrorDTO errorDTO = new ErrorDTO(
                "INTERNAL_SERVER_ERROR",
                ex.getMessage(),
                Instant.now()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
    }
}
