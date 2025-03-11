package edu.mx.unsis.unsiSmile.dtos.request.patients;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.request.PersonRequest;
import edu.mx.unsis.unsiSmile.dtos.request.addresses.AddressRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientRequest {

    @NotNull(message = ResponseMessages.NOT_NULL_HAS_DISABILITY)
    private Boolean hasDisability;

    @NotNull(message = ResponseMessages.NOT_NULL_NATIONALITY)
    private Long nationalityId;

    @NotNull(message = ResponseMessages.NOT_NULL_PERSON)
    @Valid
    private PersonRequest person;

    @NotNull(message = ResponseMessages.NOT_NULL_ADDRESS)
    private AddressRequest address;

    @NotNull(message = ResponseMessages.NOT_NULL_MARITAL_STATUS)
    @Valid
    private MaritalStatusRequest maritalStatus;

    @NotNull(message = ResponseMessages.NOT_NULL_OCCUPATION)
    @Valid
    private OccupationRequest occupation;

    @NotNull(message = ResponseMessages.NOT_NULL_ETHNIC_GROUP)
    @Valid
    private EthnicGroupRequest ethnicGroup;

    @NotNull(message = ResponseMessages.NOT_NULL_RELIGION)
    @Valid
    private ReligionRequest religion;

    private GuardianRequest guardian;
}
