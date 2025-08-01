package edu.mx.unsis.unsiSmile.dtos.response.digitizers;

import edu.mx.unsis.unsiSmile.dtos.response.people.PersonResponse;
import edu.mx.unsis.unsiSmile.dtos.response.patients.PatientResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DigitizerPatientResponse {
    private Long idDigitizerPatient;
    private PatientResponse patient;
    private PersonResponse person;
    private MedicalRecordDigitizerResponse digitizer;
}