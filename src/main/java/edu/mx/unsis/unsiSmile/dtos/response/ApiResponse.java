package edu.mx.unsis.unsiSmile.dtos.response;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ApiResponse<T> {
    private Map<String, T> response;
}
