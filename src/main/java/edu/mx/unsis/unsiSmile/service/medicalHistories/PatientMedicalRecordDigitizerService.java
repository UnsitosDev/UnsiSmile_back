package edu.mx.unsis.unsiSmile.service.medicalHistories;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.MedicalRecordCatalogResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.model.MedicalRecordCatalogModel;
import edu.mx.unsis.unsiSmile.model.PatientMedicalRecordModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.treatments.TreatmentDetailModel;
import edu.mx.unsis.unsiSmile.model.patients.PatientModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IPatientMedicalRecordRepository;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.treatments.ITreatmentDetailRepository;
import edu.mx.unsis.unsiSmile.service.patients.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientMedicalRecordDigitizerService {

    private final IPatientMedicalRecordRepository patientMedicalRecordRepository;
    private final MedicalRecordCatalogDigitizerService medicalRecordCatalogDigitizerService;
    private final MedicalRecordCatalogService medicalRecordCatalogService;
    private final PatientService patientService;
    private final ITreatmentDetailRepository treatmentDetailRepository;

    @Transactional
    public MedicalRecordCatalogResponse save(String idPatient, Long idMedicalRecordCatalog) {
        try {
            PatientModel patientModel = patientService.getPatientModel(idPatient);
            MedicalRecordCatalogModel medicalRecordCatalogModel =
                    medicalRecordCatalogService.findByIdMedicalRecordCatalog(idMedicalRecordCatalog);

            PatientMedicalRecordModel existingRecord = existPatientMedicalRecord(idPatient, idMedicalRecordCatalog);

            if (existingRecord != null) {
                throw new AppException(
                        String.format(ResponseMessages.MEDICAL_RECORD_ALREADY_EXISTS, idPatient),
                        HttpStatus.CONFLICT);
            }

            PatientMedicalRecordModel saved = patientMedicalRecordRepository.save(toEntity(patientModel, medicalRecordCatalogModel));
            return medicalRecordCatalogDigitizerService.toResponse(saved);
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_TO_SAVE_PATIENT_MEDICAL_RECORD, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    private PatientMedicalRecordModel toEntity(PatientModel patient, MedicalRecordCatalogModel medicalRecordCatalog) {
        return PatientMedicalRecordModel.builder()
                .patient(patient)
                .medicalRecordCatalog(medicalRecordCatalog)
                .appointmentDate(LocalDateTime.now())
                .build();
    }

    private PatientMedicalRecordModel existPatientMedicalRecord(String idPatient, Long idMedicalRecordCatalog) {
        List<TreatmentDetailModel> usedTreatmentDetails =
                treatmentDetailRepository.findUsedByPatientAndCatalog(idPatient, idMedicalRecordCatalog);

        List<Long> usedPmrIds = usedTreatmentDetails.stream()
                .map(td -> td.getPatientMedicalRecord().getIdPatientMedicalRecord())
                .distinct()
                .toList();

        List<PatientMedicalRecordModel> availableRecords = usedPmrIds.isEmpty()
                ? patientMedicalRecordRepository
                .findByPatient_IdPatientAndMedicalRecordCatalog_IdMedicalRecordCatalog(idPatient, idMedicalRecordCatalog)
                .map(List::of)
                .orElse(List.of())
                : patientMedicalRecordRepository
                .findAvailableByPatientAndCatalogExcludingIds(idPatient, idMedicalRecordCatalog, usedPmrIds);

        return availableRecords.stream()
                .findFirst()
                .orElse(null);
    }
}