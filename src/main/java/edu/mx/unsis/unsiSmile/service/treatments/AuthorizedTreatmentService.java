package edu.mx.unsis.unsiSmile.service.treatments;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.request.treatments.TreatmentStatusRequest;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.model.professors.ProfessorClinicalAreaModel;
import edu.mx.unsis.unsiSmile.model.treatments.AuthorizedTreatmentModel;
import edu.mx.unsis.unsiSmile.model.treatments.TreatmentDetailModel;
import edu.mx.unsis.unsiSmile.repository.professors.IProfessorClinicalAreaRepository;
import edu.mx.unsis.unsiSmile.repository.treatments.IAuthorizedTreatmentRepository;
import edu.mx.unsis.unsiSmile.repository.treatments.ITreatmentDetailRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@AllArgsConstructor
@Slf4j
public class AuthorizedTreatmentService {

    private final IAuthorizedTreatmentRepository authorizedTreatmentRepository;
    private final ITreatmentDetailRepository treatmentDetailRepository;
    private final IProfessorClinicalAreaRepository professorClinicalAreaRepository;

    @Transactional
    public AuthorizedTreatmentModel createAuthorizedTreatment(TreatmentStatusRequest request) {
        try {
            TreatmentDetailModel treatmentDetail = treatmentDetailRepository.findById(request.getTreatmentDetailId())
                    .orElseThrow(() -> new AppException(
                            String.format(ResponseMessages.TREATMENT_DETAIL_NOT_FOUND, request.getTreatmentDetailId()),
                            HttpStatus.NOT_FOUND));

            ProfessorClinicalAreaModel professorClinicalAreaModel = professorClinicalAreaRepository.findById(
                    request.getProfessorClinicalAreaId())
                    .orElseThrow(() -> new AppException(ResponseMessages.PROFESSOR_CLINICAL_AREA_NOT_FOUND, HttpStatus.NOT_FOUND));

            AuthorizedTreatmentModel model = AuthorizedTreatmentModel.builder()
                    .treatmentDetail(treatmentDetail)
                    .status(request.getStatus())
                    .professorClinicalArea(professorClinicalAreaModel)
                    .build();

            return authorizedTreatmentRepository.save(model);
        } catch (Exception ex) {
            log.error(ResponseMessages.ERROR_CREATING_AUTHORIZED_TREATMENT, ex);
            throw new AppException(ResponseMessages.ERROR_CREATING_AUTHORIZED_TREATMENT, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public AuthorizedTreatmentModel updateAuthorizedTreatmentByTreatmentId(TreatmentStatusRequest request) {
        try {
            AuthorizedTreatmentModel authorizedTreatment = authorizedTreatmentRepository
                    .findTopByTreatmentDetail_IdTreatmentDetailOrderByIdAuthorizedTreatmentDesc(request.getTreatmentDetailId())
                    .orElseThrow(() -> new AppException(
                            String.format(ResponseMessages.AUTHORIZATION_REQUEST_NOT_FOUND, request.getTreatmentDetailId()),
                            HttpStatus.NOT_FOUND));

            ProfessorClinicalAreaModel professorClinicalAreaModel = professorClinicalAreaRepository.findById(
                            request.getProfessorClinicalAreaId())
                    .orElseThrow(() -> new AppException(ResponseMessages.PROFESSOR_CLINICAL_AREA_NOT_FOUND, HttpStatus.NOT_FOUND));
            professorClinicalAreaModel.getProfessor().getPerson().getFullName();
            authorizedTreatment.setProfessorClinicalArea(professorClinicalAreaModel);

            return authorizedTreatmentRepository.save(authorizedTreatment);
        } catch (AppException e) {
            log.warn(String.format(ResponseMessages.AUTHORIZED_TREATMENT_NOT_FOUND, request.getTreatmentDetailId()));
            throw e;
        } catch (Exception ex) {
            log.warn(String.format(ResponseMessages.ERROR_UPDATING_AUTHORIZED_TREATMENT, request.getTreatmentDetailId()));
            throw new AppException(String.format(ResponseMessages.ERROR_UPDATING_AUTHORIZED_TREATMENT, request.getTreatmentDetailId()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public AuthorizedTreatmentModel getAuthorizedTreatmentById(Long id) {
        try {
            return authorizedTreatmentRepository.findById(id)
                    .orElseThrow(() -> new AppException(
                            String.format(ResponseMessages.AUTHORIZED_TREATMENT_NOT_FOUND, id),
                            HttpStatus.NOT_FOUND));
        } catch (AppException e) {
            log.warn(String.format(ResponseMessages.AUTHORIZED_TREATMENT_NOT_FOUND, id));
            throw e;
        } catch (Exception ex) {
            log.error(String.format(ResponseMessages.FAILED_FETCH_AUTHORIZED_TREATMENT, id),
                    ex);
            throw new AppException(String.format(ResponseMessages.FAILED_FETCH_AUTHORIZED_TREATMENT, id),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public AuthorizedTreatmentModel getAuthorizedTreatmentByTreatmentDetailId(Long id) {
        try {
            return authorizedTreatmentRepository
                    .findTopByTreatmentDetail_IdTreatmentDetailOrderByIdAuthorizedTreatmentDesc(id)
                    .orElseThrow(() -> new AppException(
                            String.format(ResponseMessages.AUTHORIZATION_REQUEST_NOT_FOUND, id),
                            HttpStatus.NOT_FOUND));
        } catch (AppException e) {
            log.warn(String.format(ResponseMessages.AUTHORIZED_TREATMENT_NOT_FOUND, id));
            throw e;
        } catch (Exception ex) {
            log.error(String.format(ResponseMessages.FAILED_FETCH_AUTHORIZED_TREATMENT, id),
                    ex);
            throw new AppException(String.format(ResponseMessages.FAILED_FETCH_AUTHORIZED_TREATMENT, id),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}