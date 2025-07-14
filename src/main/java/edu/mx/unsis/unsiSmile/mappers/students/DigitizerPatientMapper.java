package edu.mx.unsis.unsiSmile.mappers.students;

import edu.mx.unsis.unsiSmile.dtos.request.students.DigitizerPatientRequest;
import edu.mx.unsis.unsiSmile.dtos.response.students.DigitizerPatientResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.mappers.patients.PatientMapper;
import edu.mx.unsis.unsiSmile.model.patients.PatientModel;
import edu.mx.unsis.unsiSmile.model.students.DigitizerPatientModel;
import edu.mx.unsis.unsiSmile.model.students.MedicalRecordDigitizerModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DigitizerPatientMapper implements BaseMapper<DigitizerPatientResponse, DigitizerPatientRequest, DigitizerPatientModel> {

    private final PatientMapper patientMapper;
    private final MedicalRecordDigitizerMapper digitizerMapper;
    private final StudentMapper studentMapper;

    @Override
    public DigitizerPatientModel toEntity(DigitizerPatientRequest dto) {
        return DigitizerPatientModel.builder()
                .patient(PatientModel.builder().idPatient(dto.getPatientId()).build())
                .digitizer(MedicalRecordDigitizerModel.builder().idMedicalRecordDigitizer(dto.getMedicalRecordDigitizerId()).build())
                .build();
    }

    @Override
    public DigitizerPatientResponse toDto(DigitizerPatientModel entity) {
        return DigitizerPatientResponse.builder()
                .idDigitizerPatient(entity.getIdDigitizerPatient())
                .patient(patientMapper.toDto(entity.getPatient()))
                .student(studentMapper.toDto(entity.getDigitizer().getStudent()))
                .digitizer(digitizerMapper.toDto(entity.getDigitizer()))
                .build();
    }

    @Override
    public List<DigitizerPatientResponse> toDtos(List<DigitizerPatientModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(DigitizerPatientRequest request, DigitizerPatientModel entity) {
    }
}