package edu.mx.unsis.unsiSmile.service.medicalHistories;

import edu.mx.unsis.unsiSmile.authenticationProviders.model.ERole;
import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.ReviewStatusRequest;
import edu.mx.unsis.unsiSmile.dtos.response.UserResponse;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.PatientClinicalHistoryResponse;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.ReviewStatusResponse;
import edu.mx.unsis.unsiSmile.dtos.response.patients.PatientResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.ReviewStatusMapper;
import edu.mx.unsis.unsiSmile.mappers.patients.PatientMapper;
import edu.mx.unsis.unsiSmile.model.PatientClinicalHistoryModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.ReviewStatus;
import edu.mx.unsis.unsiSmile.model.medicalHistories.ReviewStatusModel;
import edu.mx.unsis.unsiSmile.model.patients.PatientModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IFormSectionRepository;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IPatientClinicalHistoryRepository;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IReviewStatusRepository;
import edu.mx.unsis.unsiSmile.repository.patients.IPatientRepository;
import edu.mx.unsis.unsiSmile.repository.professors.IProfessorClinicalAreaRepository;
import edu.mx.unsis.unsiSmile.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewStatusService {

    private final IReviewStatusRepository reviewStatusRepository;
    private final ReviewStatusMapper reviewStatusMapper;
    private final IPatientClinicalHistoryRepository patientClinicalHistoryRepository;
    private final PatientMapper patientMapper;
    private final IPatientRepository patientRepository;
    private final IFormSectionRepository formSectionRepository;
    private final IProfessorClinicalAreaRepository professorClinicalAreaRepository;
    private final UserService userService;

    @Transactional
    public void updateReviewStatus(ReviewStatusRequest request) {
        try {
            ReviewStatusModel statusModel = reviewStatusRepository
                    .findById(request.getIdReviewStatus())
                    .orElseThrow(() -> new AppException(
                            String.format(ResponseMessages.STATUS_NOT_FOUND, request.getIdReviewStatus()),
                            HttpStatus.NOT_FOUND
                    ));
            reviewStatusMapper.updateEntity(request, statusModel);

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
    public ReviewStatusResponse getStatusByPatientClinicalHistoryId(Long idPatientClinicalHistory, Long idSection) {
        try {
            PatientClinicalHistoryModel patientClinicalHistoryModel = patientClinicalHistoryRepository.findById(idPatientClinicalHistory)
                    .orElseThrow(() -> new AppException(ResponseMessages.PATIENT_CLINICAL_HISTORY_NOT_FOUND, HttpStatus.NOT_FOUND));

            ReviewStatusModel statusModel =
                reviewStatusRepository.findByPatientClinicalHistory_Patient_IdPatientAndFormSection_IdFormSection(
                        patientClinicalHistoryModel.getPatient().getIdPatient(),
                                idSection)
                        .orElseThrow(() -> new AppException(ResponseMessages.STATUS_NOT_FOUND, HttpStatus.NOT_FOUND));

            return reviewStatusMapper.toDto(statusModel);

        } catch (AppException ex) {
            throw ex;
        } catch (Exception e) {
            throw new AppException(ResponseMessages.ERROR_FETCHING_STATUS, HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Transactional
    public void sendToReview(Long idPatientClinicalHistory,  Long idSection, Long idProfessorClinicalArea) {
        try {
            validateEntitiesExist(idPatientClinicalHistory, idSection, idProfessorClinicalArea);

            ReviewStatusModel statusModel = reviewStatusMapper.toEntity(idPatientClinicalHistory, idSection, idProfessorClinicalArea);

            reviewStatusRepository.save(statusModel);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ResponseMessages.ERROR_SENDING_TO_REVIEW, HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Transactional(readOnly = true)
    public Page<PatientResponse> getPatientsByReviewStatus(String status, Pageable pageable) {
        try {
            UserResponse user = userService.getCurrentUser();
            ReviewStatus clinicalHistoryStatus = ReviewStatus.valueOf(status.toUpperCase());

            Page<ReviewStatusModel> statusModels;

            if (user.getRole().getRole() == ERole.ROLE_ADMIN) {
                statusModels = reviewStatusRepository.findByStatus(clinicalHistoryStatus, pageable);
            } else if (user.getRole().getRole() == ERole.ROLE_CLINICAL_AREA_SUPERVISOR) {
                statusModels = reviewStatusRepository.findByStatusAndProfessor(
                        user.getUsername(),clinicalHistoryStatus, pageable);
            } else {
                throw new AppException(ResponseMessages.UNAUTHORIZED, HttpStatus.FORBIDDEN);
            }

            return statusModels.map(statusModel -> {
                PatientModel patient = statusModel.getPatientClinicalHistory().getPatient();
                return patientMapper.toDto(patient);
            });
        } catch (IllegalArgumentException e) {
            throw new AppException(ResponseMessages.INVALID_STATUS + status, HttpStatus.BAD_REQUEST);
        }catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ResponseMessages.ERROR_FETCHING_STATUS_LIST, HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Transactional(readOnly = true)
    public ReviewStatusModel getStatusByPatientClinicalHistoryIdAndSection(String idPatient, Long idSection) {
        return reviewStatusRepository.findByPatientClinicalHistory_Patient_IdPatientAndFormSection_IdFormSection(idPatient, idSection)
                            .orElse(null);
    }

    @Transactional(readOnly = true)
    public List<PatientClinicalHistoryResponse> searchClinicalHistory(String idPatient, String status) {
        if (!patientRepository.existsById(idPatient)) {
            throw new AppException(ResponseMessages.PATIENT_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        ReviewStatus clinicalHistoryStatus;
        try {
            clinicalHistoryStatus = ReviewStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new AppException(ResponseMessages.INVALID_STATUS, HttpStatus.BAD_REQUEST);
        }

        List<ReviewStatusModel> statusModels = reviewStatusRepository
                .findByIdPatientAndStatus(idPatient, clinicalHistoryStatus);

        return statusModels.stream()
                .map(statusModel -> mapToClinicalHistoryResponse(statusModel.getPatientClinicalHistory()))
                .collect(Collectors.toList());
    }

    private PatientClinicalHistoryResponse mapToClinicalHistoryResponse(PatientClinicalHistoryModel patientClinicalHistoryModel) {
        return PatientClinicalHistoryResponse.builder()
                .id(patientClinicalHistoryModel.getClinicalHistoryCatalog().getIdClinicalHistoryCatalog())
                .clinicalHistoryName(patientClinicalHistoryModel.getClinicalHistoryCatalog().getClinicalHistoryName())
                .patientClinicalHistoryId(patientClinicalHistoryModel.getIdPatientClinicalHistory())
                .patientId(patientClinicalHistoryModel.getPatient().getIdPatient())
                .build();
    }

    private void validateEntitiesExist(Long idPatientClinicalHistory, Long idSection, Long idProfessorClinicalArea) {
        patientClinicalHistoryRepository.findById(idPatientClinicalHistory)
                .orElseThrow(() -> new AppException(ResponseMessages.PATIENT_CLINICAL_HISTORY_NOT_FOUND + idPatientClinicalHistory,
                        HttpStatus.NOT_FOUND));

        formSectionRepository.findById(idSection)
                .orElseThrow(() -> new AppException(ResponseMessages.FORM_SECTION_NOT_FOUND + idSection, HttpStatus.NOT_FOUND));

        professorClinicalAreaRepository.findById(idProfessorClinicalArea)
                .orElseThrow(() -> new AppException(ResponseMessages.PROFESSOR_CLINICAL_AREA_NOT_FOUND + idProfessorClinicalArea,
                        HttpStatus.NOT_FOUND));
    }
}
