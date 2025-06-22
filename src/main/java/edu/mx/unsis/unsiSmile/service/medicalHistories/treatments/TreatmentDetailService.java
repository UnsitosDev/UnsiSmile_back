package edu.mx.unsis.unsiSmile.service.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.authenticationProviders.model.ERole;
import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.treatments.TreatmentDetailRequest;
import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.treatments.TreatmentDetailToothRequest;
import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.treatments.TreatmentStatusRequest;
import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.treatments.TreatmentStatusUpdateRequest;
import edu.mx.unsis.unsiSmile.dtos.response.UserResponse;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.treatments.TreatmentDetailResponse;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.treatments.TreatmentDetailToothResponse;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.treatments.TreatmentResponse;
import edu.mx.unsis.unsiSmile.dtos.response.students.TreatmentReportResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.treatments.TreatmentDetailMapper;
import edu.mx.unsis.unsiSmile.model.PatientClinicalHistoryModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.ReviewStatus;
import edu.mx.unsis.unsiSmile.model.medicalHistories.treatments.AuthorizedTreatmentModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.treatments.ExecutionReviewModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.treatments.TreatmentDetailModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.treatments.TreatmentModel;
import edu.mx.unsis.unsiSmile.model.professors.ProfessorClinicalAreaModel;
import edu.mx.unsis.unsiSmile.model.professors.ProfessorModel;
import edu.mx.unsis.unsiSmile.model.students.StudentGroupModel;
import edu.mx.unsis.unsiSmile.model.students.StudentModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IReviewStatusRepository;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.treatments.IAuthorizedTreatmentRepository;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.treatments.IExecutionReviewRepository;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.treatments.ITreatmentDetailRepository;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.treatments.ITreatmentRepository;
import edu.mx.unsis.unsiSmile.repository.professors.IProfessorClinicalAreaRepository;
import edu.mx.unsis.unsiSmile.repository.professors.IProfessorRepository;
import edu.mx.unsis.unsiSmile.repository.students.ISemesterRepository;
import edu.mx.unsis.unsiSmile.repository.students.IStudentRepository;
import edu.mx.unsis.unsiSmile.service.UserService;
import edu.mx.unsis.unsiSmile.service.medicalHistories.PatientClinicalHistoryService;
import edu.mx.unsis.unsiSmile.service.patients.PatientService;
import edu.mx.unsis.unsiSmile.service.professors.ProfessorClinicalAreaService;
import edu.mx.unsis.unsiSmile.service.socketNotifications.ReviewTreatmentService;
import edu.mx.unsis.unsiSmile.service.students.StudentGroupService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TreatmentDetailService {

    private final ITreatmentDetailRepository treatmentDetailRepository;
    private final IStudentRepository studentRepository;
    private final IProfessorRepository professorRepository;
    private final IProfessorClinicalAreaRepository professorClinicalAreaRepository;
    private final IReviewStatusRepository reviewStatusRepository;
    private final TreatmentDetailMapper treatmentDetailMapper;
    private final PatientClinicalHistoryService patientClinicalHistoryService;
    private final TreatmentService treatmentService;
    private final ITreatmentRepository treatmentRepository;
    private final StudentGroupService studentGroupService;
    private final PatientService patientService;
    private final UserService userService;
    private final TreatmentDetailToothService treatmentDetailToothService;
    private final ISemesterRepository semesterRepository;
    private final ReviewTreatmentService reviewTreatmentService;
    private final AuthorizedTreatmentService authorizedTreatmentService;
    private final ProfessorClinicalAreaService professorClinicalAreaService;
    private final IAuthorizedTreatmentRepository authorizedTreatmentRepository;
    private final ExecutionReviewService executionReviewService;
    private final IExecutionReviewRepository executionReviewRepository;

    @Transactional
    public TreatmentDetailResponse createTreatmentDetail(@NonNull TreatmentDetailRequest request) {
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

            AuthorizedTreatmentModel authorizedTreatment =
                    createAuthorizationTreatment(savedModel.getIdTreatmentDetail(), request.getProfessorClinicalAreaId());

            return this.toDtoWithAuthorizingProfessor(authorizedTreatment);
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
        professorClinicalAreaService.getProfessorClinicalAreaById(request.getProfessorClinicalAreaId());
    }

    private TreatmentDetailModel saveTreatmentDetail(TreatmentDetailRequest request,
                                                     TreatmentResponse treatmentResponse) {
        PatientClinicalHistoryModel clinicalHistory = patientClinicalHistoryService.save(
                request.getPatientId(),
                treatmentResponse.getClinicalHistoryCatalogId());

        UserResponse currentUser = userService.getCurrentUser();
        StudentGroupModel studentGroup = studentGroupService.getStudentGroupByStudent(currentUser.getUsername());

        TreatmentDetailModel model = treatmentDetailMapper.toEntity(request);
        model.setPatientClinicalHistory(clinicalHistory);
        model.setStudentGroup(studentGroup);

        return treatmentDetailRepository.save(model);
    }

    private TreatmentDetailResponse toDto(TreatmentDetailModel treatmentDetailModel) {
        TreatmentDetailResponse treatmentDetail = treatmentDetailMapper.toDto(treatmentDetailModel);

        if (Constants.TOOTH.equals(treatmentDetailModel.getTreatment().getTreatmentScope().getName())) {
            treatmentDetail.setTeeth(
                    treatmentDetailToothService.getTreatmentDetailTeethByTreatmentDetail(
                            treatmentDetailModel.getIdTreatmentDetail()));
        }
        return treatmentDetail;
    }

    @Transactional(readOnly = true)
    public TreatmentDetailResponse getTreatmentDetailById(@NonNull Long id) {
        try {
            TreatmentDetailModel model = treatmentDetailRepository.findById(id)
                    .orElseThrow(() -> new AppException(
                            String.format(ResponseMessages.TREATMENT_DETAIL_NOT_FOUND, id),
                            HttpStatus.NOT_FOUND));

            AuthorizedTreatmentModel authorizedTreatment = authorizedTreatmentRepository
                    .findTopByTreatmentDetail_IdTreatmentDetailOrderByIdAuthorizedTreatmentDesc(id)
                    .orElseThrow(() -> new AppException(
                            String.format(ResponseMessages.AUTHORIZATION_REQUEST_NOT_FOUND, id),
                            HttpStatus.NOT_FOUND));

            if(!ReviewStatus.APPROVED.equals(authorizedTreatment.getStatus())){
                throw new AppException(ResponseMessages.TREATMENT_DETAIL_AWAITING_APPROVAL, HttpStatus.BAD_REQUEST);
            }
            
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
    public TreatmentDetailResponse updateTreatmentDetail(@NonNull Long id, @NonNull TreatmentDetailRequest request) {
        try {
            TreatmentDetailModel existing = treatmentDetailRepository.findById(id)
                    .orElseThrow(() -> new AppException(
                            String.format(ResponseMessages.TREATMENT_DETAIL_NOT_FOUND, id),
                            HttpStatus.NOT_FOUND));
            String scope = existing.getTreatment().getTreatmentScope().getName();
            TreatmentModel newTreatment = treatmentRepository.findById(request.getTreatmentId())
                    .orElseThrow(() -> new AppException(ResponseMessages.TREATMENT_NOT_FOUND + id, HttpStatus.NOT_FOUND));

            validateRequestDependencies(request);
            treatmentDetailMapper.updateEntity(request, existing);
            existing.setTreatment(newTreatment);
            AuthorizedTreatmentModel authorizedTreatmentModel;
            if (!ReviewStatus.AWAITING_APPROVAL.equals(existing.getStatus())) {
                authorizedTreatmentModel = createAuthorizationTreatment(existing.getIdTreatmentDetail(), request.getProfessorClinicalAreaId());
                existing.setStatus(ReviewStatus.AWAITING_APPROVAL);
            } else {
                authorizedTreatmentModel = updateAuthorizationTreatment(existing.getIdTreatmentDetail(), request.getProfessorClinicalAreaId());
            }

            TreatmentDetailModel saved = treatmentDetailRepository.save(existing);

            // Lógica para el manejo de dientes
            handleTeethByScope(scope,
                    newTreatment.getTreatmentScope().getName(), saved.getIdTreatmentDetail(), request);

            return this.toDtoWithAuthorizingProfessor(authorizedTreatmentModel);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_UPDATE_TREATMENT_DETAIL, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    private void handleTeethByScope(String existingScope, String newScope, Long treatmentDetailId,
                                    TreatmentDetailRequest request) {

        boolean wasTooth = Constants.TOOTH.equals(existingScope);
        boolean isTooth = Constants.TOOTH.equals(newScope);

        if (wasTooth && !isTooth) {
            // CASO 1: eliminar todos los dientes
            treatmentDetailToothService.deleteAllByTreatmentDetailId(treatmentDetailId);
        } else if (!wasTooth && isTooth) {
            // CASO 2: guardar nuevos dientes
            TreatmentDetailToothRequest toothRequest = request.getTreatmentDetailToothRequest();

            if (toothRequest == null || toothRequest.getIdTeeth() == null || toothRequest.getIdTeeth().isEmpty()) {
                throw new AppException(ResponseMessages.TREATMENT_DETAIL_TOOTH_REQUEST_CANNOT_BE_NULL,
                        HttpStatus.BAD_REQUEST);
            }

            toothRequest.setIdTreatmentDetail(treatmentDetailId);
            treatmentDetailToothService.createTreatmentDetailTeeth(toothRequest);

        } else if (wasTooth) {
            // CASO 3: actualizar dientes
            TreatmentDetailToothRequest toothRequest = request.getTreatmentDetailToothRequest();

            if (toothRequest == null || toothRequest.getIdTeeth() == null || toothRequest.getIdTeeth().isEmpty()) {
                throw new AppException(ResponseMessages.TREATMENT_DETAIL_TOOTH_REQUEST_CANNOT_BE_NULL,
                        HttpStatus.BAD_REQUEST);
            }

            List<String> currentTeeth = treatmentDetailToothService.getAllTeethByTreatmentDetailId(treatmentDetailId);
            List<String> updatedTeeth = toothRequest.getIdTeeth();

            List<String> toDelete = currentTeeth.stream()
                    .filter(d -> !updatedTeeth.contains(d))
                    .toList();

            List<String> toAdd = updatedTeeth.stream()
                    .filter(d -> !currentTeeth.contains(d))
                    .toList();

            if (!toDelete.isEmpty()) {
                treatmentDetailToothService.deleteTeethByCodes(treatmentDetailId, toDelete);
            }

            if (!toAdd.isEmpty()) {
                TreatmentDetailToothRequest toAddRequest = new TreatmentDetailToothRequest();
                toAddRequest.setIdTreatmentDetail(treatmentDetailId);
                toAddRequest.setIdTeeth(toAdd);
                treatmentDetailToothService.createTreatmentDetailTeeth(toAddRequest);
            }
        }
    }

    @Transactional
    public void deleteTreatmentDetail(Long id) {
        try {
            TreatmentDetailModel model = treatmentDetailRepository.findById(id)
                    .orElseThrow(() -> new AppException(
                            String.format(ResponseMessages.TREATMENT_DETAIL_NOT_FOUND, id),
                            HttpStatus.NOT_FOUND));

            treatmentDetailRepository.delete(model);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_DELETE_TREATMENT_DETAIL, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public Page<TreatmentDetailResponse> getAllTreatmentDetailsByStudent(Pageable pageable, String idStudent,
                                                                         Long idTreatment) {
        try {
            Page<TreatmentDetailModel> page = getTreatmentDetailsByStudentGroups(pageable, idStudent, idTreatment);

            List<TreatmentDetailResponse> allResponses = page.getContent().stream()
                    .flatMap(model -> toDtoList(model).stream())
                    .collect(Collectors.toList());

            return new PageImpl<>(allResponses, pageable, allResponses.size());
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_FETCH_TREATMENT_DETAILS, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public Page<TreatmentDetailResponse> getAllTreatmentDetailsByStudentForReport(Pageable pageable, String idStudent,
                                                                                  Long idTreatment) {
        try {
            Page<TreatmentDetailModel> page = getTreatmentDetailsByStudentGroups(pageable, idStudent, idTreatment);
            return page.map(this::toDto);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_FETCH_TREATMENT_DETAILS, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    private Page<TreatmentDetailModel> getTreatmentDetailsByStudentGroups(Pageable pageable, String idStudent, Long idTreatment) {
        StudentModel studentModel = getStudentModel(idStudent);

        List<StudentGroupModel> studentGroups = studentGroupService.getAllStudentGroupsByStudent(studentModel);

        if (studentGroups.isEmpty()) {
            return Page.empty();
        }

        if (idTreatment != null) {
            treatmentService.getTreatmentById(idTreatment);
            return treatmentDetailRepository
                    .findAllByStudentGroupInAndTreatment_IdTreatment(studentGroups, idTreatment, pageable);
        }

        return treatmentDetailRepository
                .findAllByStudentGroupIn(studentGroups, pageable);
    }

    private StudentModel getStudentModel(String idStudent) {
        UserResponse currentUser = userService.getCurrentUser();

        if (currentUser.getRole().getRole().equals(ERole.ROLE_STUDENT)) {
            return studentRepository.findById(currentUser.getUsername())
                    .orElseThrow(() -> new AppException(
                            ResponseMessages.STUDENT_NOT_FOUND,
                            HttpStatus.NOT_FOUND));
        } else {
            return studentRepository.findById(idStudent)
                    .orElseThrow(() -> new AppException(
                            ResponseMessages.STUDENT_NOT_FOUND,
                            HttpStatus.NOT_FOUND));
        }
    }

    @Transactional
    public TreatmentDetailResponse sendToReview(Long id, Long professorClinicalAreaId, TreatmentDetailToothRequest toothRequest) {
        try {
            TreatmentDetailModel treatment = getValidTreatment(id, null);

            if (!treatment.getStatus().equals(ReviewStatus.IN_PROGRESS) &&
                    !treatment.getStatus().equals(ReviewStatus.REJECTED)) {
                throw new AppException(ResponseMessages.ERROR_TREATMENT_DETAIL_STATUS,
                        HttpStatus.BAD_REQUEST);
            }

            boolean isSectionInReview = reviewStatusRepository
                    .existsByPatientClinicalHistory_IdPatientClinicalHistoryAndStatus(
                            treatment.getPatientClinicalHistory().getIdPatientClinicalHistory(),
                            ReviewStatus.IN_REVIEW);

            if (isSectionInReview) {
                throw new AppException(ResponseMessages.ERROR_SECTIONS_IN_REVIEW,
                        HttpStatus.BAD_REQUEST);
            }

            ProfessorClinicalAreaModel professorClinicalArea = professorClinicalAreaRepository
                    .findById(professorClinicalAreaId)
                    .orElseThrow(() -> new AppException(
                            ResponseMessages.PROFESSOR_CLINICAL_AREA_NOT_FOUND + "con ID: " + professorClinicalAreaId,
                            HttpStatus.NOT_FOUND));

            treatment.setProfessor(professorClinicalArea.getProfessor());
            treatment.setStatus(ReviewStatus.IN_REVIEW);
            TreatmentDetailModel treatmentDetail = treatmentDetailRepository.save(treatment);

            TreatmentStatusRequest executionRequest = TreatmentStatusRequest.builder()
                    .treatmentDetailId(treatmentDetail.getIdTreatmentDetail())
                    .status(ReviewStatus.IN_REVIEW)
                    .build();

            executionReviewService.updateAuthorizedTreatment(treatmentDetail.getIdTreatmentDetail(), executionRequest);

            // Validación específica para alcance tipo TOOTH
            if (treatment.getTreatment().getTreatmentScope().getName().equals(Constants.TOOTH)) {
                if (toothRequest == null || toothRequest.getIdTeeth() == null || toothRequest.getIdTeeth().isEmpty()) {
                    throw new AppException(ResponseMessages.TREATMENT_DETAIL_TOOTH_REQUIRED, HttpStatus.BAD_REQUEST);
                }

                treatmentDetailToothService.updateToothReviewStatus(id, toothRequest);
            }
            // send notification
            this.sendNotifications(treatmentDetail);

            return mapTreatmentDetailToDto(treatmentDetail);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_SEND_TREATMENT_DETAIL_TO_REVIEW,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public TreatmentDetailResponse updateTreatmentStatus(Long treatmentDetailId, TreatmentStatusUpdateRequest request) {
        try {
            TreatmentDetailModel treatment = treatmentDetailRepository.findById(treatmentDetailId)
                    .orElseThrow(() -> new AppException(
                            ResponseMessages.TREATMENT_DETAIL_NOT_FOUND, HttpStatus.NOT_FOUND));

            ReviewStatus currentStatus = treatment.getStatus();

            // CASO 1: Tratamiento en etapa de autorización
            if (currentStatus == ReviewStatus.AWAITING_APPROVAL) {
                if (request.getStatus() != ReviewStatus.APPROVED && request.getStatus() != ReviewStatus.NOT_APPROVED) {
                    throw new AppException(ResponseMessages.INVALID_TREATMENT_DETAIL_STATUS, HttpStatus.BAD_REQUEST);
                }

                AuthorizedTreatmentModel auth = authorizedTreatmentRepository
                        .findTopByTreatmentDetail_IdTreatmentDetailOrderByIdAuthorizedTreatmentDesc(treatmentDetailId)
                        .orElseThrow(() -> new AppException(
                                String.format(ResponseMessages.AUTHORIZATION_REQUEST_NOT_FOUND, treatmentDetailId),
                                HttpStatus.NOT_FOUND));

                if (ReviewStatus.APPROVED.equals(auth.getStatus())) {
                    throw new AppException(ResponseMessages.TREATMENT_ALREADY_AUTHORIZED, HttpStatus.BAD_REQUEST);
                }

                if (ReviewStatus.NOT_APPROVED.equals(auth.getStatus())) {
                    throw new AppException(ResponseMessages.TREATMENT_ALREADY_REJECTED, HttpStatus.BAD_REQUEST);
                }

                if (request.getComments() != null && !request.getComments().isBlank()) {
                    auth.setComment(request.getComments());
                }

                auth.setStatus(request.getStatus());
                auth.setAuthorizedAt(LocalDateTime.now());
                authorizedTreatmentRepository.save(auth);

                // Si fue aprobado, pasa a IN_PROGRESS y se crea el primer executionReview
                if (request.getStatus() == ReviewStatus.APPROVED) {
                    treatment.setStatus(ReviewStatus.IN_PROGRESS);

                    TreatmentStatusRequest executionRequest = TreatmentStatusRequest.builder()
                            .treatmentDetailId(treatmentDetailId)
                            .professorClinicalAreaId(auth.getProfessorClinicalArea().getIdProfessorClinicalArea())
                            .comment(request.getComments())
                            .status(ReviewStatus.IN_PROGRESS)
                            .build();

                    executionReviewService.createExecutionReview(executionRequest);
                } else {
                    treatment.setStatus(ReviewStatus.NOT_APPROVED);
                }
            }
            // CASO 2: Tratamiento en ejecución
            else if (currentStatus == ReviewStatus.IN_REVIEW) {
                if (request.getStatus() != ReviewStatus.FINISHED && request.getStatus() != ReviewStatus.REJECTED
                        && request.getStatus() != ReviewStatus.CANCELLED) {
                    throw new AppException(ResponseMessages.INVALID_TREATMENT_DETAIL_STATUS, HttpStatus.BAD_REQUEST);
                }

                treatment = getValidTreatment(treatmentDetailId, currentStatus);
                treatment.setStatus(request.getStatus());

                TreatmentStatusRequest executionRequest = TreatmentStatusRequest.builder()
                        .treatmentDetailId(treatmentDetailId)
                        .comment(request.getComments())
                        .status(request.getStatus())
                        .build();

                executionReviewService.updateAuthorizedTreatment(treatmentDetailId, executionRequest);

                // Actualizar el estado de los dientes(en revision)
                if (treatment.getTreatment().getTreatmentScope().getName().equals(Constants.TOOTH)) {
                    treatmentDetailToothService.applyToothReviewAction(treatmentDetailId, request.getStatus());
                }

                sendNotifications(treatment);
            } else {
                throw new AppException(ResponseMessages.INVALID_TREATMENT_STATE_TRANSITION, HttpStatus.BAD_REQUEST);
            }

            TreatmentDetailModel saved = treatmentDetailRepository.save(treatment);
            return mapTreatmentDetailToDto(saved);

        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_PROCESS_TREATMENT_AUTHORIZATION,
                    HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    private void sendNotifications(TreatmentDetailModel treatment) {
        this.reviewTreatmentService.broadcastReviewTreatmentToStudent(
                treatment.getIdTreatmentDetail(),
                treatment.getPatientClinicalHistory().getPatient().getIdPatient(),
                treatmentDetailMapper.toDto(treatment));

        this.reviewTreatmentService.broadcastReviewTreatmentToProfessor(treatment.getProfessor().getIdProfessor(),
                treatmentDetailMapper.toDto(treatment));
    }

    private TreatmentDetailModel getValidTreatment(Long id, ReviewStatus requiredCurrentStatus) {
        TreatmentDetailModel treatment = treatmentDetailRepository.findById(id)
                .orElseThrow(() -> new AppException(
                        String.format(ResponseMessages.TREATMENT_DETAIL_NOT_FOUND, id),
                        HttpStatus.NOT_FOUND));

        if (requiredCurrentStatus != null && !treatment.getStatus().equals(requiredCurrentStatus)) {
            throw new AppException(ResponseMessages.ERROR_TREATMENT_DETAIL_STATUS_REVIEW,
                    HttpStatus.BAD_REQUEST);
        }

        return treatment;
    }

    @Transactional(readOnly = true)
    public Page<TreatmentDetailResponse> getTreatmentsInReviewByProfessor(String professorId, ReviewStatus status, Pageable pageable) {
        try {
            ProfessorModel professorModel = professorRepository.findById(professorId)
                    .orElseThrow(() -> new AppException(ResponseMessages.PROFESSOR_NOT_FOUND, HttpStatus.NOT_FOUND));

            List<ReviewStatus> authorizationStatuses = List.of(
                    ReviewStatus.AWAITING_APPROVAL,
                    ReviewStatus.NOT_APPROVED,
                    ReviewStatus.APPROVED
            );

            if (authorizationStatuses.contains(status)) {
                // Buscar en tabla de autorizaciones
                Page<AuthorizedTreatmentModel> auths = authorizedTreatmentRepository
                        .findByProfessorClinicalArea_Professor_idProfessorAndStatus(
                                professorModel.getIdProfessor(),
                                status,
                                pageable
                        );

                return auths.map(this::toDtoWithAuthorizingProfessor);
            } else {
                // Buscar en tabla de revisiones de ejecución
                Page<ExecutionReviewModel> reviews = executionReviewRepository
                        .findByProfessorClinicalArea_Professor_idProfessorAndStatus(
                                professorModel.getIdProfessor(),
                                status,
                                pageable
                        );

                return reviews.map(this::toDtoWithReviewerProfessor);
            }
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_FETCH_PATIENTS_WITH_TREATMENTS_IN_REVIEW,
                    HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<TreatmentReportResponse> getReport(String enrollment, Long semesterId,
                                                   LocalDate startDate, LocalDate endDate, ReviewStatus status) {
        try {
            StudentModel student = studentRepository.findById(enrollment)
                    .orElseThrow(() -> new AppException(ResponseMessages.STUDENT_NOT_FOUND, HttpStatus.NOT_FOUND));

            LocalDate endDateNormalized = normalizeDates(startDate, endDate);

            List<TreatmentDetailModel> treatments = getTreatments(enrollment, student, semesterId, status);

            treatments = filterByDateRange(treatments, startDate, endDateNormalized);

            String studentName = student.getPerson().getFullName();

            Map<String, List<TreatmentDetailModel>> groupedByTreatmentName = treatments.stream()
                    .collect(Collectors.groupingBy(t -> t.getTreatment().getName()));

            List<TreatmentReportResponse.GroupedTreatmentResponse> groupedTreatments = new ArrayList<>();

            for (Map.Entry<String, List<TreatmentDetailModel>> entry : groupedByTreatmentName.entrySet()) {
                String treatmentName = entry.getKey();
                List<TreatmentDetailModel> treatmentDetails = entry.getValue();

                List<TreatmentReportResponse.TreatmentReportDetailResponse> detailResponses = new ArrayList<>();

                for (TreatmentDetailModel t : treatmentDetails) {
                    String scope = t.getTreatment().getTreatmentScope().getName();

                    if (Constants.TOOTH.equals(scope)) {
                        List<String> teeth = treatmentDetailToothService
                                .getAllTeethByTreatmentDetailId(t.getIdTreatmentDetail());
                        for (String toothId : teeth) {
                            detailResponses.add(TreatmentReportResponse.TreatmentReportDetailResponse.builder()
                                    .treatmentDate(t.getEndDate().toLocalDate())
                                    .toothId(toothId)
                                    .patientName(t.getPatientClinicalHistory().getPatient().getPerson().getFullName())
                                    .medicalRecordNumber(String.valueOf(
                                            t.getPatientClinicalHistory().getPatient().getMedicalRecordNumber()))
                                    .professorName(t.getProfessor().getPerson().getFullName())
                                    .build());
                        }
                    } else {
                        detailResponses.add(TreatmentReportResponse.TreatmentReportDetailResponse.builder()
                                .treatmentDate(t.getEndDate().toLocalDate())
                                .toothId(null)
                                .patientName(t.getPatientClinicalHistory().getPatient().getPerson().getFullName())
                                .medicalRecordNumber(String
                                        .valueOf(t.getPatientClinicalHistory().getPatient().getMedicalRecordNumber()))
                                .professorName(t.getProfessor().getPerson().getFullName())
                                .build());
                    }
                }

                groupedTreatments.add(TreatmentReportResponse.GroupedTreatmentResponse.builder()
                        .treatmentName(treatmentName)
                        .details(detailResponses)
                        .build());
            }

            return List.of(
                    TreatmentReportResponse.builder()
                            .studentName(studentName)
                            .treatments(groupedTreatments)
                            .build());

        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_FETCH_TREATMENT_REPORT, HttpStatus.INTERNAL_SERVER_ERROR,
                    ex);
        }
    }

    private LocalDate normalizeDates(LocalDate startDate, LocalDate endDate) {
        if (startDate != null && endDate == null) {
            return LocalDate.now();
        }

        if (startDate != null && endDate.isBefore(startDate)) {
            throw new AppException(ResponseMessages.END_DATE_MUST_BE_GREATER_THAN_START_DATE, HttpStatus.BAD_REQUEST);
        }

        return endDate;
    }

    private List<TreatmentDetailModel> getTreatments(String enrollment, StudentModel student, Long semesterId,
                                                     ReviewStatus status) {
        if (semesterId != null) {
            semesterRepository.findById(semesterId)
                    .orElseThrow(() -> new AppException(ResponseMessages.SEMESTER_NOT_FOUND, HttpStatus.NOT_FOUND));
            return treatmentDetailRepository.findByStudentAndSemester(enrollment, semesterId, status);
        } else {
            return treatmentDetailRepository.findByStudentGroup_StudentAndStatusAndStatusKey(student, status,
                    Constants.ACTIVE);
        }
    }

    private List<TreatmentDetailModel> filterByDateRange(List<TreatmentDetailModel> treatments, LocalDate startDate,
                                                         LocalDate endDate) {
        if (startDate == null || endDate == null)
            return treatments;

        return treatments.stream()
                .filter(t -> {
                    LocalDate treatmentDate = t.getEndDate().toLocalDate();
                    return (treatmentDate.isEqual(startDate) || treatmentDate.isAfter(startDate)) &&
                            (treatmentDate.isEqual(endDate) || treatmentDate.isBefore(endDate));
                })
                .collect(Collectors.toList());
    }

    private List<TreatmentDetailResponse> toDtoList(TreatmentDetailModel treatmentDetailModel) {
        ReviewStatus status = treatmentDetailModel.getStatus();

        TreatmentDetailResponse response;

        try {
            response = mapTreatmentDetailToDto(treatmentDetailModel);

            // Si es tratamiento dental, crear lista por diente
            if (Constants.TOOTH.equals(treatmentDetailModel.getTreatment().getTreatmentScope().getName())) {
                List<TreatmentDetailToothResponse> teeth = treatmentDetailToothService
                        .getTreatmentDetailTeethByTreatmentDetail(
                                treatmentDetailModel.getIdTreatmentDetail());

                return teeth.stream()
                        .map(tooth -> {
                            TreatmentDetailResponse copy = new TreatmentDetailResponse();
                            BeanUtils.copyProperties(response, copy);
                            copy.setTeeth(List.of(tooth));
                            return copy;
                        })
                        .collect(Collectors.toList());
            } else {
                response.setTeeth(null);
                return Collections.singletonList(response);
            }

        } catch (Exception e) {
            throw new AppException(ResponseMessages.FAILED_FETCH_TREATMENT_DETAILS, HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Transactional
    public AuthorizedTreatmentModel createAuthorizationTreatment(@NonNull Long treatmentDetailModelId, @NonNull Long professorClinicalAreaId) {
        try {
            TreatmentStatusRequest request = TreatmentStatusRequest.builder()
                    .treatmentDetailId(treatmentDetailModelId)
                    .status(ReviewStatus.AWAITING_APPROVAL)
                    .professorClinicalAreaId(professorClinicalAreaId).build();

            return authorizedTreatmentService.createAuthorizedTreatment(request);
        } catch (AppException e) {
            throw e;
        }
    }

    @Transactional
    public AuthorizedTreatmentModel updateAuthorizationTreatment(@NonNull Long treatmentDetailModelId, @NonNull Long professorClinicalAreaId) {
        try {
            TreatmentStatusRequest request = TreatmentStatusRequest.builder()
                    .treatmentDetailId(treatmentDetailModelId)
                    .professorClinicalAreaId(professorClinicalAreaId).build();

            return authorizedTreatmentService.updateAuthorizedTreatmentByTreatmentId(request);
        } catch (AppException e) {
            throw e;
        }
    }

    private TreatmentDetailResponse toDtoWithAuthorizingProfessor(AuthorizedTreatmentModel model) {
        TreatmentDetailResponse response = toDto(model.getTreatmentDetail());

        response.setProfessor(TreatmentDetailResponse.ProfessorResponse.builder()
                .id(model.getProfessorClinicalArea().getProfessor().getIdProfessor())
                .name(model.getProfessorClinicalArea().getProfessor().getPerson().getFullName())
                .build());
        response.setComments(model.getComment());

        return response;
    }

    private TreatmentDetailResponse toDtoWithReviewerProfessor(ExecutionReviewModel model) {
        TreatmentDetailResponse response = toDto(model.getTreatmentDetail());

        response.setProfessor(TreatmentDetailResponse.ProfessorResponse.builder()
                .id(model.getProfessorClinicalArea().getProfessor().getIdProfessor())
                .name(model.getProfessorClinicalArea().getProfessor().getPerson().getFullName())
                .build());
        response.setComments(model.getComment());

        return response;
    }

    private TreatmentDetailResponse mapTreatmentDetailToDto(TreatmentDetailModel treatmentDetailModel) {
        List<ReviewStatus> authorizationStatuses = List.of(
                ReviewStatus.AWAITING_APPROVAL,
                ReviewStatus.NOT_APPROVED,
                ReviewStatus.APPROVED
        );

        if (authorizationStatuses.contains(treatmentDetailModel.getStatus())) {
            return authorizedTreatmentRepository
                    .findTopByTreatmentDetail_IdTreatmentDetailOrderByIdAuthorizedTreatmentDesc(
                            treatmentDetailModel.getIdTreatmentDetail())
                    .map(this::toDtoWithAuthorizingProfessor)
                    .orElseGet(() -> toDto(treatmentDetailModel));
        } else {
            return executionReviewRepository
                    .findTopByTreatmentDetail_IdTreatmentDetailOrderByIdExecutionReviewDesc(
                            treatmentDetailModel.getIdTreatmentDetail())
                    .map(this::toDtoWithReviewerProfessor)
                    .orElseGet(() -> toDto(treatmentDetailModel));
        }
    }
}