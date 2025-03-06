package edu.mx.unsis.unsiSmile.config;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import edu.mx.unsis.unsiSmile.dtos.ErrorDto;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler(value = {AppException.class})
    @ResponseBody
    public ResponseEntity<ErrorDto> handleAppException(AppException ex) {
        log.error("AppException occurred at {}: {}", ex.getTimestamp(), ex.getFullMessage(), ex);

        ErrorDto errorResponse = new ErrorDto(
                ex.getHttpStatus().value(),
                ex.getMessage(),
                ex.getErrorCode().orElse("UNKNOWN_ERROR"),
                ex.getTimestamp()
        );

        return ResponseEntity.status(ex.getHttpStatus()).body(errorResponse);
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public ResponseEntity<ErrorDto> handleGenericException(Exception ex) {
        log.error("Unhandled exception occurred at {}: {}", LocalDateTime.now(), ex.getMessage(), ex);

        ErrorDto errorResponse = new ErrorDto(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected error occurred. Please contact support.",
                "UNEXPECTED_ERROR",
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return errors;
    }
}