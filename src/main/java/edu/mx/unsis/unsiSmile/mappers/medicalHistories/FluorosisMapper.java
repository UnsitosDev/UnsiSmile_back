package edu.mx.unsis.unsiSmile.mappers.medicalHistories;

import edu.mx.unsis.unsiSmile.dtos.request.medicalrecords.fluorosis.FluorosisRequest;
import edu.mx.unsis.unsiSmile.dtos.request.medicalrecords.teeth.ConditionRequest;
import edu.mx.unsis.unsiSmile.dtos.request.medicalrecords.teeth.FaceRequest;
import edu.mx.unsis.unsiSmile.dtos.request.medicalrecords.teeth.ToothFaceConditionRequest;
import edu.mx.unsis.unsiSmile.dtos.request.medicalrecords.teeth.ToothRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalrecords.fluorosis.FluorosisResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.medicalrecords.fluorosis.FluorosisModel;
import edu.mx.unsis.unsiSmile.model.medicalrecords.fluorosis.FluorosisToothConditionAssignmentModel;
import edu.mx.unsis.unsiSmile.model.medicalrecords.fluorosis.FluorosisToothfaceConditionsAssignmentModel;
import edu.mx.unsis.unsiSmile.model.medicalrecords.teeth.ToothConditionModel;
import edu.mx.unsis.unsiSmile.model.medicalrecords.teeth.ToothFaceConditionModel;
import edu.mx.unsis.unsiSmile.model.medicalrecords.teeth.ToothFaceModel;
import edu.mx.unsis.unsiSmile.model.medicalrecords.teeth.ToothModel;
import edu.mx.unsis.unsiSmile.model.treatments.TreatmentDetailModel;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FluorosisMapper implements BaseMapper<FluorosisResponse, FluorosisRequest, FluorosisModel> {

    @Override
    public FluorosisModel toEntity(FluorosisRequest dto) {
        return FluorosisModel.builder()
                .build();
    }

    @Override
    public FluorosisResponse toDto(FluorosisModel entity) {
        return FluorosisResponse.builder()
                .idFluorosis(entity.getIdFluorosis())
                .creationDate(entity.getCreatedAt().toLocalDateTime().toLocalDate())
                .teethFluorosis(Collections.emptyList())
                .build();
    }

    @Override
    public List<FluorosisResponse> toDtos(List<FluorosisModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(FluorosisRequest request, FluorosisModel entity) {
    }

    public static FluorosisModel toFluorosisModel(FluorosisRequest dto) {
        FluorosisModel fluorosisModel = FluorosisModel.builder()
                .treatmentDetail(TreatmentDetailModel.builder()
                        .idTreatmentDetail(dto.getIdTreatmentDetail())
                        .build())
                .build();

        List<FluorosisToothConditionAssignmentModel> toothFluorosisAssignments = dto.getTheetFluorosis().stream()
                .flatMap(toothDTO -> toothDTO.getConditions().stream()
                        .map(conditionDTO -> mapToFluorosisToothConditionAssignmentModel(toothDTO, conditionDTO)))
                .collect(Collectors.toList());

        List<FluorosisToothfaceConditionsAssignmentModel> toothFaceConditionsAssignments = dto.getTheetFluorosis().stream()
                .flatMap(toothDTO -> toothDTO.getFaces().stream()
                        .flatMap(faceDTO -> faceDTO.getConditions().stream()
                                .map(conditionDTO -> mapToFluorosisToothfaceConditionsAssignmentModel(toothDTO, faceDTO, conditionDTO))))
                .collect(Collectors.toList());

        fluorosisModel.setToothFaceConditionsAssignments(toothFaceConditionsAssignments);
        fluorosisModel.setToothConditionAssignments(toothFluorosisAssignments);

        return fluorosisModel;
    }

    private static FluorosisToothConditionAssignmentModel mapToFluorosisToothConditionAssignmentModel(ToothRequest toothDTO, ConditionRequest conditionDTO) {
        return FluorosisToothConditionAssignmentModel.builder()
                .tooth(mapToToothModel(toothDTO))
                .toothCondition(mapToToothConditionModel(conditionDTO))
                .build();
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

    private static FluorosisToothfaceConditionsAssignmentModel mapToFluorosisToothfaceConditionsAssignmentModel(ToothRequest toothDTO, FaceRequest faceDTO, ToothFaceConditionRequest conditionDTO) {
        return FluorosisToothfaceConditionsAssignmentModel.builder()
                .tooth(mapToToothModel(toothDTO))
                .toothFace(mapToToothFaceModel(faceDTO))
                .toothFaceCondition(mapToToothFaceConditionModel(conditionDTO))
                .build();
    }

    private static ToothFaceModel mapToToothFaceModel(FaceRequest faceDTO) {
        return ToothFaceModel.builder()
                .idToothFace(faceDTO.getIdFace().toString())
                .build();
    }

    private static ToothFaceConditionModel mapToToothFaceConditionModel(ToothFaceConditionRequest conditionDTO) {
        return ToothFaceConditionModel.builder()
                .idToothFaceCondition(conditionDTO.getIdToothFaceCondition())
                .build();
    }
}