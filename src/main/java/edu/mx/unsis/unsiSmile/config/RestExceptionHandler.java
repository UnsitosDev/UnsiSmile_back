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
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;

@ControllerAdvice
@Log4j2
public class RestExceptionHandler {

    @ExceptionHandler(AppException.class)
    @ResponseBody
    public ResponseEntity<ErrorDto> handleAppException(AppException ex, HttpServletRequest request) {
        // Se incluye información contextual (método y URI)
        log.error("AppException occurred at {} - Request [{} {}]: {}. Full message: {}",
                  ex.getTimestamp(),
                  request.getMethod(),
                  request.getRequestURI(),
                  ex.getMessage(),
                  ex.getFullMessage(), ex);

        ErrorDto errorResponse = new ErrorDto(
                ex.getHttpStatus().value(),
                ex.getMessage(),
                ex.getErrorCode().orElse("UNKNOWN_ERROR"),
                ex.getTimestamp()
        );

        return ResponseEntity.status(ex.getHttpStatus()).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorDto> handleGenericException(Exception ex, HttpServletRequest request) {
        log.error("Unhandled exception occurred at {} - Request [{} {}]: {}",
                  LocalDateTime.now(),
                  request.getMethod(),
                  request.getRequestURI(),
                  ex.getMessage(), ex);

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
    public Map<String, String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest request) {
        // Se registra el error de validación con nivel WARN para diferenciar de errores internos
        log.warn("Validation error for request [{} {}]: {}",
                 request.getMethod(),
                 request.getRequestURI(),
                 ex.getBindingResult().getFieldErrors());
        
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage())
        );
        return errors;
    }
}
