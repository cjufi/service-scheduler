package com.prime.rushhour.infrastructure.exceptions;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleInvalidArgument(ConstraintViolationException e) {
        List<Violation> violations = new ArrayList<>();
        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            var v = new Violation(violation.getPropertyPath().toString(), e.getMessage(), LocalDateTime.now());
            violations.add(v);
        }

        var error = new ErrorResponse(violations);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleInvalidMethodArgument(MethodArgumentNotValidException e) {
        var violations = new ArrayList<Violation>();
        e.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        violations.add(new Violation(error.getField(), error.getDefaultMessage(), LocalDateTime.now()))
                );
        var error = new ErrorResponse(violations);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateResourceException(DuplicateResourceException e) {
        var violation = new Violation(e.getFieldName(), e.getMessage(), LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(List.of(violation)));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(RuntimeException e) {
        var violation = new Violation(null, e.getMessage(), LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(List.of(violation)));
    }

    private record Violation(String field, String error, LocalDateTime timestamp){}
    private record ErrorResponse(List<Violation> violations){}
}
