package edu.mx.unsis.unsiSmile.dtos.request.patients;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.request.forms.catalogs.CatalogOptionRequest;
import edu.mx.unsis.unsiSmile.dtos.request.people.PersonRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GuardianRequest {

    private Long idGuardian;

    @NotNull(message = ResponseMessages.PARENTAL_STATUS_CANNOT_BE_NULL)
    private CatalogOptionRequest parentalStatus;

    @Size(max = 100, message = ResponseMessages.DOCTOR_NAME_MAX_STRING_LENGTH)
    private String doctorName;

    @NotNull(message = ResponseMessages.PERSON_NULL)
    private PersonRequest person;
}