package edu.mx.unsis.unsiSmile.mappers.medicalHistories;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.ConditionRequest;
import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.FaceRequest;
import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.OdontogramRequest;
import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.ToothFaceConditionRequest;
import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.ToothRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.OdontogramResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.FormSectionModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.odontogram.OdontogramModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.odontogram.ToothConditionAssignmentModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.teeth.ToothConditionModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.odontogram.ToothFaceConditionModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.teeth.ToothFaceModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.teeth.ToothModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.odontogram.ToothfaceConditionsAssignmentModel;
import edu.mx.unsis.unsiSmile.model.patients.PatientModel;

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
                .adultArcade(Collections.emptyList())
                .childArcade(Collections.emptyList())
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
        OdontogramModel odontogramModel = OdontogramModel.builder()
                .patient(
                        PatientModel.builder().
                                idPatient(dto.getIdPatient())
                                .build())
                .formSection(FormSectionModel.builder().
                        idFormSection(dto.getIdFormSection()).
                        build())
                .build();

        // Mapeo de ToothConditionAssignments
        List<ToothConditionAssignmentModel> toothConditionAssignments = dto.getTeeth().stream()
                .flatMap(toothDTO -> toothDTO.getConditions().stream()
                        .map(conditionDTO -> mapToToothConditionAssignmentModel(toothDTO, conditionDTO)))
                .collect(Collectors.toList());

        odontogramModel.setToothConditionAssignments(toothConditionAssignments);

        // Mapeo de ToothFaceConditions
        List<ToothfaceConditionsAssignmentModel> toothFaceConditionsAssignments = dto.getTeeth().stream()
                .flatMap(toothDTO -> toothDTO.getFaces().stream()
                        .flatMap(faceDTO -> faceDTO.getConditions().stream()
                                .map(conditionDTO -> mapToToothfaceConditionsAssignmentModel(toothDTO, faceDTO, conditionDTO))))
                .collect(Collectors.toList());

        odontogramModel.setToothFaceConditionsAssignments(toothFaceConditionsAssignments);

        return odontogramModel;
    }

    private static ToothConditionAssignmentModel mapToToothConditionAssignmentModel(ToothRequest toothDTO, ConditionRequest conditionDTO) {
        ToothConditionAssignmentModel assignmentModel = new ToothConditionAssignmentModel();

        assignmentModel.setTooth(mapToToothModel(toothDTO));
        assignmentModel.setToothCondition(mapToToothConditionModel(conditionDTO));

        return assignmentModel;
    }

    private static ToothfaceConditionsAssignmentModel mapToToothfaceConditionsAssignmentModel(ToothRequest toothDTO, FaceRequest faceDTO, ToothFaceConditionRequest conditionDTO) {
        ToothfaceConditionsAssignmentModel faceConditionModel = new ToothfaceConditionsAssignmentModel();

        faceConditionModel.setTooth(mapToToothModel(toothDTO));
        faceConditionModel.setToothFace(mapToToothFaceModel(faceDTO));
        faceConditionModel.setToothFaceCondition(mapToToothConditionModel(conditionDTO));

        return faceConditionModel;
    }

    private static ToothModel mapToToothModel(ToothRequest toothDTO) {
        return ToothModel.builder()
                .idTooth(toothDTO.getIdTooth().toString())
                .build();
    }

    private static ToothConditionModel mapToToothConditionModel(ConditionRequest conditionDTO) {
        return ToothConditionModel.builder()
                .idToothCondition(conditionDTO.getIdCondition())
                .build();
    }

    private static ToothFaceConditionModel mapToToothConditionModel(ToothFaceConditionRequest conditionDTO) {
        return ToothFaceConditionModel.builder()
                .idToothFaceCondition(conditionDTO.getIdToothFaceCondition())
                .build();
    }

    private static ToothFaceModel mapToToothFaceModel(FaceRequest faceDTO) {
        return ToothFaceModel.builder()
                .idToothFace(faceDTO.getIdFace().toString())
                .build();
    }
}
