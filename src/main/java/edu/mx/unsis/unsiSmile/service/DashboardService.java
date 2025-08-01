package edu.mx.unsis.unsiSmile.service;

import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.response.*;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.model.enums.ReviewStatus;
import edu.mx.unsis.unsiSmile.model.professors.ProfessorGroupModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IReviewStatusRepository;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.treatments.IAuthorizedTreatmentRepository;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.treatments.IExecutionReviewRepository;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.treatments.ITreatmentDetailRepository;
import edu.mx.unsis.unsiSmile.repository.patients.IPatientRepository;
import edu.mx.unsis.unsiSmile.repository.professors.IProfessorGroupRepository;
import edu.mx.unsis.unsiSmile.repository.professors.IProfessorRepository;
import edu.mx.unsis.unsiSmile.repository.students.IStudentGroupRepository;
import edu.mx.unsis.unsiSmile.repository.students.IStudentPatientRepository;
import edu.mx.unsis.unsiSmile.repository.students.IStudentRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final IPatientRepository patientRepository;
    private final IStudentRepository studentRepository;
    private final IStudentPatientRepository studentPatientRepository;
    private final IProfessorGroupRepository professorGroupRepository;
    private final IStudentGroupRepository studentGroupRepository;
    private final IProfessorRepository professorRepository;
    private final UserService userService;
    private final IReviewStatusRepository reviewStatusRepository;
    private final ITreatmentDetailRepository treatmentDetailRepository;
    private final IAuthorizedTreatmentRepository authorizedTreatmentRepository;
    private final IExecutionReviewRepository executionReviewRepository;

    @Transactional(readOnly = true)
    public StudentDashboardResponse getStudentDashboard() {
        try {
            String username = getUserName();
            return getStudentDashboardMetrics(username);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ResponseMessages.ERROR_STUDENT_DASHBOARD, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    private StudentDashboardResponse getStudentDashboardMetrics(String enrollment) {
        try {
            Timestamp lastMonthTimestamp = Timestamp.valueOf(LocalDateTime.now().minusMonths(1));

            StudentDashboardResponse.StudentDashboardResponseBuilder builder = StudentDashboardResponse.builder()
                    .totalPatients(studentPatientRepository.countPatientsForStudent(enrollment, Constants.ACTIVE))
                    .patientsWithDisability(studentPatientRepository.countPatientsWithDisabilityByStudent(enrollment, Constants.ACTIVE))
                    .patientsRegisteredLastMonth(studentPatientRepository.countPatientsRegisteredSinceByStudent(enrollment, lastMonthTimestamp, Constants.ACTIVE))
                    .patientsByNationality(convertToMap(studentPatientRepository.countPatientsByNationalityByStudent(enrollment, Constants.ACTIVE)))
                    .patientsUnder18(studentPatientRepository.countPatientsUnder18ByStudent(enrollment, Constants.ACTIVE))
                    .patientsBetween18And60(studentPatientRepository.countPatientsBetween18And60ByStudent(enrollment, Constants.ACTIVE))
                    .patientsOver60(studentPatientRepository.countPatientsOver60ByStudent(enrollment, Constants.ACTIVE))
                    .rejectedTreatments(treatmentDetailRepository.countByStudentAndStatus(enrollment, ReviewStatus.REJECTED))
                    .inReviewTreatments(treatmentDetailRepository.countByStudentAndStatus(enrollment, ReviewStatus.IN_REVIEW))
                    .progressingTreatments(treatmentDetailRepository.countByStudentAndStatus(enrollment, ReviewStatus.IN_PROGRESS));

            TreatmentCountResponse treatments = getTreatmentCountResponseByStudent(enrollment, null, null);
            builder.treatments(treatments);

            return builder.build();
        } catch (Exception e) {
            throw new AppException(ResponseMessages.ERROR_STUDENT_DASHBOARD, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public TreatmentCountResponse getTreatmentCountResponse(Timestamp startDate, Timestamp endDate) {
        List<Object[]> toothScope;
        List<Object[]> generalScope;

        if (startDate != null && endDate != null) {
            toothScope = treatmentDetailRepository.countToothScopeTreatmentsBetweenDates(
                    ReviewStatus.FINISHED, startDate, endDate
            );
            generalScope = treatmentDetailRepository.countGeneralScopeTreatmentsBetweenDates(
                    ReviewStatus.FINISHED, startDate, endDate
            );
        } else {
            toothScope = treatmentDetailRepository.countAllToothScopeTreatments(ReviewStatus.FINISHED);
            generalScope = treatmentDetailRepository.countAllGeneralScopeTreatments(ReviewStatus.FINISHED);
        }
        Map<String, Long> treatmentCounts = mergeTreatmentCounts(toothScope, generalScope);
        return mapToTreatmentCountResponse(treatmentCounts);
    }

    @Transactional(readOnly = true)
    public ProfessorDashboardResponse getProfessorDashboardMetrics() {
        try {
            String employeeNumber = getUserName();
            Pair<List<ProfessorGroupModel>, Long> data = getProfessorGroupsAndStudentCount(employeeNumber);
            List<ProfessorGroupModel> professorGroups = data.getLeft();
            Long studentCount = data.getRight();

            return ProfessorDashboardResponse.builder()
                    .totalGroups(professorGroups.size())
                    .totalStudents(studentCount)
                    .build();
        } catch (DataAccessException e) {
            throw new AppException(ResponseMessages.ERROR_PROFESSOR_DASHBOARD, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public AdminDashboardResponse getAdminDashboardMetrics() {
        try {
            Timestamp lastMonthTimestamp = Timestamp.valueOf(LocalDateTime.now().minusMonths(1));

            AdminDashboardResponse.AdminDashboardResponseBuilder builder = AdminDashboardResponse.builder()
                    .totalPatients(patientRepository.countTotalPatients(Constants.ACTIVE))
                    .patientsWithDisability(patientRepository.countPatientsWithDisability(Constants.ACTIVE))
                    .patientsRegisteredLastMonth(patientRepository.countPatientsRegisteredSince(lastMonthTimestamp, Constants.ACTIVE))
                    .patientsByNationality(convertToMap(patientRepository.countPatientsByNationality(Constants.ACTIVE)))
                    .totalStudents(studentRepository.countTotalStudents(Constants.ACTIVE))
                    .studentsRegisteredLastMonth(studentRepository.countStudentsRegisteredSince(lastMonthTimestamp, Constants.ACTIVE))
                    .totalProfessors(professorRepository.countTotalProfessors(Constants.ACTIVE))
                    .rejectedTreatments(treatmentDetailRepository.countByStatusAndStatusKey(ReviewStatus.REJECTED, Constants.ACTIVE))
                    .progressingTreatments(treatmentDetailRepository.countByStatusAndStatusKey(ReviewStatus.IN_PROGRESS, Constants.ACTIVE))
                    .inReviewTreatments(treatmentDetailRepository.countByStatusAndStatusKey(ReviewStatus.IN_REVIEW, Constants.ACTIVE));

            List<Object[]> toothScope = treatmentDetailRepository.countAllToothScopeTreatments(ReviewStatus.FINISHED);
            List<Object[]> generalScope = treatmentDetailRepository.countAllGeneralScopeTreatments(ReviewStatus.FINISHED);

            Map<String, Long> treatmentCounts = mergeTreatmentCounts(toothScope, generalScope);
            TreatmentCountResponse treatments = mapToTreatmentCountResponse(treatmentCounts);
            builder.treatments(treatments);

            return builder.build();
        } catch (DataAccessException e) {
            throw new AppException(ResponseMessages.ERROR_ADMIN_DASHBOARD, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Map<String, Long> convertToMap(List<Object[]> dataList) {
        Map<String, Long> map = new HashMap<>();
        if (dataList != null) {
            for (Object[] row : dataList) {
                String key = row[0] != null ? row[0].toString() : "N/A";
                Long count = ((Number) row[1]).longValue();
                map.put(key, count);
            }
        }
        return map;
    }

    private String getUserName() {
        UserResponse user = userService.getCurrentUser();
        return user.getUsername();
    }

    @Transactional(readOnly = true)
    public ClinicalSupervisorDashboardResponse getClinicalSupervisorDashboardMetrics() {
        try {
            String employeeNumber = getUserName();
            return ClinicalSupervisorDashboardResponse.builder()
                    .treatmentsAwaitingApproval(
                            authorizedTreatmentRepository.countByStatusAndProfessor(employeeNumber, ReviewStatus.AWAITING_APPROVAL, Constants.ACTIVE)
                    )
                    .treatmentsApproved(
                            authorizedTreatmentRepository.countByStatusAndProfessor(employeeNumber, ReviewStatus.APPROVED, Constants.ACTIVE)
                    )
                    .treatmentsNotApproved(
                            authorizedTreatmentRepository.countByStatusAndProfessor(employeeNumber, ReviewStatus.NOT_APPROVED, Constants.ACTIVE)
                    )
                    .treatmentsInProgress(
                            executionReviewRepository.countByStatusAndProfessor(employeeNumber, ReviewStatus.IN_PROGRESS, Constants.ACTIVE)
                    )
                    .treatmentsInReview(
                            executionReviewRepository.countByStatusAndProfessor(employeeNumber, ReviewStatus.IN_REVIEW, Constants.ACTIVE)
                    )
                    .treatmentsRejected(
                            executionReviewRepository.countByStatusAndProfessor(employeeNumber, ReviewStatus.REJECTED, Constants.ACTIVE)
                    )
                    .treatmentsCompleted(
                            executionReviewRepository.countByStatusAndProfessor(employeeNumber, ReviewStatus.FINISHED, Constants.ACTIVE)
                    )
                    .medicalRecordsInReview(
                            reviewStatusRepository.countByStatus(employeeNumber, ReviewStatus.IN_REVIEW, Constants.ACTIVE)
                    )
                    .medicalRecordsRejected(
                            reviewStatusRepository.countByStatus(employeeNumber, ReviewStatus.REJECTED, Constants.ACTIVE)
                    )
                    .medicalRecordsAccepted(
                            reviewStatusRepository.countByStatus(employeeNumber, ReviewStatus.APPROVED, Constants.ACTIVE)
                    )
                    .build();
        } catch (DataAccessException e) {
            throw new AppException(ResponseMessages.ERROR_CLINICAL_SUPERVISOR_DASHBOARD, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Pair<List<ProfessorGroupModel>, Long> getProfessorGroupsAndStudentCount(String employeeNumber) {
        List<ProfessorGroupModel> professorGroups = professorGroupRepository
                .findByProfessorAndGroupStatus(employeeNumber, Constants.ACTIVE);

        Set<Long> idsGroups = professorGroups.stream()
                .map(professorGroup -> professorGroup.getGroup().getIdGroup())
                .collect(Collectors.toSet());

        Long studentCount = studentGroupRepository.countStudentsByGroupIds(idsGroups);

        return Pair.of(professorGroups, studentCount);
    }

    private TreatmentCountResponse mapToTreatmentCountResponse(Map<String, Long> treatmentCounts) {
        TreatmentCountResponse.TreatmentCountResponseBuilder builder = TreatmentCountResponse.builder();

        treatmentCounts.forEach((name, count) -> {
            switch (name) {
                case "Resinas" -> builder.resins(count);
                case "Profilaxis" -> builder.prophylaxis(count);
                case "Fluorosis" -> builder.fluorosis(count);
                case "Selladores de fosetas y fisuras" -> builder.pitAndFissureSealers(count);
                case "Exodoncias" -> builder.extractions(count);
                case "Prótesis removible" -> builder.removableProsthesis(count);
                case "Prótesis fija" -> builder.prosthesisRemovable(count);
                case "Prostodoncia" -> builder.prosthodontics(count);
                case "Endodoncias" -> builder.rootCanals(count);
                case "Raspado y alisado" -> builder.scrapedAndSmoothed(count);
                case "Cerrado y abierto" -> builder.closedAndOpen(count);
                case "Cuña distal" -> builder.distalWedges(count);
                case "Pulpotomía y corona" -> builder.pulpotomyAndCrowns(count);
                case "Pulpectomía y corona" -> builder.pulpectomyAndCrowns(count);
            }
        });

        return builder.build();
    }

    private Map<String, Long> mergeTreatmentCounts(List<Object[]> list1, List<Object[]> list2) {
        Map<String, Long> result = new HashMap<>();
        for (Object[] row : list1) {
            result.put((String) row[0], (Long) row[1]);
        }
        for (Object[] row : list2) {
            result.merge((String) row[0], (Long) row[1], Long::sum);
        }
        return result;
    }

    public TreatmentCountResponse getTreatmentCountResponseByStudent(String enrollment, Timestamp startDate, Timestamp endDate) {
        List<Object[]> toothScope;
        List<Object[]> generalScope;

        if (startDate != null && endDate != null) {
            toothScope = treatmentDetailRepository.countToothScopeTreatmentsBetweenDatesByStudent(
                    enrollment, ReviewStatus.FINISHED, startDate, endDate
            );
            generalScope = treatmentDetailRepository.countGeneralScopeTreatmentsBetweenDatesByStudent(
                    enrollment, ReviewStatus.FINISHED, startDate, endDate
            );
        } else {
            toothScope = treatmentDetailRepository.countToothScopeTreatmentsByStudent(enrollment, ReviewStatus.FINISHED);
            generalScope = treatmentDetailRepository.countGeneralScopeTreatmentsByStudent(enrollment, ReviewStatus.FINISHED);
        }
        Map<String, Long> treatmentCounts = mergeTreatmentCounts(toothScope, generalScope);
        return mapToTreatmentCountResponse(treatmentCounts);
    }
}