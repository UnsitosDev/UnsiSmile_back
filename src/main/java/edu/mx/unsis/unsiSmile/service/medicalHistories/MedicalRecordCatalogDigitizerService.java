package edu.mx.unsis.unsiSmile.service.medicalHistories;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.response.FormSectionResponse;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.MedicalRecordCatalogResponse;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.PatientMedicalRecordResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.MedicalRecordCatalogMapper;
import edu.mx.unsis.unsiSmile.model.MedicalRecordSectionModel;
import edu.mx.unsis.unsiSmile.model.patients.PatientMedicalRecordModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IMedicalRecordCatalogRepository;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.treatments.ITreatmentDetailRepository;
import edu.mx.unsis.unsiSmile.service.patients.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalRecordCatalogDigitizerService {
    private final IMedicalRecordCatalogRepository medicalRecordCatalogRepository;
    private final ITreatmentDetailRepository treatmentDetailRepository;
    private final MedicalRecordCatalogMapper medicalRecordCatalogMapper;
    private final MedicalRecordSectionService medicalRecordSectionService;
    private final FormSectionService formSectionService;
    private final PatientService patientService;

    public MedicalRecordCatalogResponse toResponse(PatientMedicalRecordModel patientMedicalRecord) {

        List<MedicalRecordSectionModel> medicalRecordSectionList = medicalRecordSectionService
                .findByMedicalRecordId(patientMedicalRecord.getMedicalRecordCatalog().getIdMedicalRecordCatalog());

        List<FormSectionResponse> sections = formSectionService.findAllByMedicalRecord(medicalRecordSectionList, patientMedicalRecord.getPatient().getIdPatient(), patientMedicalRecord.getIdPatientMedicalRecord());

        MedicalRecordCatalogResponse medicalRecordCatalogResponse = medicalRecordCatalogMapper.toDto(patientMedicalRecord.getMedicalRecordCatalog());

        medicalRecordCatalogResponse.setMedicalRecordNumber(patientMedicalRecord.getPatient().getMedicalRecordNumber());
        medicalRecordCatalogResponse.setAppointmentDate(patientMedicalRecord.getAppointmentDate());

        medicalRecordCatalogResponse.setFormSections(sections);

        return medicalRecordCatalogResponse;
    }

    @Transactional(readOnly = true)
    public List<PatientMedicalRecordResponse> searchCMedicalRecordsByPatient(String idPatient) {
        try {
            // 1. Verificar que el paciente existe
            patientService.getPatientModel(idPatient);

            // 2. Obtener todos los PMR que están en tratamientos
            List<Long> usedPmrIds = treatmentDetailRepository.findByPatientId(idPatient)
                    .stream()
                    .map(td -> td.getPatientMedicalRecord().getIdPatientMedicalRecord())
                    .distinct()
                    .toList();

            // 3. Ejecutar la query con LEFT JOIN y filtrado en SQL
            List<Object[]> results = medicalRecordCatalogRepository.findAllMedicalRecordByPatientId(
                    idPatient,
                    usedPmrIds.isEmpty() ? List.of(-1L) : usedPmrIds
            );

            if (results.isEmpty()) {
                throw new AppException(String.format(ResponseMessages.NO_MEDICAL_RECORDS_FOUND_FOR_PATIENT, idPatient),
                        HttpStatus.NOT_FOUND);
            }

            // 4. Mapear resultados (ya no necesitamos verificar isUsed porque el filtrado se hizo en SQL)
            return results.stream()
                    .map(result -> PatientMedicalRecordResponse.builder()
                            .id(((Number) result[0]).longValue())
                            .medicalRecordName((String) result[1])
                            .patientMedicalRecordId(result[2] != null ? ((Number) result[2]).longValue() : null)
                            .patientId(result[3] != null ? result[3].toString() : null)
                            .build())
                    .toList();

        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(String.format(ResponseMessages.FAILED_TO_SEARCH_MEDICAL_RECORDS, idPatient),
                    HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}
