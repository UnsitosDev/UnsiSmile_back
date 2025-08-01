package edu.mx.unsis.unsiSmile.dtos.response.patients;

import edu.mx.unsis.unsiSmile.dtos.response.addresses.AddressResponse;
import edu.mx.unsis.unsiSmile.dtos.response.patients.demographics.*;
import edu.mx.unsis.unsiSmile.dtos.response.people.PersonResponse;
import edu.mx.unsis.unsiSmile.dtos.response.students.StudentRes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientResponse {
    private String idPatient;
    private LocalDate admissionDate;
    private Long medicalRecordNumber;
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
    private StudentRes student;
    private Boolean hasTreatmentInProgress;
}