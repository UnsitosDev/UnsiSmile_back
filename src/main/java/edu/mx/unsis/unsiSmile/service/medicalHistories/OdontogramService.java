package edu.mx.unsis.unsiSmile.service.medicalHistories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import edu.mx.unsis.unsiSmile.dtos.request.AnswerRequest;
import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.OdontogramRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.OdontogramResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.OdontogramMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.odontogram.OdontogramModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.odontogram.ToothConditionAssignmentModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.odontogram.ToothfaceConditionsAssignmentModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IOdontogramRepository;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IToothConditionAssignmentRepository;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IToothFaceConditionAssignmentRepository;
import edu.mx.unsis.unsiSmile.service.AnswerService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OdontogramService {

    private final IOdontogramRepository odontogramRepository;
    private final IToothFaceConditionAssignmentRepository toothFaceConditionAssignmentRepository;
    private final IToothConditionAssignmentRepository toothConditionAssignmentRepository;
    private final OdontogramMapper odontogramMapper;
    private final AnswerService answerService;

    @Transactional(readOnly = true)
    public OdontogramResponse getOdontogramById(@NonNull Long id) {
        try {
            OdontogramModel odontogramModel = odontogramRepository.findById(id)
                    .orElseThrow(() -> new AppException("Odontogram not found with ID: " + id, HttpStatus.NOT_FOUND));

            return odontogramMapper.toDto(odontogramModel);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch odontogram", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<OdontogramResponse> getAllOdontograms() {
        try {
            List<OdontogramModel> allOdontograms = odontogramRepository.findAll();
            return allOdontograms.stream()
                    .map(odontogramMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException("Failed to fetch odontograms", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public OdontogramResponse updateOdontogram(@NonNull Long id, @NonNull OdontogramRequest updatedOdontogramRequest) {
        try {
            Assert.notNull(updatedOdontogramRequest, "Updated OdontogramRequest cannot be null");

            OdontogramModel existingOdontogram = odontogramRepository.findById(id)
                    .orElseThrow(() -> new AppException("Odontogram not found with ID: " + id, HttpStatus.NOT_FOUND));

            OdontogramModel updatedOdontogram = odontogramMapper.toEntity(updatedOdontogramRequest);
            updatedOdontogram.setIdOdontogram(existingOdontogram.getIdOdontogram()); // Ensure ID consistency

            OdontogramModel savedOdontogram = odontogramRepository.save(updatedOdontogram);

            return odontogramMapper.toDto(savedOdontogram);
        } catch (Exception ex) {
            throw new AppException("Failed to update odontogram", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteOdontogram(@NonNull Long id) {
        try {
            if (!odontogramRepository.existsById(id)) {
                throw new AppException("Odontogram not found with ID: " + id, HttpStatus.NOT_FOUND);
            }
            odontogramRepository.deleteById(id);
        } catch (Exception ex) {
            throw new AppException("Failed to delete odontogram", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void saveOdontogram(OdontogramRequest odontogramDTO) {
        try {
            OdontogramModel odontogram = OdontogramMapper.toOdontogramModel(odontogramDTO);

            odontogram = odontogramRepository.save(odontogram);

            for (ToothConditionAssignmentModel assignment : odontogram.getToothConditionAssignments()) {
                assignment.setOdontogram(odontogram);
                toothConditionAssignmentRepository.save(assignment);
            }

            for (ToothfaceConditionsAssignmentModel condition : odontogram.getToothFaceConditionsAssignments()) {
                condition.setOdontogram(odontogram);
                toothFaceConditionAssignmentRepository.save(condition);
            }

            answerService.saveBatch(
                    List.of(
                            AnswerRequest.builder()
                                    .idQuestion(odontogramDTO.getIdQuestion())
                                    .idPatientClinicalHistory(
                                            odontogramDTO.getIdPatientClinicalHistory())
                                    .answerText("")
                                    .build()));

        } catch (DataIntegrityViolationException e) {
            throw new AppException("Duplicate entry", HttpStatus.CONFLICT, e);
        } catch (Exception e) {
            throw new AppException(e.getCause().toString(), HttpStatus.BAD_REQUEST, e);
        }

    }

    public OdontogramResponse getOdontogramDetails(String patientId) {

        OdontogramModel odontogramModel = odontogramRepository.findLatestOdontogramByPatientId(patientId)
                .orElseThrow(() -> new AppException("Odontogram not found for patient ID: " + patientId,
                        HttpStatus.NOT_FOUND));

        Long odontogramId = odontogramModel.getIdOdontogram();

        // Obtener todas las asignaciones de condiciones de dientes
        List<ToothConditionAssignmentModel> toothConditionAssignments = odontogramRepository
                .findToothConditionAssignmentsByOdontogramId(odontogramId);

        // Obtener todas las condiciones de caras de dientes
        List<ToothfaceConditionsAssignmentModel> toothFaceConditions = odontogramRepository
                .findToothFaceConditionsAssignmentByOdontogramId(odontogramId);

        OdontogramResponse odontogramResponse = odontogramMapper.mapConditionsAssignments(toothConditionAssignments,
                toothFaceConditions);
        odontogramResponse.setIdOdontogram(odontogramId);
        odontogramResponse.setObservations(odontogramModel.getObservations());

        return odontogramResponse;

    }

    // obtener el odontograma por id de form section y el id del paciente
    public OdontogramResponse getOdontogramByFormSectionId(Long formSectionId, String patientId) {
        OdontogramModel odontogramModel = odontogramRepository.findByFormSectionIdAndPatientId(formSectionId, patientId)
                .orElseThrow(() -> new AppException("Odontogram not found with form section ID: " + formSectionId,
                        HttpStatus.NOT_FOUND));

        Long odontogramId = odontogramModel.getIdOdontogram();

        // Obtener todas las asignaciones de condiciones de dientes
        List<ToothConditionAssignmentModel> toothConditionAssignments = odontogramRepository
                .findToothConditionAssignmentsByOdontogramId(odontogramId);

        // Obtener todas las condiciones de caras de dientes
        List<ToothfaceConditionsAssignmentModel> toothFaceConditions = odontogramRepository
                .findToothFaceConditionsAssignmentByOdontogramId(odontogramId);

        odontogramMapper.mapConditionsAssignments(toothConditionAssignments, toothFaceConditions);

        OdontogramResponse odontogramResponse = odontogramMapper.mapConditionsAssignments(toothConditionAssignments,
                toothFaceConditions);
        odontogramResponse.setIdOdontogram(odontogramId);
        odontogramResponse.setObservations(odontogramModel.getObservations());

        return odontogramResponse;
    }

}
