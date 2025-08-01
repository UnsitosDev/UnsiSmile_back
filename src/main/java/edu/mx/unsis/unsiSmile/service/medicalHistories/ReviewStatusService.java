package edu.mx.unsis.unsiSmile.service.medicalHistories;

import edu.mx.unsis.unsiSmile.authenticationProviders.model.ERole;
import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.request.forms.sections.ReviewStatusRequest;
import edu.mx.unsis.unsiSmile.dtos.response.users.UserResponse;
import edu.mx.unsis.unsiSmile.dtos.response.patients.PatientMedicalRecordResponse;
import edu.mx.unsis.unsiSmile.dtos.response.forms.sections.ReviewSectionResponse;
import edu.mx.unsis.unsiSmile.dtos.response.forms.sections.ReviewStatusResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.ReviewStatusMapper;
import edu.mx.unsis.unsiSmile.model.patients.PatientMedicalRecordModel;
import edu.mx.unsis.unsiSmile.model.enums.ReviewStatus;
import edu.mx.unsis.unsiSmile.model.forms.sections.ReviewStatusModel;
import edu.mx.unsis.unsiSmile.model.treatments.TreatmentDetailModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IFormSectionRepository;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IPatientMedicalRecordRepository;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IReviewStatusRepository;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.treatments.ITreatmentDetailRepository;
import edu.mx.unsis.unsiSmile.repository.patients.IPatientRepository;
import edu.mx.unsis.unsiSmile.repository.professors.IProfessorClinicalAreaRepository;
import edu.mx.unsis.unsiSmile.service.UserService;
import edu.mx.unsis.unsiSmile.service.socketNotifications.ReviewSectionNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewStatusService {

    private final IReviewStatusRepository reviewStatusRepository;
    private final ReviewStatusMapper reviewStatusMapper;
    private final IPatientMedicalRecordRepository patientMedicalRecordRepository;
    private final IPatientRepository patientRepository;
    private final IFormSectionRepository formSectionRepository;
    private final IProfessorClinicalAreaRepository professorClinicalAreaRepository;
    private final UserService userService;
    private final ReviewSectionNotificationService reviewSectionNotificationService;
    private final ITreatmentDetailRepository treatmentDetailRepository;

    @Transactional
    public void updateReviewStatus(ReviewStatusRequest request) {
        try {
            ReviewStatusModel statusModel = reviewStatusRepository
                    .findById(request.getIdReviewStatus())
                    .orElseThrow(() -> new AppException(
                            String.format(ResponseMessages.STATUS_NOT_FOUND, request.getIdReviewStatus()),
                            HttpStatus.NOT_FOUND));
            reviewStatusMapper.updateEntity(request, statusModel);
            reviewSectionNotificationService.broadcastReviewStatus(
                    statusModel.getPatientMedicalRecord().getIdPatientMedicalRecord(),
                    statusModel.getPatientMedicalRecord().getPatient().getIdPatient(),
                    reviewStatusMapper.toDto(statusModel));
            reviewStatusRepository.save(statusModel);

        } catch (IllegalArgumentException e) {
            throw new AppException(ResponseMessages.INVALID_STATUS + request.getStatus(), HttpStatus.BAD_REQUEST);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ResponseMessages.ERROR_PROCESSING_STATUS,
                    HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Transactional(readOnly = true)
    public ReviewStatusModel getStatusModelByPatientMedicalRecordId(Long idPatientMedicalRecord, String idSection) {
        try {
            PatientMedicalRecordModel patientMedicalRecordModel = patientMedicalRecordRepository
                    .findById(idPatientMedicalRecord)
                    .orElseThrow(() -> new AppException(
                            String.format(ResponseMessages.PATIENT_CLINICAL_HISTORY_NOT_FOUND, idPatientMedicalRecord),
                            HttpStatus.NOT_FOUND));

            List<ReviewStatusModel> results = reviewStatusRepository.findAllByPatientIdAndSectionOrdered(
                    patientMedicalRecordModel.getPatient().getIdPatient(),
                    idSection);

            return results.stream()
                    .findFirst()
                    .orElseThrow(() -> new AppException(ResponseMessages.STATUS_NOT_FOUND, HttpStatus.NOT_FOUND));
        } catch (AppException ex) {
            throw ex;
        } catch (Exception e) {
            throw new AppException(ResponseMessages.ERROR_FETCHING_STATUS, HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Transactional(readOnly = true)
    public ReviewStatusResponse getStatusByPatientMedicalRecordId(Long idPatientMedicalRecord, String idSection) {
        try {
            ReviewStatusModel statusModel = this.getStatusModelByPatientMedicalRecordId(idPatientMedicalRecord,
                    idSection);
            return reviewStatusMapper.toDto(statusModel);

        } catch (AppException ex) {
            throw ex;
        } catch (Exception e) {
            throw new AppException(ResponseMessages.ERROR_FETCHING_STATUS, HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Transactional
    public void sendToReview(Long patientMedicalRecordId, String sectionId, Long professorClinicalAreaId) {
        try {
             PatientMedicalRecordModel patientMedicalRecord = patientMedicalRecordRepository.findById(patientMedicalRecordId)
                    .orElseThrow(() -> new AppException(
                            String.format(ResponseMessages.PATIENT_CLINICAL_HISTORY_NOT_FOUND, patientMedicalRecordId),
                            HttpStatus.NOT_FOUND));

            validateEntitiesExist(sectionId, professorClinicalAreaId);

            ReviewStatusModel statusModel = reviewStatusMapper.toEntity(patientMedicalRecord, sectionId,
                    professorClinicalAreaId);

            reviewSectionNotificationService.broadcastReviewStatus(
                    patientMedicalRecord.getIdPatientMedicalRecord(),
                    patientMedicalRecord.getPatient().getIdPatient(),
                    reviewStatusMapper.toDto(statusModel));

            reviewStatusRepository.save(statusModel);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ResponseMessages.ERROR_SENDING_TO_REVIEW, HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Transactional(readOnly = true)
    public Page<ReviewStatusResponse> getReviewStatusByStatus(String status, Pageable pageable) {
        try {
            UserResponse user = userService.getCurrentUser();
            ReviewStatus medicalRecordStatus = ReviewStatus.valueOf(status.toUpperCase());

            Page<ReviewStatusModel> statusModels;

            if (user.getRole().getRole() == ERole.ROLE_ADMIN) {
                statusModels = reviewStatusRepository.findByStatus(medicalRecordStatus, pageable);
            } else if (user.getRole().getRole() == ERole.ROLE_CLINICAL_AREA_SUPERVISOR) {
                statusModels = reviewStatusRepository.findByStatusAndProfessor(
                        user.getUsername(), medicalRecordStatus, pageable);
            } else {
                throw new AppException(ResponseMessages.UNAUTHORIZED, HttpStatus.FORBIDDEN);
            }
            return statusModels.map(this::buildStatusResponse);
        } catch (IllegalArgumentException e) {
            throw new AppException(ResponseMessages.INVALID_STATUS + status, HttpStatus.BAD_REQUEST);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ResponseMessages.ERROR_FETCHING_STATUS_LIST, HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Transactional(readOnly = true)
    public ReviewStatusModel getStatusByPatientMedicalRecordIdAndSection(String idPatient, String idSection) {
        return reviewStatusRepository.findAllByPatientIdAndSectionOrdered(idPatient, idSection)
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public List<PatientMedicalRecordResponse> searchMedicalRecords(String idPatient, String status) {
        if (!patientRepository.existsById(idPatient)) {
            throw new AppException(ResponseMessages.PATIENT_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        ReviewStatus reviewStatus;
        try {
            reviewStatus = ReviewStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new AppException(ResponseMessages.INVALID_STATUS, HttpStatus.BAD_REQUEST);
        }

        List<ReviewStatusModel> statusModels = reviewStatusRepository
                .findByIdPatientAndStatus(idPatient, reviewStatus);

        return statusModels.stream()
                .map(statusModel -> mapToMedicalRecordResponse(statusModel.getPatientMedicalRecord()))
                .collect(Collectors.toList());
    }

    private PatientMedicalRecordResponse mapToMedicalRecordResponse(
            PatientMedicalRecordModel patientMedicalRecordModel) {
        return PatientMedicalRecordResponse.builder()
                .id(patientMedicalRecordModel.getMedicalRecordCatalog().getIdMedicalRecordCatalog())
                .medicalRecordName(patientMedicalRecordModel.getMedicalRecordCatalog().getMedicalRecordName())
                .patientMedicalRecordId(patientMedicalRecordModel.getIdPatientMedicalRecord())
                .patientId(patientMedicalRecordModel.getPatient().getIdPatient())
                .build();
    }

    private void validateEntitiesExist(String idSection, Long idProfessorClinicalArea) {
        formSectionRepository.findById(idSection)
                .orElseThrow(() -> new AppException(ResponseMessages.FORM_SECTION_NOT_FOUND + idSection,
                        HttpStatus.NOT_FOUND));

        professorClinicalAreaRepository.findById(idProfessorClinicalArea)
                .orElseThrow(() -> new AppException(
                        ResponseMessages.PROFESSOR_CLINICAL_AREA_NOT_FOUND + idProfessorClinicalArea,
                        HttpStatus.NOT_FOUND));
    }

    public ReviewSectionResponse buildStatusResponse(ReviewStatusModel statusModel, boolean requiresReview) {
        if (!requiresReview) {
            return ReviewSectionResponse.builder()
                    .status("NOT_REQUIRED")
                    .build();
        }

        return statusModel != null
                ? reviewStatusMapper.toReviewSectionResponse(statusModel)
                : ReviewSectionResponse.builder()
                        .status("NO_STATUS")
                        .build();
    }

    private ReviewStatusResponse buildStatusResponse(ReviewStatusModel statusModel) {
        Optional<TreatmentDetailModel> treatmentDetailOpt = treatmentDetailRepository
                .findByPatientMedicalRecord_IdPatientMedicalRecord(
                        statusModel.getPatientMedicalRecord().getIdPatientMedicalRecord());

        ReviewStatusResponse response = reviewStatusMapper.toDto(statusModel);

        treatmentDetailOpt.ifPresent(treatmentDetail -> {
                    response.setIdTreatmentDetail(treatmentDetail.getIdTreatmentDetail());
                    response.setStudentName(treatmentDetail.getStudentGroup().getStudent().getPerson().getFullName());
                }
        );

        return response;
    }
}
