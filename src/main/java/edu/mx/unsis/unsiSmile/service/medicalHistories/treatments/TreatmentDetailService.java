package edu.mx.unsis.unsiSmile.service.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.treatments.TreatmentDetailRequest;
import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.treatments.TreatmentDetailToothRequest;
import edu.mx.unsis.unsiSmile.dtos.response.UserResponse;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.treatments.TreatmentDetailResponse;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.treatments.TreatmentResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.treatments.TreatmentDetailMapper;
import edu.mx.unsis.unsiSmile.model.PatientClinicalHistoryModel;
import edu.mx.unsis.unsiSmile.model.PersonModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.ReviewStatus;
import edu.mx.unsis.unsiSmile.model.medicalHistories.treatments.TreatmentDetailModel;
import edu.mx.unsis.unsiSmile.model.patients.PatientModel;
import edu.mx.unsis.unsiSmile.model.professors.ProfessorModel;
import edu.mx.unsis.unsiSmile.model.students.StudentGroupModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.treatments.ITreatmentDetailRepository;
import edu.mx.unsis.unsiSmile.service.UserService;
import edu.mx.unsis.unsiSmile.service.medicalHistories.PatientClinicalHistoryService;
import edu.mx.unsis.unsiSmile.service.patients.PatientService;
import edu.mx.unsis.unsiSmile.service.professors.ProfessorService;
import edu.mx.unsis.unsiSmile.service.students.StudentGroupService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TreatmentDetailService {

    private final ITreatmentDetailRepository treatmentDetailRepository;
    private final TreatmentDetailMapper treatmentDetailMapper;
    private final PatientClinicalHistoryService patientClinicalHistoryService;
    private final TreatmentService treatmentService;
    private final ProfessorService professorService;
    private final StudentGroupService studentGroupService;
    private final PatientService patientService;
    private final UserService userService;
    private final TreatmentDetailToothService treatmentDetailToothService;

    @Transactional
    public TreatmentDetailResponse createTreatmentDetail(@NonNull TreatmentDetailRequest request) {
        try {
            validateRequestDependencies(request);

            String patientId = request.getPatientId();

            boolean existsInReview = treatmentDetailRepository
                    .existsByPatientClinicalHistory_Patient_idPatientAndStatus(
                            patientId, ReviewStatus.IN_PROGRESS.toString()
                    );

            if (existsInReview) {
                throw new AppException(ResponseMessages.TREATMENT_DETAIL_ALREADY_IN_PROGRESS, HttpStatus.CONFLICT);
            }

            TreatmentResponse treatmentResponse = treatmentService.getTreatmentById(request.getTreatmentId());
            String scope = treatmentResponse.getTreatmentScope().getName();

            if (scope.equals(Constants.TOOTH) && request.getTreatmentDetailToothRequest() == null) {
                throw new AppException(ResponseMessages.TREATMENT_DETAIL_TOOTH_REQUEST_CANNOT_BE_NULL, HttpStatus.BAD_REQUEST);
            }

            TreatmentDetailModel savedModel = saveTreatmentDetail(request, treatmentResponse);

            if (scope.equals(Constants.TOOTH)) {
                TreatmentDetailToothRequest toothRequest = request.getTreatmentDetailToothRequest();
                toothRequest.setIdTreatmentDetail(savedModel.getIdTreatmentDetail());
                treatmentDetailToothService.createTreatmentDetailTeeth(toothRequest);
            }

            return this.toDto(savedModel);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_CREATE_TREATMENT_DETAIL, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    private void validateRequestDependencies(TreatmentDetailRequest request) {
            patientService.getPatientById(request.getPatientId());
            treatmentService.getTreatmentById(request.getTreatmentId());

            if (request.getProfessorId() != null) {
                professorService.getProfessor(request.getProfessorId());
            }
    }

    private TreatmentDetailModel saveTreatmentDetail(TreatmentDetailRequest request, TreatmentResponse treatmentResponse) {
        PatientClinicalHistoryModel clinicalHistory = patientClinicalHistoryService.save(
                request.getPatientId(),
                treatmentResponse.getClinicalHistoryCatalogId()
        );

        UserResponse currentUser = userService.getCurrentUser();
        StudentGroupModel studentGroup = studentGroupService.getStudentGroup(currentUser.getUsername());

        TreatmentDetailModel model = treatmentDetailMapper.toEntity(request);
        model.setPatientClinicalHistory(clinicalHistory);
        model.setStudentGroup(studentGroup);

        return treatmentDetailRepository.save(model);
    }

    private TreatmentDetailResponse toDto(TreatmentDetailModel treatmentDetailModel) {
        TreatmentDetailResponse treatmentDetail = treatmentDetailMapper.toDto(treatmentDetailModel);

        String professorName = Optional.ofNullable(treatmentDetailModel.getProfessor())
                .map(ProfessorModel::getPerson)
                .map(PersonModel::getFullName)
                .orElse(null);
        treatmentDetail.setProfessorName(professorName);

        String patientName = Optional.ofNullable(treatmentDetailModel.getPatientClinicalHistory())
                .map(PatientClinicalHistoryModel::getPatient)
                .map(PatientModel::getPerson)
                .map(PersonModel::getFullName)
                .orElse(null);
        treatmentDetail.setPatientName(patientName);

        if (Constants.TOOTH.equals(treatmentDetailModel.getTreatment().getTreatmentScope().getName())) {
            treatmentDetail.setTeeth(
                    treatmentDetailToothService.getTreatmentDetailTeethByTreatmentDetail(
                            treatmentDetailModel.getIdTreatmentDetail()
                    ));
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
            return toDto(model);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_FETCH_TREATMENT_DETAIL, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public Page<TreatmentDetailResponse> getAllTreatmentDetailsByPatient(Pageable pageable,@NonNull String patientId) {
        try {
            patientService.getPatientById(patientId);
            Page<TreatmentDetailModel> page = treatmentDetailRepository
                    .findByPatientClinicalHistory_Patient_IdPatient(patientId, pageable);            return page.map(this::toDto);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_FETCH_TREATMENT_DETAILS, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public TreatmentDetailResponse updateTreatmentDetail(@NonNull Long id, @NonNull TreatmentDetailRequest request) {
        try {
            TreatmentDetailModel existing = treatmentDetailRepository.findById(id)
                    .orElseThrow(() -> new AppException(
                            String.format(ResponseMessages.TREATMENT_DETAIL_NOT_FOUND, id),
                            HttpStatus.NOT_FOUND));

            validateRequestDependencies(request);

            TreatmentDetailModel updated = treatmentDetailMapper.toEntity(request);

            updated.setIdTreatmentDetail(existing.getIdTreatmentDetail());
            updated.setPatientClinicalHistory(existing.getPatientClinicalHistory());
            updated.setStudentGroup(existing.getStudentGroup());

            TreatmentDetailModel saved = treatmentDetailRepository.save(updated);
            return toDto(saved);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_UPDATE_TREATMENT_DETAIL, HttpStatus.INTERNAL_SERVER_ERROR);
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
}