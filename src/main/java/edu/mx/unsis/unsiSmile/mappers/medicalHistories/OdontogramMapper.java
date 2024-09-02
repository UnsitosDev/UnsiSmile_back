package edu.mx.unsis.unsiSmile.mappers.medicalHistories;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.ConditionRequest;
import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.FaceRequest;
import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.OdontogramRequest;
import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.ToothRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.OdontogramResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OdontogramMapper implements BaseMapper<OdontogramResponse, OdontogramRequest, OdontogramModel> {

    @Override
    public OdontogramModel toEntity(OdontogramRequest dto) {
        return OdontogramModel.builder()
                .build();
    }

    @Override
    public OdontogramResponse toDto(OdontogramModel entity) {
        return OdontogramResponse.builder()
                .idOdontogram(entity.getIdOdontogram())
                .date(entity.getCreationDate().toString())
                .build();
    }

    @Override
    public List<OdontogramResponse> toDtos(List<OdontogramModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(OdontogramRequest request, OdontogramModel entity) {

    }


    public static OdontogramModel toOdontogramModel(OdontogramRequest dto) {
        OdontogramModel odontogramModel = OdontogramModel.builder().creationDate(LocalDate.now()).build();

        // Mapeo de ToothConditionAssignments
        List<ToothConditionAssignmentModel> toothConditionAssignments = dto.getTooths().stream()
                .flatMap(toothDTO -> toothDTO.getConditions().stream()
                        .map(conditionDTO -> mapToToothConditionAssignmentModel(toothDTO, conditionDTO)))
                .collect(Collectors.toList());

        odontogramModel.setToothConditionAssignments(toothConditionAssignments);

        // Mapeo de ToothFaceConditions
        List<ToothFaceConditionModel> toothFaceConditions = dto.getTooths().stream()
                .flatMap(toothDTO -> toothDTO.getFaces().stream()
                        .flatMap(faceDTO -> faceDTO.getConditions().stream()
                                .map(conditionDTO -> mapToToothFaceConditionModel(toothDTO, faceDTO, conditionDTO))))
                .collect(Collectors.toList());

        odontogramModel.setToothFaceConditions(toothFaceConditions);

        return odontogramModel;
    }

    private static ToothConditionAssignmentModel mapToToothConditionAssignmentModel(ToothRequest toothDTO, ConditionRequest conditionDTO) {
        ToothConditionAssignmentModel assignmentModel = new ToothConditionAssignmentModel();

        assignmentModel.setTooth(mapToToothModel(toothDTO));
        assignmentModel.setToothCondition(mapToToothConditionModel(conditionDTO));
        assignmentModel.setCreationDate(java.time.LocalDate.now());

        return assignmentModel;
    }

    private static ToothFaceConditionModel mapToToothFaceConditionModel(ToothRequest toothDTO, FaceRequest faceDTO, ConditionRequest conditionDTO) {
        ToothFaceConditionModel faceConditionModel = new ToothFaceConditionModel();

        faceConditionModel.setTooth(mapToToothModel(toothDTO));
        faceConditionModel.setToothFace(mapToToothFaceModel(faceDTO));
        faceConditionModel.setToothCondition(mapToToothConditionModel(conditionDTO));
        faceConditionModel.setCreationDate(java.time.LocalDate.now());

        return faceConditionModel;
    }

    private static ToothModel mapToToothModel(ToothRequest toothDTO) {
        return ToothModel.builder()
                .idTooth(toothDTO.getIdTooth().toString())
                .isAdult(toothDTO.getIsAdult())
                .build();
    }

    private static ToothConditionModel mapToToothConditionModel(ConditionRequest conditionDTO) {
        return ToothConditionModel.builder()
                .idToothCondition(conditionDTO.getIdCondition())
                .build();
    }

    private static ToothFaceModel mapToToothFaceModel(FaceRequest faceDTO) {
        return ToothFaceModel.builder()
                .idToothFace(faceDTO.getIdFace().toString())
                .build();
    }
}
