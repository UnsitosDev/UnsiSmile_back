package edu.mx.unsis.unsiSmile.service;

import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.response.AdminDashboardResponse;
import edu.mx.unsis.unsiSmile.dtos.response.ProfessorDashboardResponse;
import edu.mx.unsis.unsiSmile.dtos.response.StudentDashboardResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.model.professors.ProfessorGroupModel;
import edu.mx.unsis.unsiSmile.repository.patients.IPatientRepository;
import edu.mx.unsis.unsiSmile.repository.professors.IProfessorGroupRepository;
import edu.mx.unsis.unsiSmile.repository.professors.IProfessorRepository;
import edu.mx.unsis.unsiSmile.repository.students.IStudentGroupRepository;
import edu.mx.unsis.unsiSmile.repository.students.IStudentPatientRepository;
import edu.mx.unsis.unsiSmile.repository.students.IStudentRepository;
import lombok.RequiredArgsConstructor;
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

    @Transactional(readOnly = true)
    public StudentDashboardResponse getStudentDashboardMetrics(String enrollment) {
        try {
            Timestamp lastMonthTimestamp = Timestamp.valueOf(LocalDateTime.now().minusMonths(1));

            return StudentDashboardResponse.builder()
                    .totalPatients(studentPatientRepository.countPatientsForStudent(enrollment, Constants.ACTIVE))
                    .patientsWithDisability(studentPatientRepository.countPatientsWithDisabilityByStudent(enrollment, Constants.ACTIVE))
                    .patientsRegisteredLastMonth(studentPatientRepository.countPatientsRegisteredSinceByStudent(enrollment, lastMonthTimestamp, Constants.ACTIVE))
                    .patientsByNationality(convertToMap(studentPatientRepository.countPatientsByNationalityByStudent(enrollment, Constants.ACTIVE)))
                    .patientsUnder18(studentPatientRepository.countPatientsUnder18ByStudent(enrollment, Constants.ACTIVE))
                    .patientsBetween18And60(studentPatientRepository.countPatientsBetween18And60ByStudent(enrollment, Constants.ACTIVE))
                    .patientsOver60(studentPatientRepository.countPatientsOver60ByStudent(enrollment, Constants.ACTIVE))
                    .build();
        } catch (Exception e) {
            throw new AppException(ResponseMessages.ERROR_STUDENT_DASHBOARD, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Transactional(readOnly = true)
    public ProfessorDashboardResponse getProfessorDashboardMetrics(String employeeNumber) {
        try {
            List<ProfessorGroupModel> professorGroups = professorGroupRepository
                    .findByProfessorAndGroupStatus(employeeNumber, Constants.ACTIVE);

            Set<Long> idsGroups = professorGroups.stream()
                    .map(professorGroup -> professorGroup.getGroup().getIdGroup())
                    .collect(Collectors.toSet());

            Long studentCount = studentGroupRepository.countStudentsByGroupIds(idsGroups);

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

            return AdminDashboardResponse.builder()
                    .totalPatients(patientRepository.countTotalPatients(Constants.ACTIVE))
                    .patientsWithDisability(patientRepository.countPatientsWithDisability(Constants.ACTIVE))
                    .patientsRegisteredLastMonth(patientRepository.countPatientsRegisteredSince(lastMonthTimestamp, Constants.ACTIVE))
                    .patientsByNationality(convertToMap(patientRepository.countPatientsByNationality(Constants.ACTIVE)))
                    .totalStudents(studentRepository.countTotalStudents(Constants.ACTIVE))
                    .studentsRegisteredLastMonth(studentRepository.countStudentsRegisteredSince(lastMonthTimestamp, Constants.ACTIVE))
                    .totalProfessors(professorRepository.countTotalProfessors(Constants.ACTIVE))
                    .build();
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
}