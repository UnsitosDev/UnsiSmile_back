package edu.mx.unsis.unsiSmile.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Global validation error handler for handling MethodArgumentNotValidException.
 */
@RestControllerAdvice
public class ValidationHandler {

    /**
     * Handles validation errors and returns a detailed response with field-specific error messages.
     *
     * @param e the exception thrown when validation fails.
     * @return a map containing validation error details.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, Object> handleValidationErrors(MethodArgumentNotValidException e) {
        Map<String, Object> response = new HashMap<>();
        Map<String, List<String>> fieldErrors = new HashMap<>();
        List<String> globalErrors = new ArrayList<>();

        e.getBindingResult().getAllErrors().forEach(error -> {
            if (error instanceof FieldError fieldError) {
                fieldErrors.computeIfAbsent(fieldError.getField(), key -> new ArrayList<>())
                        .add(fieldError.getDefaultMessage());
            } else {
                globalErrors.add(error.getDefaultMessage());
            }
        });

        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("message", "Validation failed");
        response.put("fieldErrors", fieldErrors);
        if (!globalErrors.isEmpty()) {
            response.put("globalErrors", globalErrors);
        }

        return response;
    }
}
