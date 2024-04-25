package edu.mx.unsis.unsiSmile.exceptions;

import org.springframework.http.HttpStatus;

public class AppException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final Exception originalException;

    public AppException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
        this.originalException = null;
    }

    public AppException(String message, HttpStatus httpStatus, Exception originalException) {
        super(message, originalException);
        this.httpStatus = httpStatus;
        this.originalException = originalException;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public Exception getOriginalException() {
        return originalException;
    }

    public String getFullMessage() {
        StringBuilder fullMessage = new StringBuilder(getMessage());
        if (originalException != null) {
            fullMessage.append("; Caused by: ").append(originalException.getMessage());
        }
        return fullMessage.toString();
    }
}
