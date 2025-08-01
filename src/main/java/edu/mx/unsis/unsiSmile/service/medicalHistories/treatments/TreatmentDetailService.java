package edu.mx.unsis.unsiSmile.service.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.request.treatments.TreatmentDetailRequest;
import edu.mx.unsis.unsiSmile.dtos.request.treatments.TreatmentDetailToothRequest;
import edu.mx.unsis.unsiSmile.dtos.request.treatments.TreatmentStatusRequest;
import edu.mx.unsis.unsiSmile.dtos.request.treatments.TreatmentStatusUpdateRequest;
import edu.mx.unsis.unsiSmile.dtos.response.UserResponse;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.treatments.TreatmentDetailResponse;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.treatments.TreatmentResponse;
import edu.mx.unsis.unsiSmile.dtos.response.students.TreatmentReportResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.treatments.TreatmentDetailMapper;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.treatments.TreatmentDetailToothMapper;
import edu.mx.unsis.unsiSmile.model.patients.PatientMedicalRecordModel;
import edu.mx.unsis.unsiSmile.model.enums.ReviewStatus;
import edu.mx.unsis.unsiSmile.model.treatments.*;
import edu.mx.unsis.unsiSmile.model.professors.ProfessorClinicalAreaModel;
import edu.mx.unsis.unsiSmile.model.professors.ProfessorModel;
import edu.mx.unsis.unsiSmile.model.students.StudentGroupModel;
import edu.mx.unsis.unsiSmile.model.students.StudentModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IReviewStatusRepository;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.treatments.IAuthorizedTreatmentRepository;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.treatments.IExecutionReviewRepository;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.treatments.ITreatmentDetailRepository;
import edu.mx.unsis.unsiSmile.repository.professors.IProfessorRepository;
import edu.mx.unsis.unsiSmile.repository.students.ISemesterRepository;
import edu.mx.unsis.unsiSmile.repository.students.IStudentRepository;
import edu.mx.unsis.unsiSmile.service.UserService;
import edu.mx.unsis.unsiSmile.service.medicalHistories.PatientMedicalRecordService;
import edu.mx.unsis.unsiSmile.service.patients.PatientService;
import edu.mx.unsis.unsiSmile.service.professors.ProfessorClinicalAreaService;
import edu.mx.unsis.unsiSmile.service.socketNotifications.ReviewTreatmentService;
import edu.mx.unsis.unsiSmile.service.students.StudentGroupService;
import edu.mx.unsis.unsiSmile.service.students.StudentService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
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
    private final IReviewStatusRepository reviewStatusRepository;
    private final TreatmentDetailMapper treatmentDetailMapper;
    private final PatientMedicalRecordService patientMedicalRecordService;
    private final TreatmentService treatmentService;
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
    private final StudentService studentService;
    private final TreatmentDetailToothMapper treatmentDetailToothMapper;

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

            TreatmentDetailResponse response = treatmentDetailMapper.toDtoWithAuthorizingProfessor(authorizedTreatment);

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
        professorClinicalAreaService.getProfessorClinicalAreaById(request.getProfessorClinicalAreaId());
    }

    private TreatmentDetailModel saveTreatmentDetail(TreatmentDetailRequest request,
                                                     TreatmentResponse treatmentResponse) {
        PatientMedicalRecordModel medicalRecord = patientMedicalRecordService.save(
                request.getPatientId(),
                treatmentResponse.getMedicalRecordCatalogId());

        UserResponse currentUser = userService.getCurrentUser();
        StudentGroupModel studentGroup = studentGroupService.getStudentGroupByStudent(currentUser.getUsername());

        TreatmentDetailModel model = treatmentDetailMapper.toEntity(request);
        model.setPatientMedicalRecord(medicalRecord);
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

            AuthorizedTreatmentModel authorizedTreatment = authorizedTreatmentService.getAuthorizedTreatmentByTreatmentDetailId(id);

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
                    .findByPatientMedicalRecord_Patient_IdPatient(patientId, pageable);
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

            TreatmentModel newTreatment = treatmentService.getTreatmentModelById(request.getTreatmentId());

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
            treatmentDetailToothService.handleTeethByScope(scope,
                    newTreatment.getTreatmentScope().getName(), saved.getIdTreatmentDetail(), request);

            TreatmentDetailResponse response = treatmentDetailMapper.toDtoWithAuthorizingProfessor(authorizedTreatmentModel);

            if(isToothTreatment(saved)) {
                addTeethList(response);
            }

            return response;
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_UPDATE_TREATMENT_DETAIL, HttpStatus.INTERNAL_SERVER_ERROR, ex);
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
    public Page<TreatmentDetailResponse> getAllTreatmentDetailsByStudentForReport(
            Pageable pageable, String idStudent, Long idTreatment) {
        try {
            List<TreatmentDetailResponse> treatments = getFinalizedTreatmentDetailsByStudent(idStudent, idTreatment);

            if(treatments.isEmpty()) {
                return new PageImpl<>(Collections.emptyList(), pageable, 0);
            }

            return new PageImpl<>(Collections.singletonList(treatments.get(0)), pageable, 1);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_FETCH_TREATMENT_DETAILS,
                    HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    private Page<TreatmentDetailModel> getTreatmentDetailsByStudentGroups(Pageable pageable, String idStudent, Long idTreatment) {
        StudentModel studentModel = studentService.getStudentModel(idStudent);

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

    @Transactional
    public TreatmentDetailResponse sendToReview(Long id, Long professorClinicalAreaId, TreatmentDetailToothRequest toothRequest) {
        try {
            TreatmentDetailModel treatment = getValidTreatment(id, null);
            boolean isTooth = isToothTreatment(treatment);

            // Validar estado general del tratamiento
            boolean validGeneralStatus = treatment.getStatus().equals(ReviewStatus.IN_PROGRESS) ||
                    treatment.getStatus().equals(ReviewStatus.REJECTED);

            // Validar si hay al menos un diente en progreso o rechazado
            boolean hasToothInValidStatus = false;
            if (isTooth) {
                hasToothInValidStatus = treatmentDetailToothService.canSendToReviewBasedOnTeeth(
                        treatment.getIdTreatmentDetail()
                );
            }

            // Lanzar error si no cumple con ninguna de las condiciones válidas
            if (!validGeneralStatus && !(isTooth && hasToothInValidStatus)) {
                throw new AppException(ResponseMessages.ERROR_TREATMENT_DETAIL_STATUS, HttpStatus.BAD_REQUEST);
            }

            // Validar si hay otra sección en revisión
            boolean isSectionInReview = reviewStatusRepository
                    .existsByPatientMedicalRecord_IdPatientMedicalRecordAndStatus(
                            treatment.getPatientMedicalRecord().getIdPatientMedicalRecord(),
                            ReviewStatus.IN_REVIEW);

            if (isSectionInReview) {
                throw new AppException(ResponseMessages.ERROR_SECTIONS_IN_REVIEW,
                        HttpStatus.BAD_REQUEST);
            }

            // Obtener profesor y actualizar estado del tratamiento
            ProfessorClinicalAreaModel professorClinicalArea = professorClinicalAreaService.getProfessorClinicalAreaModel(professorClinicalAreaId);
            treatment.setProfessor(professorClinicalArea.getProfessor());
            treatment.setStatus(ReviewStatus.IN_REVIEW);
            TreatmentDetailModel treatmentDetail = treatmentDetailRepository.save(treatment);

            // Preparar request para ExecutionReview
            TreatmentStatusRequest executionRequest = TreatmentStatusRequest.builder()
                    .treatmentDetailId(treatmentDetail.getIdTreatmentDetail())
                    .professorClinicalAreaId(professorClinicalAreaId)
                    .status(ReviewStatus.IN_REVIEW)
                    .build();

            // Crear o actualizar revisión de ejecución
            ReviewStatus currentStatus = treatment.getStatus();
            ExecutionReviewModel executionReview;
            if (ReviewStatus.IN_PROGRESS.equals(currentStatus)) {
                executionReview = executionReviewService.updateExecutionReview(treatmentDetail.getIdTreatmentDetail(), executionRequest);
            } else {
                executionReview = executionReviewService.createExecutionReview(executionRequest);
                executionReview.setProfessorClinicalArea(professorClinicalArea);
                executionReview.setTreatmentDetail(treatmentDetail);
            }

            // Validación específica para tratamientos dentales
            if (isTooth) {
                if (toothRequest == null || toothRequest.getIdTeeth() == null || toothRequest.getIdTeeth().isEmpty()) {
                    throw new AppException(ResponseMessages.TREATMENT_DETAIL_TOOTH_REQUIRED, HttpStatus.BAD_REQUEST);
                }

                treatmentDetailToothService.updateToothReviewStatus(id, toothRequest, executionReview);
            }

            // Enviar notificación
            this.sendNotifications(treatmentDetail);

            // Armar respuesta
            TreatmentDetailResponse response = treatmentDetailMapper.toDtoWithReviewerProfessor(executionReview);

            if (isToothTreatment(executionReview.getTreatmentDetail())) {
                addTeethList(response);
            }

            return response;
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

            if (currentStatus != ReviewStatus.IN_REVIEW) {
                throw new AppException(ResponseMessages.INVALID_TREATMENT_STATE_TRANSITION, HttpStatus.BAD_REQUEST);
            }

            validateExecutionStatus(request.getStatus());

            treatment = getValidTreatment(treatmentDetailId, currentStatus);
            treatment.setStatus(request.getStatus());

            TreatmentStatusRequest executionRequest = buildExecutionStatusRequest(treatmentDetailId, request);
            executionReviewService.updateExecutionReview(treatment.getIdTreatmentDetail(), executionRequest);

            sendNotifications(treatment);

            if (!isToothTreatment(treatment) && request.getStatus() == ReviewStatus.FINISHED) {
                treatment.setEndDate(LocalDateTime.now().toLocalDate().atStartOfDay());
            }
            else if (isToothTreatment(treatment) && request.getStatus() == ReviewStatus.FINISHED) {
                boolean canSendToReview = treatmentDetailToothService.canSendToReviewBasedOnTeeth(treatment.getIdTreatmentDetail());
                if (!canSendToReview) {
                    treatment.setEndDate(LocalDateTime.now().toLocalDate().atStartOfDay());
                }
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
                treatment.getPatientMedicalRecord().getPatient().getIdPatient(),
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

            Page<ExecutionReviewModel> reviews = executionReviewRepository
                    .findByProfessorClinicalArea_Professor_idProfessorAndStatus(
                            professorModel.getIdProfessor(),
                            status,
                            pageable
                    );

            return reviews.map(executionReviewModel -> {
                TreatmentDetailResponse response = treatmentDetailMapper.toDtoWithReviewerProfessor(executionReviewModel);

                if(isToothTreatment(executionReviewModel.getTreatmentDetail())) {
                    addTeethList(response, executionReviewModel);
                }

                return response;
            });
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

            treatments = filterTreatmentsByDateRange(treatments, startDate, endDateNormalized);

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
                                    .patientName(t.getPatientMedicalRecord().getPatient().getPerson().getFullName())
                                    .medicalRecordNumber(String.valueOf(
                                            t.getPatientMedicalRecord().getPatient().getMedicalRecordNumber()))
                                    .professorName(t.getProfessor().getPerson().getFullName())
                                    .build());
                        }
                    } else {
                        detailResponses.add(TreatmentReportResponse.TreatmentReportDetailResponse.builder()
                                .treatmentDate(t.getEndDate().toLocalDate())
                                .toothId(null)
                                .patientName(t.getPatientMedicalRecord().getPatient().getPerson().getFullName())
                                .medicalRecordNumber(String
                                        .valueOf(t.getPatientMedicalRecord().getPatient().getMedicalRecordNumber()))
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

    private List<TreatmentDetailModel> filterTreatmentsByDateRange(List<TreatmentDetailModel> treatments, LocalDate startDate,
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
        try {
            // Si no es tratamiento dental, mapeo normal
            if (!isToothTreatment(treatmentDetailModel)) {
                TreatmentDetailResponse response = mapTreatmentDetailToDto(treatmentDetailModel);
                response.setTeeth(null);
                return Collections.singletonList(response);
            }

            // Cargar dientes asociados al tratamiento
            List<TreatmentDetailToothModel> teeth = treatmentDetailToothService
                    .getTreatmentDetailTeethModelByTreatmentDetail(treatmentDetailModel.getIdTreatmentDetail());

            return teeth.stream().map(tooth -> {
                // Crear respuesta base con profesor aprobador (siempre obligatorio)
                TreatmentDetailResponse copy = authorizedTreatmentRepository
                        .findTopByTreatmentDetail_IdTreatmentDetailOrderByIdAuthorizedTreatmentDesc(
                                treatmentDetailModel.getIdTreatmentDetail())
                        .map(treatmentDetailMapper::toDtoWithAuthorizingProfessor)
                        .orElseGet(() -> treatmentDetailMapper.toDto(treatmentDetailModel));

                // Asignar profesor revisor si el diente tiene ejecución
                if (tooth.getStatus() != null) {
                    executionReviewRepository.findById(tooth.getStatus().getIdExecutionReview())
                            .ifPresent(executionReview ->
                                    treatmentDetailMapper.setReviewerProfessor(copy, executionReview)
                            );
                }

                // Asignar el diente individual al response
                copy.setTeeth(List.of(treatmentDetailToothMapper.toDto(tooth)));

                // Asignar el estado directamente desde el diente
                copy.setStatus(tooth.getStatus() != null
                        ? tooth.getStatus().getStatus().toString()
                        : ReviewStatus.IN_PROGRESS.toString());

                return copy;
            }).collect(Collectors.toList());

        } catch (Exception e) {
            throw new AppException(ResponseMessages.FAILED_FETCH_TREATMENT_DETAILS,
                    HttpStatus.INTERNAL_SERVER_ERROR, e);
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

    private TreatmentDetailResponse mapTreatmentDetailToDto(TreatmentDetailModel treatmentDetailModel) {
        List<ReviewStatus> authorizationStatuses = List.of(
                ReviewStatus.AWAITING_APPROVAL,
                ReviewStatus.NOT_APPROVED,
                ReviewStatus.APPROVED
        );
        TreatmentDetailResponse response;
        if (authorizationStatuses.contains(treatmentDetailModel.getStatus())) {
            // Fase de autorización: solo asignar aprobador
            response = authorizedTreatmentRepository
                    .findTopByTreatmentDetail_IdTreatmentDetailOrderByIdAuthorizedTreatmentDesc(
                            treatmentDetailModel.getIdTreatmentDetail())
                    .map(treatmentDetailMapper::toDtoWithAuthorizingProfessor)
                    .orElseGet(() -> treatmentDetailMapper.toDto(treatmentDetailModel));
        } else {
            // Agregar revisor (si existe)
            response = executionReviewRepository
                    .findTopByTreatmentDetail_IdTreatmentDetailOrderByIdExecutionReviewDesc(
                            treatmentDetailModel.getIdTreatmentDetail())
                    .map(treatmentDetailMapper::toDtoWithReviewerProfessor)
                    .orElseGet(() -> treatmentDetailMapper.toDto(treatmentDetailModel));

            // Agregar aprobador (si existe)
            authorizedTreatmentRepository
                    .findTopByTreatmentDetail_IdTreatmentDetailOrderByIdAuthorizedTreatmentDesc(
                            treatmentDetailModel.getIdTreatmentDetail())
                    .ifPresent(authorizedTreatment ->
                            treatmentDetailMapper.setAuthorizingProfessor(response, authorizedTreatment)
                    );
        }

        // Dientes y nuevos estatus (si aplica)
        if (isToothTreatment(treatmentDetailModel)) {
            addTeethList(response);
            if (!authorizationStatuses.contains(treatmentDetailModel.getStatus())) {
                addNewStatus(response);
            }
        }
        return response;
    }

    @Transactional(readOnly = true)
    public Page<TreatmentDetailResponse> getTreatmentsByProfessorAndStatus(String professorId, ReviewStatus status, Pageable pageable) {
        try {
            ProfessorModel professorModel = professorRepository.findById(professorId)
                    .orElseThrow(() -> new AppException(ResponseMessages.PROFESSOR_NOT_FOUND, HttpStatus.NOT_FOUND));

            Page<AuthorizedTreatmentModel> auths = authorizedTreatmentRepository
                    .findByProfessorClinicalArea_Professor_idProfessorAndStatus(
                            professorModel.getIdProfessor(),
                            status,
                            pageable
                    );

            return auths.map(authorizedTreatmentModel -> {
                TreatmentDetailResponse response = treatmentDetailMapper.toDtoWithAuthorizingProfessor(authorizedTreatmentModel);

                if(isToothTreatment(authorizedTreatmentModel.getTreatmentDetail())) {
                    addTeethList(response);
                }

                return response;
            });
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_FETCH_PATIENTS_WITH_TREATMENTS_IN_REVIEW,
                    HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public TreatmentDetailResponse approveOrRejectTreatment(Long treatmentDetailId, TreatmentStatusUpdateRequest request) {
        try {
            TreatmentDetailModel treatment = treatmentDetailRepository.findById(treatmentDetailId)
                    .orElseThrow(() -> new AppException(
                            ResponseMessages.TREATMENT_DETAIL_NOT_FOUND, HttpStatus.NOT_FOUND));

            ReviewStatus currentStatus = treatment.getStatus();

            if (currentStatus != ReviewStatus.AWAITING_APPROVAL &&
                    currentStatus != ReviewStatus.NOT_APPROVED) {
                throw new AppException(ResponseMessages.INVALID_TREATMENT_DETAIL_STATUS, HttpStatus.BAD_REQUEST);
            }

            AuthorizedTreatmentModel auth = authorizedTreatmentService
                    .getAuthorizedTreatmentByTreatmentDetailId(treatmentDetailId);

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

            treatmentDetailRepository.save(treatment);
            return treatmentDetailMapper.toDtoWithAuthorizingProfessor(auth);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_PROCESS_TREATMENT_AUTHORIZATION,
                    HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    private void validateExecutionStatus(ReviewStatus status) {
        if (status != ReviewStatus.FINISHED &&
                status != ReviewStatus.REJECTED &&
                status != ReviewStatus.CANCELLED) {
            throw new AppException(ResponseMessages.INVALID_TREATMENT_DETAIL_STATUS, HttpStatus.BAD_REQUEST);
        }
    }

    private TreatmentStatusRequest buildExecutionStatusRequest(Long treatmentDetailId, TreatmentStatusUpdateRequest request) {
        return TreatmentStatusRequest.builder()
                .treatmentDetailId(treatmentDetailId)
                .comment(request.getComments())
                .status(request.getStatus())
                .professorClinicalAreaId(null)
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

    private void addTeethList(TreatmentDetailResponse response, ExecutionReviewModel executionReviewModel) {
        response.setTeeth(
                treatmentDetailToothService.getTreatmentDetailTeethByReview(
                        response.getIdTreatmentDetail(),
                        executionReviewModel.getIdExecutionReview()
                )
        );
        response.setStatus(executionReviewModel.getStatus().toString());
    }

    private void addNewStatus(TreatmentDetailResponse response){
        boolean isInProgress = treatmentDetailToothService.canSendToReviewBasedOnTeeth(response.getIdTreatmentDetail());
        if (isInProgress) {
            response.setStatus(ReviewStatus.IN_PROGRESS.toString());
        }
    }

    @Transactional(readOnly = true)
    public List<TreatmentDetailResponse> getFinalizedTreatmentDetailsByStudent(
            String idStudent, Long idTreatment) {
        try {
            StudentModel studentModel = studentService.getStudentModel(idStudent);
            List<StudentGroupModel> studentGroups = studentGroupService.getAllStudentGroupsByStudent(studentModel);
            TreatmentModel treatment = treatmentService.getTreatmentModelById(idTreatment);

            List<TreatmentDetailModel> treatmentModels;
            if(treatment.getTreatmentScope().getName().equals(Constants.TOOTH)) {
                treatmentModels = treatmentDetailRepository.findByStudentGroupsAndTreatmentDetailIdAndToothStatus(
                        studentGroups, idTreatment, ReviewStatus.FINISHED);
            } else {
                treatmentModels = treatmentDetailRepository
                        .findByStudentGroupInAndTreatment_IdTreatmentAndStatusOrderByIdTreatmentDetailDesc(
                                studentGroups, idTreatment, ReviewStatus.FINISHED);
            }

            return treatmentModels.stream()
                    .map(this::mapTreatmentDetailToDto)
                    .collect(Collectors.toList());
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_TO_FETCH_TREATMENTS,
                    HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public TreatmentDetailResponse toTreatmentDetailResponse(TreatmentDetailModel model) {
            return mapTreatmentDetailToDto(model);
    }
}