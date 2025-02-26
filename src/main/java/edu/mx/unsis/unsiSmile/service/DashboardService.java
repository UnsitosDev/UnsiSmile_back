package edu.mx.unsis.unsiSmile.service;

import edu.mx.unsis.unsiSmile.authenticationProviders.model.ERole;
import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.dtos.response.DashboardResponse;
import edu.mx.unsis.unsiSmile.dtos.response.UserResponse;
import edu.mx.unsis.unsiSmile.model.professors.ProfessorGroupModel;
import edu.mx.unsis.unsiSmile.repository.patients.IPatientRepository;
import edu.mx.unsis.unsiSmile.repository.professors.IProfessorGroupRepository;
import edu.mx.unsis.unsiSmile.repository.professors.IProfessorRepository;
import edu.mx.unsis.unsiSmile.repository.students.IStudentGroupRepository;
import edu.mx.unsis.unsiSmile.repository.students.IStudentPatientRepository;
import edu.mx.unsis.unsiSmile.repository.students.IStudentRepository;
import lombok.RequiredArgsConstructor;
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

    @Transactional(readOnly = true)
    public DashboardResponse getDashboardMetrics() {
        Timestamp lastMonthTimestamp = Timestamp.valueOf(LocalDateTime.now().minusMonths(1));
        UserResponse user = userService.getCurrentUser();
        Map<String, Object> data = new HashMap<>();

        if (isStudent(user)) {
            loadStudentMetrics(data, user.getUsername(), lastMonthTimestamp);
        } else if (isProfessor(user)) {
            loadProfessorMetrics(data, user.getUsername());
        } else if (isAdmin(user)) {
            loadAdminMetrics(data, lastMonthTimestamp);
        }

        return DashboardResponse.builder().data(data).build();
    }

    private boolean isStudent(UserResponse user) {
        return ERole.ROLE_STUDENT.equals(user.getRole().getRole());
    }

    private boolean isProfessor(UserResponse user) {
        return ERole.ROLE_PROFESSOR.equals(user.getRole().getRole());
    }

    private boolean isAdmin(UserResponse user) {
        return ERole.ROLE_ADMIN.equals(user.getRole().getRole());
    }

    private void loadStudentMetrics(Map<String, Object> data, String enrollment, Timestamp lastMonthTimestamp) {
        data.put("patients", studentPatientRepository.countPatientsForStudent(enrollment, Constants.ACTIVE));
        data.put("patientsWithDisability", studentPatientRepository.countPatientsWithDisabilityByStudent(enrollment, Constants.ACTIVE));
        data.put("patientsRegisteredLastMonth", studentPatientRepository.countPatientsRegisteredSinceByStudent(enrollment, lastMonthTimestamp, Constants.ACTIVE));
        data.put("patientsByNationality", convertToMap(studentPatientRepository.countPatientsByNationalityByStudent(enrollment, Constants.ACTIVE)));
    }

    private void loadProfessorMetrics(Map<String, Object> data, String employeeNumber) {
        List<ProfessorGroupModel> professorGroups = professorGroupRepository
                .findByProfessorAndGroupStatus(employeeNumber, Constants.ACTIVE);

        Set<Long> idsGroups = professorGroups.stream()
                .map(professorGroup -> professorGroup.getGroup().getIdGroup())
                .collect(Collectors.toSet());

        Long studentCount = studentGroupRepository.countStudentsByGroupIds(idsGroups);
        data.put("groups", professorGroups.size());
        data.put("students", studentCount);
    }

    private void loadAdminMetrics(Map<String, Object> data, Timestamp lastMonthTimestamp) {
        data.put("patients", patientRepository.countTotalPatients(Constants.ACTIVE));
        data.put("patientsWithDisability", patientRepository.countPatientsWithDisability(Constants.ACTIVE));
        data.put("patientsRegisteredLastMonth", patientRepository.countPatientsRegisteredSince(lastMonthTimestamp, Constants.ACTIVE));
        data.put("patientsByNationality", convertToMap(patientRepository.countPatientsByNationality(Constants.ACTIVE)));
        data.put("students", studentRepository.countTotalStudents(Constants.ACTIVE));
        data.put("studentsRegisteredLastMonth", studentRepository.countStudentsRegisteredSince(lastMonthTimestamp, Constants.ACTIVE));
        data.put("professors", professorRepository.countTotalProfessors(Constants.ACTIVE));
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
