package edu.mx.unsis.unsiSmile.service.patients;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.response.forms.catalogs.MedicalRecordCatalogResponse;
import edu.mx.unsis.unsiSmile.dtos.response.patients.MedicalRecordListResponse;
import edu.mx.unsis.unsiSmile.dtos.response.patients.PatientMedicalRecordResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.model.forms.catalogs.MedicalRecordCatalogModel;
import edu.mx.unsis.unsiSmile.model.patients.PatientMedicalRecordModel;
import edu.mx.unsis.unsiSmile.model.patients.PatientModel;
import edu.mx.unsis.unsiSmile.model.treatments.TreatmentDetailModel;
import edu.mx.unsis.unsiSmile.repository.patients.IPatientMedicalRecordRepository;
import edu.mx.unsis.unsiSmile.repository.treatments.ITreatmentDetailRepository;
import edu.mx.unsis.unsiSmile.service.medicalrecords.MedicalRecordCatalogDigitizerService;
import edu.mx.unsis.unsiSmile.service.medicalrecords.MedicalRecordCatalogService;
import edu.mx.unsis.unsiSmile.service.treatments.TreatmentDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientMedicalRecordDigitizerService {

    private final IPatientMedicalRecordRepository patientMedicalRecordRepository;
    private final MedicalRecordCatalogDigitizerService medicalRecordCatalogDigitizerService;
    private final MedicalRecordCatalogService medicalRecordCatalogService;
    private final PatientService patientService;
    private final ITreatmentDetailRepository treatmentDetailRepository;
    private final TreatmentDetailService treatmentDetailService;

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


    @Transactional(readOnly = true)
    public Page<MedicalRecordListResponse> findPatientMedicalRecords(String idPatient, Pageable pageable) {
        try {
            Page<PatientMedicalRecordModel> patientMedicalRecords = patientMedicalRecordRepository
                    .findByPatient_IdPatient(idPatient, pageable);

            return patientMedicalRecords.map(medicalRecord -> {
                MedicalRecordListResponse response = MedicalRecordListResponse.builder()
                        .patientMedicalRecord(
                                PatientMedicalRecordResponse.builder()
                                        .id(medicalRecord.getMedicalRecordCatalog().getIdMedicalRecordCatalog())
                                        .medicalRecordName(medicalRecord.getMedicalRecordCatalog().getMedicalRecordName())
                                        .patientMedicalRecordId(medicalRecord.getIdPatientMedicalRecord())
                                        .patientId(medicalRecord.getPatient().getIdPatient())
                                        .patientName(medicalRecord.getPatient().getPerson().getFullName())
                                        .build()
                        )
                        .appointmentDate(medicalRecord.getAppointmentDate().toLocalDate())
                        .build();

                // Buscar tratamiento asociado si es necesario
                Optional<TreatmentDetailModel> treatment = treatmentDetailRepository.findByPatientMedicalRecord_IdPatientMedicalRecord(medicalRecord.getIdPatientMedicalRecord());
                treatment.ifPresent(treatmentDetailModel -> response.setTreatmentDetail(treatmentDetailService.toTreatmentDetailResponse(treatmentDetailModel)));

                return response;
            });

        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_TO_FETCH_PATIENT_MEDICAL_RECORDS, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}