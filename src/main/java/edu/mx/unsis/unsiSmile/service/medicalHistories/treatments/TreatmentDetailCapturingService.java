package edu.mx.unsis.unsiSmile.service.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.treatments.*;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.treatments.TreatmentDetailResponse;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.treatments.TreatmentResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.treatments.TreatmentDetailMapper;
import edu.mx.unsis.unsiSmile.model.PatientClinicalHistoryModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.ReviewStatus;
import edu.mx.unsis.unsiSmile.model.medicalHistories.treatments.ExecutionReviewModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.treatments.TreatmentDetailModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.treatments.TreatmentModel;
import edu.mx.unsis.unsiSmile.model.professors.ProfessorClinicalAreaModel;
import edu.mx.unsis.unsiSmile.model.students.StudentGroupModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.treatments.IExecutionReviewRepository;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.treatments.ITreatmentDetailRepository;
import edu.mx.unsis.unsiSmile.service.medicalHistories.PatientClinicalHistoryService;
import edu.mx.unsis.unsiSmile.service.patients.PatientService;
import edu.mx.unsis.unsiSmile.service.professors.ProfessorClinicalAreaService;
import edu.mx.unsis.unsiSmile.service.students.StudentGroupService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TreatmentDetailCapturingService {

    private final ITreatmentDetailRepository treatmentDetailRepository;
    private final TreatmentDetailMapper treatmentDetailMapper;
    private final PatientClinicalHistoryService patientClinicalHistoryService;
    private final TreatmentService treatmentService;
    private final StudentGroupService studentGroupService;
    private final PatientService patientService;
    private final TreatmentDetailToothService treatmentDetailToothService;
    private final ProfessorClinicalAreaService professorClinicalAreaService;
    private final ExecutionReviewService executionReviewService;
    private final IExecutionReviewRepository executionReviewRepository;

    @Transactional
    public TreatmentDetailResponse createTreatmentDetail(@NonNull TreatmentDetailCapturingRequest request) {
        try {
            validateRequestDependencies(request);

            TreatmentResponse treatmentResponse = treatmentService.getTreatmentById(request.getTreatmentId());

            String scope = treatmentResponse.getTreatmentScope().getName();

            if (scope.equals(Constants.TOOTH) && request.getTreatmentDetailToothRequest() == null) {
                throw new AppException(ResponseMessages.TREATMENT_DETAIL_TOOTH_REQUEST_CANNOT_BE_NULL,
                        HttpStatus.BAD_REQUEST);
            }

            TreatmentDetailModel savedModel = saveTreatmentDetail(request, treatmentResponse);

            if (scope.equals(Constants.TOOTH)) {
                TreatmentDetailToothRequest toothRequest = request.getTreatmentDetailToothRequest();
                toothRequest.setIdTreatmentDetail(savedModel.getIdTreatmentDetail());
                treatmentDetailToothService.createTreatmentDetailTeeth(toothRequest);
            }

            ExecutionReviewModel executionReview = createExecutionReviewWithContext(
                    savedModel.getIdTreatmentDetail(),
                    request.getProfessorClinicalAreaId(),
                    savedModel
            );

            TreatmentDetailResponse response = treatmentDetailMapper.toDtoWithReviewerProfessor(executionReview);

            if(isToothTreatment(savedModel)) {
                addTeethList(response);
            }

            return response;
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_CREATE_TREATMENT_DETAIL, HttpStatus.INTERNAL_SERVER_ERROR,
                    ex);
        }
    }

    private void validateRequestDependencies(TreatmentDetailRequest request) {
        if (request.getStartDate().isAfter(request.getEndDate())) {
            throw new AppException(ResponseMessages.TREATMENT_DETAIL_START_DATE_MUST_BE_LESS_THAN_END_DATE,
                    HttpStatus.BAD_REQUEST);
        }
        patientService.getPatientById(request.getPatientId());
    }

    private TreatmentDetailModel saveTreatmentDetail(TreatmentDetailCapturingRequest request,
                                                     TreatmentResponse treatmentResponse) {
        PatientClinicalHistoryModel clinicalHistory = patientClinicalHistoryService.save(
                request.getPatientId(),
                treatmentResponse.getClinicalHistoryCatalogId());

        StudentGroupModel studentGroup = studentGroupService.getStudentGroupByStudent(request.getStudentEnrollment());

        TreatmentDetailModel model = treatmentDetailMapper.toEntity(request);
        model.setStatus(ReviewStatus.IN_PROGRESS);
        model.setPatientClinicalHistory(clinicalHistory);
        model.setStudentGroup(studentGroup);

        return treatmentDetailRepository.save(model);
    }

    @Transactional(readOnly = true)
    public TreatmentDetailResponse getTreatmentDetailById(@NonNull Long id) {
        try {
            TreatmentDetailModel model = treatmentDetailRepository.findById(id)
                    .orElseThrow(() -> new AppException(
                            String.format(ResponseMessages.TREATMENT_DETAIL_NOT_FOUND, id),
                            HttpStatus.NOT_FOUND));
            
            return mapTreatmentDetailToDto(model);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_FETCH_TREATMENT_DETAIL, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public Page<TreatmentDetailResponse> getAllTreatmentDetailsByPatient(Pageable pageable, @NonNull String patientId) {
        try {
            patientService.getPatientById(patientId);

            Page<TreatmentDetailModel> page = treatmentDetailRepository
                    .findByPatientClinicalHistory_Patient_IdPatient(patientId, pageable);
            return page.map(this::mapTreatmentDetailToDto);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_FETCH_TREATMENT_DETAILS, HttpStatus.INTERNAL_SERVER_ERROR,
                    ex);
        }
    }

    @Transactional
    public TreatmentDetailResponse updateTreatmentDetail(@NonNull Long id, @NonNull TreatmentDetailCapturingRequest request) {
        try {
            TreatmentDetailModel existing = treatmentDetailRepository.findById(id)
                    .orElseThrow(() -> new AppException(
                            String.format(ResponseMessages.TREATMENT_DETAIL_NOT_FOUND, id),
                            HttpStatus.NOT_FOUND));

            String currentScope = existing.getTreatment().getTreatmentScope().getName();

            TreatmentModel newTreatment = treatmentService.getTreatmentModelById(request.getTreatmentId());

            validateRequestDependencies(request);
            treatmentDetailMapper.updateEntity(request, existing);
            existing.setTreatment(newTreatment);

            // Asignar nuevo alumno si cambió
            StudentGroupModel studentGroup = studentGroupService.getStudentGroupByStudent(request.getStudentEnrollment());
            existing.setStudentGroup(studentGroup);

            existing.setStatus(ReviewStatus.IN_PROGRESS);

            TreatmentDetailModel savedModel = treatmentDetailRepository.save(existing);

            treatmentDetailToothService.handleTeethByScope(
                    currentScope,
                    newTreatment.getTreatmentScope().getName(),
                    savedModel.getIdTreatmentDetail(),
                    request
            );

            ExecutionReviewModel executionReview = createExecutionReviewWithContext(
                    savedModel.getIdTreatmentDetail(),
                    request.getProfessorClinicalAreaId(),
                    savedModel
            );

            // Mapear respuesta incluyendo profesor
            TreatmentDetailResponse response = treatmentDetailMapper.toDtoWithReviewerProfessor(executionReview);

            if (isToothTreatment(savedModel)) {
                addTeethList(response);
            }

            return response;
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_UPDATE_TREATMENT_DETAIL, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    private ExecutionReviewModel createExecutionReviewWithContext(Long treatmentDetailId, Long professorClinicalAreaId, TreatmentDetailModel treatmentDetail) {
        ProfessorClinicalAreaModel professorClinicalArea =
                professorClinicalAreaService.getProfessorClinicalAreaModel(professorClinicalAreaId);

        TreatmentStatusRequest executionRequest = TreatmentStatusRequest.builder()
                .treatmentDetailId(treatmentDetailId)
                .professorClinicalAreaId(professorClinicalAreaId)
                .status(ReviewStatus.IN_PROGRESS)
                .build();

        ExecutionReviewModel executionReview = executionReviewService.createExecutionReview(executionRequest);
        executionReview.setProfessorClinicalArea(professorClinicalArea);
        executionReview.setTreatmentDetail(treatmentDetail);

        return executionReview;
    }

    @Transactional
    public TreatmentDetailResponse updateTreatmentStatus(Long treatmentDetailId, TreatmentStatusUpdateRequest request) {
        try {
            TreatmentDetailModel treatment = treatmentDetailRepository.findById(treatmentDetailId)
                    .orElseThrow(() -> new AppException(
                            ResponseMessages.TREATMENT_DETAIL_NOT_FOUND, HttpStatus.NOT_FOUND));

            ReviewStatus currentStatus = treatment.getStatus();

            if (currentStatus != ReviewStatus.IN_PROGRESS) {
                throw new AppException(ResponseMessages.INVALID_TREATMENT_STATE_TRANSITION, HttpStatus.BAD_REQUEST);
            }

            validateExecutionStatus(request.getStatus());

            treatment.setStatus(request.getStatus());

            TreatmentStatusRequest executionRequest = buildExecutionStatusRequest(treatmentDetailId, request);
            executionReviewService.updateExecutionReview(treatment.getIdTreatmentDetail(), executionRequest);

            TreatmentDetailModel saved = treatmentDetailRepository.save(treatment);
            return mapTreatmentDetailToDto(saved);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_PROCESS_TREATMENT_AUTHORIZATION,
                    HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    private TreatmentDetailResponse mapTreatmentDetailToDto(TreatmentDetailModel treatmentDetailModel) {
        TreatmentDetailResponse response = executionReviewRepository
                .findTopByTreatmentDetail_IdTreatmentDetailOrderByIdExecutionReviewDesc(
                        treatmentDetailModel.getIdTreatmentDetail())
                .map(treatmentDetailMapper::toDtoWithReviewerProfessor)
                .orElseGet(() -> treatmentDetailMapper.toDto(treatmentDetailModel));

        if(isToothTreatment(treatmentDetailModel)) {
            addTeethList(response);
        }
        return response;
    }

    private void validateExecutionStatus(ReviewStatus status) {
        if (status != ReviewStatus.FINISHED &&
                status != ReviewStatus.CANCELLED) {
            throw new AppException(ResponseMessages.INVALID_TREATMENT_DETAIL_STATUS, HttpStatus.BAD_REQUEST);
        }
    }

    private TreatmentStatusRequest buildExecutionStatusRequest(Long treatmentDetailId, TreatmentStatusUpdateRequest request) {
        return TreatmentStatusRequest.builder()
                .treatmentDetailId(treatmentDetailId)
                .comment(request.getComments())
                .status(request.getStatus())
                .build();
    }

    private boolean isToothTreatment(TreatmentDetailModel treatment) {
        return Constants.TOOTH.equals(treatment.getTreatment().getTreatmentScope().getName());
    }

    private void addTeethList (TreatmentDetailResponse response) {
        response.setTeeth(
                treatmentDetailToothService.getTreatmentDetailTeethByTreatmentDetail(
                        response.getIdTreatmentDetail()));

    }
}