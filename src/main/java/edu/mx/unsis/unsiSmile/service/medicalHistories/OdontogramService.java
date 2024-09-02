package edu.mx.unsis.unsiSmile.service.medicalHistories;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.OdontogramRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.*;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.OdontogramMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.OdontogramModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.ToothConditionAssignmentModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.ToothFaceConditionModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IOdontogramRepository;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IToothConditionAssignmentRepository;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IToothFaceConditionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OdontogramService {

    private final IOdontogramRepository odontogramRepository;
    private final IToothFaceConditionRepository toothFaceConditionRepository;
    private final IToothConditionAssignmentRepository toothConditionAssignmentRepository;
    private final OdontogramMapper odontogramMapper;


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
        OdontogramModel odontogram = OdontogramMapper.toOdontogramModel(odontogramDTO);

        odontogram = odontogramRepository.save(odontogram);

        for (ToothConditionAssignmentModel assignment : odontogram.getToothConditionAssignments()) {
            assignment.setOdontogram(odontogram);
            toothConditionAssignmentRepository.save(assignment);
        }

        for (ToothFaceConditionModel condition : odontogram.getToothFaceConditions()) {
            condition.setOdontogram(odontogram);
            toothFaceConditionRepository.save(condition);
        }

    }

    public OdontogramResponse getOdontogramDetails(Long odontogramId) {
        // Obtener todas las asignaciones de condiciones de dientes
        List<ToothConditionAssignmentModel> toothConditionAssignments =
                odontogramRepository.findToothConditionAssignmentsByOdontogramId(odontogramId);

        // Obtener todas las condiciones de caras de dientes
        List<ToothFaceConditionModel> toothFaceConditions =
                odontogramRepository.findToothFaceConditionsByOdontogramId(odontogramId);

        // Mapa para agrupar los datos por diente
        Map<String, ToothResponse> toothMap = new HashMap<>();

        // Procesar ToothConditionAssignments
        for (ToothConditionAssignmentModel tca : toothConditionAssignments) {
            // Mapeo de ConditionDTO (para tooth conditions)
            ConditionResponse conditionDTO = new ConditionResponse(
                    tca.getToothCondition().getIdToothCondition(),
                    tca.getToothCondition().getDescription(),
                    "Tooth condition description" // Ajustar según el modelo
            );

            // Verificar si el diente ya existe en el mapa
            ToothResponse toothResponse = toothMap.computeIfAbsent(
                    tca.getTooth().getIdTooth(),
                    id -> new ToothResponse(id, new ArrayList<>(), true, new ArrayList<>())
            );

            // Agregar la condición al diente
            toothResponse.getConditions().add(conditionDTO);
        }

        // Procesar ToothFaceConditions
        for (ToothFaceConditionModel tfc : toothFaceConditions) {
            // Mapeo de ConditionDTO (para face conditions)
            ConditionResponse conditionDTO = new ConditionResponse(
                    tfc.getToothCondition().getIdToothCondition(),
                    tfc.getToothCondition().getDescription(),
                    "Tooth face condition description" // Ajustar según el modelo
            );

            // Verificar si el diente ya existe en el mapa
            ToothResponse toothResponse = toothMap.computeIfAbsent(
                    tfc.getTooth().getIdTooth(),
                    id -> new ToothResponse(id, new ArrayList<>(), true, new ArrayList<>())
            );

            // Buscar o crear la FaceDTO
            FaceResponse faceResponse = toothResponse.getFaces().stream()
                    .filter(f -> f.getIdFace().equals(tfc.getToothFace().getIdToothFace()))
                    .findFirst()
                    .orElseGet(() -> {
                        FaceResponse newFace = new FaceResponse(tfc.getToothFace().getIdToothFace(), new ArrayList<>());
                        toothResponse.getFaces().add(newFace);
                        return newFace;
                    });

            // Agregar la condición a la cara del diente
            faceResponse.getConditions().add(conditionDTO);
        }

        // Crear la lista de ToothDTO a partir del mapa
        List<ToothResponse> toothResponseList = new ArrayList<>(toothMap.values());

        // Crear y devolver el OdontogramDTO
        return OdontogramResponse.builder()
                        .idOdontogram(odontogramId)
                                .tooths(toothResponseList)
                .build();
    }
}
