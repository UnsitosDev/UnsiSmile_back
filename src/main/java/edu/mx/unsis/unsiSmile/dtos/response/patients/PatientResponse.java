package edu.mx.unsis.unsiSmile.dtos.response.patients;

import java.time.LocalDate;

import edu.mx.unsis.unsiSmile.dtos.response.PersonResponse;
import edu.mx.unsis.unsiSmile.dtos.response.addresses.AddressResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientResponse {
    private Long idPatient;
    private LocalDate admissionDate;
    private Boolean isMinor;
    private Boolean hasDisability;
    private NationalityResponse nationality;
    private PersonResponse person;
    private AddressResponse address;
    private MaritalStatusResponse maritalStatus;
    private OccupationResponse occupation;
    private EthnicGroupResponse ethnicGroup;
    private ReligionResponse religion;
    private GuardianResponse guardian;
}
