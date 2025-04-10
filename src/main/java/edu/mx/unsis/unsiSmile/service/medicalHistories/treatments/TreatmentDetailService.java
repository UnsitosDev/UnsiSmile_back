package edu.mx.unsis.unsiSmile.service.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.treatments.TreatmentDetailRequest;
import edu.mx.unsis.unsiSmile.dtos.response.UserResponse;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.treatments.TreatmentDetailResponse;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.treatments.TreatmentResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.treatments.TreatmentDetailMapper;
import edu.mx.unsis.unsiSmile.model.PatientClinicalHistoryModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.treatments.TreatmentDetailModel;
import edu.mx.unsis.unsiSmile.model.students.StudentGroupModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.treatments.ITreatmentDetailRepository;
import edu.mx.unsis.unsiSmile.service.UserService;
import edu.mx.unsis.unsiSmile.service.medicalHistories.PatientClinicalHistoryService;
import edu.mx.unsis.unsiSmile.service.patients.PatientService;
import edu.mx.unsis.unsiSmile.service.professors.ProfessorService;
import edu.mx.unsis.unsiSmile.service.students.StudentGroupService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TreatmentDetailService {

    private final ITreatmentDetailRepository treatmentDetailRepository;
    private final TreatmentDetailMapper treatmentDetailMapper;
    private final PatientClinicalHistoryService patientClinicalHistoryService;
    private final TreatmentService treatmentService;
    private final TreatmentScopeService treatmentScopeService;
    private final ProfessorService professorService;
    private final StudentGroupService studentGroupService;
    private final PatientService patientService;
    private final UserService userService;

    @Transactional
    public TreatmentDetailResponse createTreatmentDetail(@NonNull TreatmentDetailRequest request) {
        try {
            validateRequestDependencies(request);

            TreatmentResponse treatmentResponse = treatmentService.getTreatmentById(request.getTreatmentId());

            PatientClinicalHistoryModel clinicalHistory = patientClinicalHistoryService.save(
                    request.getPatientId(),
                    treatmentResponse.getClinicalHistoryCatalogId()
            );

            UserResponse currentUser = userService.getCurrentUser();
            StudentGroupModel studentGroup = studentGroupService.getStudentGroup(currentUser.getUsername());

            TreatmentDetailModel model = treatmentDetailMapper.toEntity(request);
            model.setPatientClinicalHistory(clinicalHistory);
            model.setStudentGroup(studentGroup);

            TreatmentDetailModel savedModel = treatmentDetailRepository.save(model);
            return treatmentDetailMapper.toDto(savedModel);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException("Failed to create treatment detail", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    private void validateRequestDependencies(TreatmentDetailRequest request) {
            patientService.getPatientById(request.getPatientId());
            treatmentService.getTreatmentById(request.getTreatmentId());
            treatmentScopeService.getTreatmentScopeById(request.getTreatmentScopeId());

            if (request.getProfessorId() != null) {
                professorService.getProfessor(request.getProfessorId());
            }
    }
}