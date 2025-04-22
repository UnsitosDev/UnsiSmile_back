package edu.mx.unsis.unsiSmile.mappers.medicalHistories;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.*;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.DentalProphylaxisResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.FormSectionModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.DentalProphylaxisModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.odontogram.*;
import edu.mx.unsis.unsiSmile.model.medicalHistories.teeth.ToothConditionModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.teeth.ToothFaceConditionModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.teeth.ToothFaceModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.teeth.ToothModel;
import edu.mx.unsis.unsiSmile.model.patients.PatientModel;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DentalProphylaxisMapper implements BaseMapper<DentalProphylaxisResponse, DentalProphylaxisRequest, DentalProphylaxisModel> {

    @Override
    public DentalProphylaxisModel toEntity(DentalProphylaxisRequest dto) {
        return DentalProphylaxisModel.builder()
                .build();
    }

    @Override
    public DentalProphylaxisResponse toDto(DentalProphylaxisModel entity) {
        return DentalProphylaxisResponse.builder()
                .idDentalProphylaxis(entity.getIdDentalProphylaxis())
                .creationDate(entity.getCreatedAt().toLocalDateTime().toLocalDate())
                .teethProphylaxis(Collections.emptyList())
                .build();
    }

    @Override
    public List<DentalProphylaxisResponse> toDtos(List<DentalProphylaxisModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(DentalProphylaxisRequest request, DentalProphylaxisModel entity) {
    }

    public static DentalProphylaxisModel toDentalProphylaxisModel(DentalProphylaxisRequest dto) {
        DentalProphylaxisModel dentalProphylaxisModel = DentalProphylaxisModel.builder()
                .patient(PatientModel.builder().idPatient(dto.getIdPatient()).build())
                .formSection(FormSectionModel.builder().idFormSection(dto.getIdFormSection()).build())
                .build();

        List<ProphylaxisToothConditionAssignmentModel> toothProphylaxisAssignments = dto.getTheetProphylaxis().stream()
                .flatMap(toothDTO -> toothDTO.getConditions().stream()
                        .map(conditionDTO -> mapToProphylaxisToothConditionAssignmentModel(toothDTO, conditionDTO)))
                .collect(Collectors.toList());

        List<ProphylaxisToothfaceConditionsAssignmentModel> toothFaceConditionsAssignments = dto.getTheetProphylaxis().stream()
                .flatMap(toothDTO -> toothDTO.getFaces().stream()
                        .flatMap(faceDTO -> faceDTO.getConditions().stream()
                                .map(conditionDTO -> mapToProphylaxisToothfaceConditionsAssignmentModel(toothDTO, faceDTO, conditionDTO))))
                .collect(Collectors.toList());

        dentalProphylaxisModel.setToothFaceConditionsAssignments(toothFaceConditionsAssignments);
        dentalProphylaxisModel.setToothConditionAssignments(toothProphylaxisAssignments);

        return dentalProphylaxisModel;
    }

    private static ProphylaxisToothConditionAssignmentModel mapToProphylaxisToothConditionAssignmentModel(ToothRequest toothDTO, ConditionRequest conditionDTO) {
        return ProphylaxisToothConditionAssignmentModel.builder()
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

    private static ProphylaxisToothfaceConditionsAssignmentModel mapToProphylaxisToothfaceConditionsAssignmentModel(ToothRequest toothDTO, FaceRequest faceDTO, ToothFaceConditionRequest conditionDTO) {
        return ProphylaxisToothfaceConditionsAssignmentModel.builder()
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