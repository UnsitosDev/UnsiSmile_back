package edu.mx.unsis.unsiSmile.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorDto {
    private int status;           // Código HTTP
    private String message;       // Mensaje de error
    private String errorCode;     // Código de error (si aplica)
    private LocalDateTime timestamp; // Marca de tiempo
}
