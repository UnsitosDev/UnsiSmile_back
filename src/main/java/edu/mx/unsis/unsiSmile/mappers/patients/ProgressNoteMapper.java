package edu.mx.unsis.unsiSmile.mappers.patients;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Component;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.ProgressNoteRequest;
import edu.mx.unsis.unsiSmile.dtos.response.patients.PatientRes;
import edu.mx.unsis.unsiSmile.dtos.response.patients.PatientResponse;
import edu.mx.unsis.unsiSmile.dtos.response.patients.ProgressNoteResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.patients.ProgressNoteModel;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ProgressNoteMapper implements BaseMapper<ProgressNoteResponse, ProgressNoteRequest, ProgressNoteModel> {
    PatientMapper patientMapper;

    @Override
    public ProgressNoteModel toEntity(ProgressNoteRequest dto) {
        return ProgressNoteModel.builder()
                .bloodPressure(dto.getBloodPressure())
                .temperature(dto.getTemperature())
                .heartRate(dto.getHeartRate())
                .respiratoryRate(dto.getRespiratoryRate())
                .oxygenSaturation(dto.getOxygenSaturation())
                .diagnosis(dto.getDiagnosis())
                .prognosis(dto.getPrognosis())
                .treatment(dto.getTreatment())
                .indications(dto.getIndications())
                .build();
    }

    @Override
    public ProgressNoteResponse toDto(ProgressNoteModel entity) {
        return ProgressNoteResponse.builder()
                .idProgressNote(entity.getIdProgressNote())
                .patient(mapPatientToPatientRes(patientMapper.toDto(entity.getPatient())))
                .bloodPressure(entity.getBloodPressure())
                .temperature(entity.getTemperature())
                .heartRate(entity.getHeartRate())
                .respirationRate(entity.getRespiratoryRate())
                .oxygenSaturation(entity.getOxygenSaturation())
                .diagnosis(entity.getDiagnosis())
                .prognosis(entity.getPrognosis())
                .treatment(entity.getTreatment())
                .indications(entity.getIndications())
                .creationDate(formatTimestamp(entity.getCreatedAt()))
                .build();
    }

    @Override
    public List<ProgressNoteResponse> toDtos(List<ProgressNoteModel> entities) {
        return List.of();
    }

    @Override
    public void updateEntity(ProgressNoteRequest request, ProgressNoteModel entity) {}

    private PatientRes mapPatientToPatientRes(PatientResponse patientResponse) {
        return PatientRes.builder()
                .idPatient(patientResponse.getIdPatient())
                .birthDate(patientResponse.getPerson().getBirthDate())
                .age(calculateAge(patientResponse.getPerson().getBirthDate()))
                .origin(getFullOrigin(patientResponse))
                .medicalRecordNumber(patientResponse.getMedicalRecordNumber())
                .creationDate(LocalDate.now())
                .isMinor(patientResponse.getIsMinor() != null ? patientResponse.getIsMinor() : false)
                .guardian(patientResponse.getGuardian() != null ? patientResponse.getGuardian().getPerson().getFullName() : null)
                .gender(patientResponse.getPerson().getGender().getGender())
                .build();
    }

    private String getFullOrigin(PatientResponse patientResponse) {
        return String.format("%s, %s, %s",
                patientResponse.getAddress().getStreet().getNeighborhood().getLocality().getName(),
                patientResponse.getAddress().getStreet().getNeighborhood().getLocality().getMunicipality().getName(),
                patientResponse.getAddress().getStreet().getNeighborhood().getLocality().getMunicipality().getState().getName());
    }

    private Long calculateAge(LocalDate birthDate) {
        return (birthDate != null) ? (long) Period.between(birthDate, LocalDate.now()).getYears() : 0L;
    }

    private String formatTimestamp(Timestamp timestamp) {
        LocalDateTime dateTime = (timestamp != null) ? timestamp.toLocalDateTime() : LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }
}
