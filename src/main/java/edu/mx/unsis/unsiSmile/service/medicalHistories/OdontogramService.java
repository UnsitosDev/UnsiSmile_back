package edu.mx.unsis.unsiSmile.service.medicalHistories;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import edu.mx.unsis.unsiSmile.model.medicalHistories.*;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IToothConditionAssignmentRepository;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IToothConditionRepository;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IToothFaceConditionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.OdontogramRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.OdontogramResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.OdontogramMapper;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IOdontogramRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OdontogramService {

    private final IOdontogramRepository odontogramRepository;
    private final OdontogramMapper odontogramMapper;
    private final IToothFaceConditionRepository toothFaceConditionRepository;
    private final IToothConditionAssignmentRepository toothConditionAssignmentRepository;

    @Transactional
    public OdontogramModel saveOdontogram(OdontogramRequest odontogramReq) {
        // Crear el odontograma
        OdontogramModel odontogram = new OdontogramModel();
        odontogram.setCreationDate(odontogramReq.getCreationDate());

        // Mapear y agregar asignaciones de condiciones de dientes
        List<ToothConditionAssignmentModel> toothConditionAssignments = odontogramReq.getToothConditionAssignments()
                .stream()
                .map(dto -> {
                    ToothConditionAssignmentModel assignment = new ToothConditionAssignmentModel();
                    assignment.setTooth(
                            ToothModel.builder()
                                    .idTooth(dto.getToothId())
                                    .build()
                    );
                    assignment.setToothCondition(ToothConditionModel.builder()
                            .idToothCondition(dto.getToothConditionId())
                            .build()
                    );
                    assignment.setOdontogram(odontogram);
                    assignment.setCreationDate(dto.getCreationDate());
                    return assignment;
                })
                .collect(Collectors.toList());

        odontogram.setToothConditionAssignments(toothConditionAssignments);

        // Mapear y agregar asignaciones de condiciones de caras de dientes
        List<ToothFaceConditionModel> toothFaceConditions = odontogramReq.getToothFaceConditions()
                .stream()
                .map(dto -> {
                    ToothFaceConditionModel faceCondition = new ToothFaceConditionModel();
                    faceCondition.setToothFace(
                            ToothFaceModel.builder()
                                    .idToothFace(dto.getToothFaceId())  // Corregido: Se asigna el id de la cara del diente
                                    .build()
                    );
                    faceCondition.setTooth(
                            ToothModel.builder()
                                    .idTooth(dto.getToothId())
                                    .build()
                    );
                    faceCondition.setToothCondition(
                            ToothConditionModel.builder()
                                    .idToothCondition(dto.getToothConditionId())
                                    .build()
                    );
                    faceCondition.setOdontogram(odontogram);
                    faceCondition.setCreationDate(dto.getCreationDate());
                    return faceCondition;
                })
                .collect(Collectors.toList());

        odontogram.setToothFaceConditions(toothFaceConditions);

        // Guardar el odontograma junto con sus asignaciones
        odontogramRepository.save(odontogram);

        return odontogram;
    }

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
    public OdontogramModel saveOdontogram(OdontogramModel odontogram) {
        if (odontogram == null) {
            throw new IllegalArgumentException("Odontogram cannot be null");
        }

        odontogram = odontogramRepository.save(odontogram);

        for (ToothConditionAssignmentModel assignment : odontogram.getToothConditionAssignments()) {
            assignment.setOdontogram(odontogram);
            toothConditionAssignmentRepository.save(assignment);
        }

        for (ToothFaceConditionModel condition : odontogram.getToothFaceConditions()) {
            condition.setOdontogram(odontogram);
            toothFaceConditionRepository.save(condition);
        }

        return odontogram;
    }
}
