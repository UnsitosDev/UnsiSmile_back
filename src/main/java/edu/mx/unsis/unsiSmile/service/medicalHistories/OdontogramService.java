package edu.mx.unsis.unsiSmile.service.medicalHistories;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.OdontogramRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.ConditionResponse;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.FaceResponse;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.OdontogramResponse;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.ToothResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.OdontogramMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.odontogram.OdontogramModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.odontogram.ToothConditionAssignmentModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.odontogram.ToothfaceConditionsAssignmentModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IOdontogramRepository;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IToothConditionAssignmentRepository;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IToothFaceConditionAssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OdontogramService {

    private final IOdontogramRepository odontogramRepository;
    private final IToothFaceConditionAssignmentRepository toothFaceConditionAssignmentRepository;
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
            throw new AppException(e.getCause().toString(), HttpStatus.BAD_REQUEST, e);
        }

    }

    public OdontogramResponse getOdontogramDetails(String patientId) {

        Long odontogramId = getLatestOdontogramIdByPatient(patientId).orElseThrow(
                () -> new AppException("Odontogram not found with ID: " + patientId, HttpStatus.NOT_FOUND)
        );

        // Obtener todas las asignaciones de condiciones de dientes
        List<ToothConditionAssignmentModel> toothConditionAssignments =
                odontogramRepository.findToothConditionAssignmentsByOdontogramId(odontogramId);

        // Obtener todas las condiciones de caras de dientes
        List<ToothfaceConditionsAssignmentModel> toothFaceConditions =
                odontogramRepository.findToothFaceConditionsAssignmentByOdontogramId(odontogramId);

        // Mapa para agrupar los datos por diente
        Map<String, ToothResponse> adultTeethMap = new HashMap<>();
        Map<String, ToothResponse> childTeethMap = new HashMap<>();

        // Procesar ToothConditionAssignments
        for (ToothConditionAssignmentModel tca : toothConditionAssignments) {

            // Mapeo de ConditionDTO (para tooth conditions)
            ConditionResponse conditionDTO = ConditionResponse.builder()
                    .idCondition(tca.getToothCondition().getIdToothCondition())
                    .condition(tca.getToothCondition().getDescription())
                    .description(tca.getToothCondition().getDescription())
                    .build();

            // Verificar si el diente ya existe en el mapa
            if (tca.getTooth().isAdult()) {
                ToothResponse adultTheetResponse = adultTeethMap.computeIfAbsent(
                        tca.getTooth().getIdTooth(),
                        id -> new ToothResponse(id, new ArrayList<>(), new ArrayList<>())
                );
                adultTheetResponse.getConditions().add(conditionDTO);
            } else {
                ToothResponse childTeethResponse = childTeethMap.computeIfAbsent(
                        tca.getTooth().getIdTooth(),
                        id -> new ToothResponse(id, new ArrayList<>(), new ArrayList<>())
                );
                childTeethResponse.getConditions().add(conditionDTO);
            }

        }

        // Procesar ToothFaceConditions
        for (ToothfaceConditionsAssignmentModel tfca : toothFaceConditions) {
            // Mapeo de ConditionDTO (para face conditions)
            ConditionResponse conditionDTO = new ConditionResponse(
                    tfca.getToothFaceCondition().getIdToothFaceCondition(),
                    tfca.getToothFaceCondition().getDescription(),
                    "Tooth face condition description" // Ajustar según el modelo
            );

            // Verificar si el diente ya existe en el mapa
            if (tfca.getTooth().isAdult()) {
                ToothResponse adultToothResponse = adultTeethMap.computeIfAbsent(
                        tfca.getTooth().getIdTooth(),
                        id -> new ToothResponse(id, new ArrayList<>(), new ArrayList<>())
                );

                // Buscar o crear la FaceDTO
                FaceResponse adultFace = adultToothResponse.getFaces().stream()
                        .filter(f -> f.getIdFace().equals(tfca.getToothFace().getIdToothFace()))
                        .findFirst()
                        .orElseGet(() -> {
                            FaceResponse newFace = new FaceResponse(tfca.getToothFace().getIdToothFace(), new ArrayList<>());
                            adultToothResponse.getFaces().add(newFace);
                            return newFace;
                        });

                // Agregar la condición a la cara del diente
                adultFace.getConditions().add(conditionDTO);
            } else {
                ToothResponse childToothResponse = childTeethMap.computeIfAbsent(
                        tfca.getTooth().getIdTooth(),
                        id -> new ToothResponse(id, new ArrayList<>(), new ArrayList<>())
                );

                // Buscar o crear la FaceDTO
                FaceResponse childFace = childToothResponse.getFaces().stream()
                        .filter(f -> f.getIdFace().equals(tfca.getToothFace().getIdToothFace()))
                        .findFirst()
                        .orElseGet(() -> {
                            FaceResponse newFace = new FaceResponse(tfca.getToothFace().getIdToothFace(), new ArrayList<>());
                            childToothResponse.getFaces().add(newFace);
                            return newFace;
                        });

                // Agregar la condición a la cara del diente
                childFace.getConditions().add(conditionDTO);
            }


        }

        // Crear la lista de ToothDTO a partir del mapa
        List<ToothResponse> adultToothResponseList = new ArrayList<>(adultTeethMap.values());
        List<ToothResponse> childToothResponseList = new ArrayList<>(childTeethMap.values());


        // Crear y devolver el OdontogramDTO
        return OdontogramResponse.builder()
                .idOdontogram(odontogramId)
                .adultArcade(adultToothResponseList)
                .childArcade(childToothResponseList)
                .build();
    }

    public Optional<Long> getLatestOdontogramIdByPatient(String patientId) {
        List<Long> results = odontogramRepository.findOdontogramIdsByPatient(patientId, PageRequest.of(0, 1));
        return results.isEmpty() ? Optional.empty() : Optional.of(results.getFirst());
    }
}
