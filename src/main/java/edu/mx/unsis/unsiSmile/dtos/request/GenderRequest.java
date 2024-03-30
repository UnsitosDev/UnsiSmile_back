package edu.mx.unsis.unsiSmile.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenderRequest {
    private Long idGender;
    private String gender;
}
