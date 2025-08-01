package edu.mx.unsis.unsiSmile.mappers.medicalHistories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import edu.mx.unsis.unsiSmile.dtos.request.medicalrecords.teeth.ConditionRequest;
import edu.mx.unsis.unsiSmile.dtos.request.medicalrecords.teeth.FaceRequest;
import edu.mx.unsis.unsiSmile.dtos.request.medicalrecords.odontograms.OdontogramRequest;
import edu.mx.unsis.unsiSmile.dtos.request.medicalrecords.teeth.ToothFaceConditionRequest;
import edu.mx.unsis.unsiSmile.dtos.request.medicalrecords.teeth.ToothRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalrecords.teeth.ConditionResponse;
import edu.mx.unsis.unsiSmile.dtos.response.medicalrecords.teeth.FaceResponse;
import edu.mx.unsis.unsiSmile.dtos.response.medicalrecords.odontograms.OdontogramResponse;
import edu.mx.unsis.unsiSmile.dtos.response.medicalrecords.teeth.ToothResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.medicalrecords.odontogram.OdontogramModel;
import edu.mx.unsis.unsiSmile.model.medicalrecords.odontogram.ToothConditionAssignmentModel;
import edu.mx.unsis.unsiSmile.model.medicalrecords.odontogram.ToothfaceConditionsAssignmentModel;
import edu.mx.unsis.unsiSmile.model.medicalrecords.teeth.ToothConditionModel;
import edu.mx.unsis.unsiSmile.model.medicalrecords.teeth.ToothFaceConditionModel;
import edu.mx.unsis.unsiSmile.model.medicalrecords.teeth.ToothFaceModel;
import edu.mx.unsis.unsiSmile.model.medicalrecords.teeth.ToothModel;
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
                                .observations(entity.getObservations())
                                .patientId(entity.getPatient().getIdPatient())
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

        // Modifica el método toOdontogramModel para manejar listas nulas en ToothRequest
        public static OdontogramModel toOdontogramModel(OdontogramRequest dto) {
                OdontogramModel odontogramModel = OdontogramModel.builder()
                                .observations(dto.getObservations())
                               .patient(PatientModel.builder()
                                        .idPatient(dto.getIdPatient())
                                        .build())
                                .build();

                // Mapeo de ToothConditionAssignments con verificación de nulos
                List<ToothConditionAssignmentModel> toothConditionAssignments = dto.getTeeth().stream()
                                .flatMap(toothDTO -> {
                                        List<ConditionRequest> conditions = toothDTO.getConditions() != null
                                                        ? toothDTO.getConditions()
                                                        : Collections.emptyList();
                                        return conditions.stream()
                                                        .map(conditionDTO -> mapToToothConditionAssignmentModel(toothDTO,
                                                                        conditionDTO));
                                })
                                .collect(Collectors.toList());
                odontogramModel.setToothConditionAssignments(toothConditionAssignments);

                // Mapeo de ToothFaceConditionsAssignments con verificación de nulos
                List<ToothfaceConditionsAssignmentModel> toothFaceConditionsAssignments = dto.getTeeth().stream()
                                .flatMap(toothDTO -> {
                                        List<FaceRequest> faces = toothDTO.getFaces() != null
                                                        ? toothDTO.getFaces()
                                                        : Collections.emptyList();
                                        return faces.stream()
                                                        .flatMap(faceDTO -> {
                                                                List<ToothFaceConditionRequest> faceConditions = faceDTO
                                                                                .getConditions() != null
                                                                                                ? faceDTO.getConditions()
                                                                                                : Collections.emptyList();
                                                                return faceConditions.stream()
                                                                                .map(conditionDTO -> mapToToothfaceConditionsAssignmentModel(
                                                                                                toothDTO, faceDTO,
                                                                                                conditionDTO));
                                                        });
                                })
                                .collect(Collectors.toList());
                odontogramModel.setToothFaceConditionsAssignments(toothFaceConditionsAssignments);

                return odontogramModel;
        }

        private static ToothConditionAssignmentModel mapToToothConditionAssignmentModel(ToothRequest toothDTO,
                        ConditionRequest conditionDTO) {
                ToothConditionAssignmentModel assignmentModel = new ToothConditionAssignmentModel();

                assignmentModel.setTooth(mapToToothModel(toothDTO));
                assignmentModel.setToothCondition(mapToToothConditionModel(conditionDTO));

                return assignmentModel;
        }

        private static ToothfaceConditionsAssignmentModel mapToToothfaceConditionsAssignmentModel(ToothRequest toothDTO,
                        FaceRequest faceDTO, ToothFaceConditionRequest conditionDTO) {
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

        // Método que centraliza el mapeo de condiciones de dientes y caras, devolviendo
        // un OdontogramResponse
        public OdontogramResponse mapConditionsAssignments(
                        List<ToothConditionAssignmentModel> toothConditionAssignments,
                        List<ToothfaceConditionsAssignmentModel> toothFaceConditions) {

                Map<String, ToothResponse> adultTeethMap = new HashMap<>();
                Map<String, ToothResponse> childTeethMap = new HashMap<>();

                // Procesar ToothConditionAssignments: condiciones generales de diente
                for (ToothConditionAssignmentModel tca : toothConditionAssignments) {
                        ConditionResponse conditionDTO = ConditionResponse.builder()
                                        .idCondition(tca.getToothCondition().getIdToothCondition())
                                        .condition(tca.getToothCondition().getDescription())
                                        .description(tca.getToothCondition().getDescription())
                                        .build();

                        if (tca.getTooth().isAdult()) {
                                ToothResponse adultToothResponse = adultTeethMap.computeIfAbsent(
                                                tca.getTooth().getIdTooth(),
                                                id -> new ToothResponse(id, new ArrayList<>(), new ArrayList<>()));
                                adultToothResponse.getConditions().add(conditionDTO);
                        } else {
                                ToothResponse childToothResponse = childTeethMap.computeIfAbsent(
                                                tca.getTooth().getIdTooth(),
                                                id -> new ToothResponse(id, new ArrayList<>(), new ArrayList<>()));
                                childToothResponse.getConditions().add(conditionDTO);
                        }
                }

                // Procesar ToothFaceConditions: condiciones en las caras del diente
                for (ToothfaceConditionsAssignmentModel tfca : toothFaceConditions) {
                        ConditionResponse conditionDTO = ConditionResponse.builder()
                                        .idCondition(tfca.getToothFaceCondition().getIdToothFaceCondition())
                                        .condition(tfca.getToothFaceCondition().getDescription())
                                        .description("Tooth face condition description") // Ajustar según corresponda
                                        .build();

                        if (tfca.getTooth().isAdult()) {
                                ToothResponse adultToothResponse = adultTeethMap.computeIfAbsent(
                                                tfca.getTooth().getIdTooth(),
                                                id -> new ToothResponse(id, new ArrayList<>(), new ArrayList<>()));

                                FaceResponse adultFace = adultToothResponse.getFaces().stream()
                                                .filter(f -> f.getIdFace().equals(tfca.getToothFace().getIdToothFace()))
                                                .findFirst()
                                                .orElseGet(() -> {
                                                        FaceResponse newFace = new FaceResponse(
                                                                        tfca.getToothFace().getIdToothFace(),
                                                                        new ArrayList<>());
                                                        adultToothResponse.getFaces().add(newFace);
                                                        return newFace;
                                                });
                                adultFace.getConditions().add(conditionDTO);
                        } else {
                                ToothResponse childToothResponse = childTeethMap.computeIfAbsent(
                                                tfca.getTooth().getIdTooth(),
                                                id -> new ToothResponse(id, new ArrayList<>(), new ArrayList<>()));

                                FaceResponse childFace = childToothResponse.getFaces().stream()
                                                .filter(f -> f.getIdFace().equals(tfca.getToothFace().getIdToothFace()))
                                                .findFirst()
                                                .orElseGet(() -> {
                                                        FaceResponse newFace = new FaceResponse(
                                                                        tfca.getToothFace().getIdToothFace(),
                                                                        new ArrayList<>());
                                                        childToothResponse.getFaces().add(newFace);
                                                        return newFace;
                                                });
                                childFace.getConditions().add(conditionDTO);
                        }
                }

                List<ToothResponse> adultArcade = new ArrayList<>(adultTeethMap.values());
                List<ToothResponse> childArcade = new ArrayList<>(childTeethMap.values());

                return OdontogramResponse.builder()
                                .adultArcade(adultArcade)
                                .childArcade(childArcade)
                                .build();
        }
}
