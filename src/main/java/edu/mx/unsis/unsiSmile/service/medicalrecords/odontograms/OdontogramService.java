package edu.mx.unsis.unsiSmile.service.medicalrecords.odontograms;

import edu.mx.unsis.unsiSmile.dtos.request.medicalrecords.odontograms.OdontogramRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalrecords.odontograms.OdontogramResponse;
import edu.mx.unsis.unsiSmile.dtos.response.medicalrecords.odontograms.OdontogramSimpleResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalrecords.odontograms.OdontogramMapper;
import edu.mx.unsis.unsiSmile.mappers.medicalrecords.odontograms.OdontogramSimpleMapper;
import edu.mx.unsis.unsiSmile.model.medicalrecords.odontogram.OdontogramModel;
import edu.mx.unsis.unsiSmile.model.medicalrecords.odontogram.ToothConditionAssignmentModel;
import edu.mx.unsis.unsiSmile.model.medicalrecords.odontogram.ToothfaceConditionsAssignmentModel;
import edu.mx.unsis.unsiSmile.repository.medicalrecords.odontograms.IOdontogramRepository;
import edu.mx.unsis.unsiSmile.repository.medicalrecords.odontograms.IToothConditionAssignmentRepository;
import edu.mx.unsis.unsiSmile.repository.medicalrecords.odontograms.IToothFaceConditionAssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OdontogramService {

    private final IOdontogramRepository odontogramRepository;
    private final IToothFaceConditionAssignmentRepository toothFaceConditionAssignmentRepository;
    private final IToothConditionAssignmentRepository toothConditionAssignmentRepository;
    private final OdontogramMapper odontogramMapper;
    private final OdontogramSimpleMapper odontogramSimpleMapper;

    @Transactional(readOnly = true)
    public OdontogramResponse getOdontogramById(@NonNull Long id) {
        try {
            OdontogramModel odontogramModel = odontogramRepository.findById(id)
                    .orElseThrow(() -> new AppException("Odontogram not found with ID: " + id, HttpStatus.NOT_FOUND));

            // Obtener todas las asignaciones de condiciones de dientes
            List<ToothConditionAssignmentModel> toothConditionAssignments = odontogramRepository
                    .findToothConditionAssignmentsByOdontogramId(id);

            // Obtener todas las condiciones de caras de dientes
            List<ToothfaceConditionsAssignmentModel> toothFaceConditions = odontogramRepository
                    .findToothFaceConditionsAssignmentByOdontogramId(id);

            OdontogramResponse odontogramResponse = odontogramMapper.mapConditionsAssignments(toothConditionAssignments,
                    toothFaceConditions);
            odontogramResponse.setIdOdontogram(id);
            odontogramResponse.setObservations(odontogramModel.getObservations());

            return odontogramResponse;
            
        } catch (Exception ex) {
            throw new AppException("Failed to fetch odontogram", HttpStatus.INTERNAL_SERVER_ERROR, ex);
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

        } catch (DataIntegrityViolationException e) {
            throw new AppException("Duplicate entry", HttpStatus.CONFLICT, e);
        } catch (Exception e) {
            throw new AppException("Failed to save odontogram", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }

    }


    public List<OdontogramSimpleResponse> getOdontogramsByPatientId(String patientId) {
        try {
            List<OdontogramModel> odontograms = odontogramRepository
                    .findAllOdontogramsByPatientIdOrderByCreatedAtDesc(patientId);
            return odontograms.stream()
                    .map(odontogramSimpleMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException("Failed to fetch odontograms by patient ID", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    public OdontogramResponse getlasOdontogramByPatientId(String patientId){
        try {
            OdontogramModel odontogramModel = odontogramRepository
                    .findLastOdontogramByPatientId(patientId)
                    .orElseThrow(() -> new AppException("No odontogram found for patient ID: " + patientId,
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

        } catch (Exception ex) {
            throw new AppException("Failed to fetch last odontogram by patient ID", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}