package edu.mx.unsis.unsiSmile.service.medicalrecords.fluorosis;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.request.medicalrecords.fluorosis.FluorosisRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalrecords.fluorosis.FluorosisResponse;
import edu.mx.unsis.unsiSmile.dtos.response.medicalrecords.teeth.ConditionResponse;
import edu.mx.unsis.unsiSmile.dtos.response.medicalrecords.teeth.FaceResponse;
import edu.mx.unsis.unsiSmile.dtos.response.medicalrecords.teeth.ToothResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalrecords.fluorosis.FluorosisMapper;
import edu.mx.unsis.unsiSmile.model.medicalrecords.fluorosis.FluorosisModel;
import edu.mx.unsis.unsiSmile.model.medicalrecords.fluorosis.FluorosisToothConditionAssignmentModel;
import edu.mx.unsis.unsiSmile.model.medicalrecords.fluorosis.FluorosisToothfaceConditionsAssignmentModel;
import edu.mx.unsis.unsiSmile.repository.medicalrecords.fluorosis.IFluorosisRepository;
import edu.mx.unsis.unsiSmile.repository.medicalrecords.fluorosis.IFluorosisToothConditionAssignmentRepository;
import edu.mx.unsis.unsiSmile.repository.medicalrecords.fluorosis.IFluorosisToothfaceConditionsAssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FluorosisService {

    private final IFluorosisRepository fluorosisRepository;
    private final IFluorosisToothfaceConditionsAssignmentRepository toothFaceConditionAssignmentRepository;
    private final IFluorosisToothConditionAssignmentRepository toothConditionAssignmentRepository;
    private final FluorosisMapper fluorosisMapper;

    @Transactional(readOnly = true)
    public List<FluorosisResponse> getAllFluorosis() {
        try {
            List<FluorosisModel> allFluorosis = fluorosisRepository.findAll();
            return allFluorosis.stream()
                    .map(fluorosisMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_FETCH_FLUOROSIS, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteFluorosis(@NonNull Long id) {
        try {
            if (!fluorosisRepository.existsById(id)) {
                throw new AppException(ResponseMessages.FLUOROSIS_NOT_FOUND + id, HttpStatus.NOT_FOUND);
            }
            fluorosisRepository.deleteById(id);
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_DELETE_FLUOROSIS, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void saveFluorosis(FluorosisRequest fluorosisDTO) {
        try {
            FluorosisModel fluorosis = FluorosisMapper.toFluorosisModel(fluorosisDTO);

            fluorosis = fluorosisRepository.save(fluorosis);

            for (FluorosisToothConditionAssignmentModel assignment : fluorosis.getToothConditionAssignments()) {
                assignment.setFluorosis(fluorosis);
                toothConditionAssignmentRepository.save(assignment);
            }

            for (FluorosisToothfaceConditionsAssignmentModel condition : fluorosis.getToothFaceConditionsAssignments()) {
                condition.setFluorosis(fluorosis);
                toothFaceConditionAssignmentRepository.save(condition);
            }
        } catch (DataIntegrityViolationException e) {
            throw new AppException(ResponseMessages.DUPLICATE_ENTRY, HttpStatus.CONFLICT, e);
        } catch (Exception e) {
            throw new AppException(e.getCause().toString(), HttpStatus.BAD_REQUEST, e);
        }
    }

    @Transactional(readOnly = true)
    public FluorosisResponse getFluorosisByPatientMedicalRecordId(Long idPatientMedicalRecord) {
        try {
            FluorosisModel fluorosisModel = fluorosisRepository.findByPatientMedicalRecord_IdPatientMedicalRecord(idPatientMedicalRecord)
                    .orElseThrow(() -> new AppException(
                            ResponseMessages.FLUOROSIS_NOT_FOUND_BY_SECTION + idPatientMedicalRecord,
                            HttpStatus.NOT_FOUND));

            Long fluorosisId = fluorosisModel.getIdFluorosis();

            List<FluorosisToothConditionAssignmentModel> toothConditionAssignments = fluorosisRepository
                    .findToothConditionAssignmentsByFluorosisId(fluorosisId);

            List<FluorosisToothfaceConditionsAssignmentModel> toothFaceConditions = fluorosisRepository
                    .findToothFaceConditionsAssignmentByFluorosisId(fluorosisId);

            return buildFluorosisResponse(fluorosisId, fluorosisModel.getCreatedAt(), toothConditionAssignments, toothFaceConditions);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(String.format(ResponseMessages.FAILED_FETCH_FLUOROSIS_BY_SECTION, idPatientMedicalRecord),
                    HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    private FluorosisResponse buildFluorosisResponse(
            Long fluorosisId,
            Timestamp createdAt,
            List<FluorosisToothConditionAssignmentModel> toothConditionAssignments,
            List<FluorosisToothfaceConditionsAssignmentModel> toothFaceConditions) {

        Map<String, ToothResponse> teethMap = new HashMap<>();

        for (FluorosisToothConditionAssignmentModel tca : toothConditionAssignments) {
            ConditionResponse conditionDTO = ConditionResponse.builder()
                    .idCondition(tca.getToothCondition().getIdToothCondition())
                    .condition(tca.getToothCondition().getDescription())
                    .description(tca.getToothCondition().getDescription())
                    .build();

            ToothResponse toothResponse = teethMap.computeIfAbsent(
                    tca.getTooth().getIdTooth(),
                    id -> new ToothResponse(id, new ArrayList<>(), new ArrayList<>()));
            toothResponse.getConditions().add(conditionDTO);
        }

        for (FluorosisToothfaceConditionsAssignmentModel tfca : toothFaceConditions) {
            ConditionResponse conditionDTO = new ConditionResponse(
                    tfca.getToothFaceCondition().getIdToothFaceCondition(),
                    tfca.getToothFaceCondition().getDescription(),
                    "Tooth face condition description"
            );

            ToothResponse toothResponse = teethMap.computeIfAbsent(
                    tfca.getTooth().getIdTooth(),
                    id -> new ToothResponse(id, new ArrayList<>(), new ArrayList<>()));

            FaceResponse face = toothResponse.getFaces().stream()
                    .filter(f -> f.getIdFace().equals(tfca.getToothFace().getIdToothFace()))
                    .findFirst()
                    .orElseGet(() -> {
                        FaceResponse newFace = new FaceResponse(tfca.getToothFace().getIdToothFace(), new ArrayList<>());
                        toothResponse.getFaces().add(newFace);
                        return newFace;
                    });

            face.getConditions().add(conditionDTO);
        }

        List<ToothResponse> teethFluorosisList = new ArrayList<>(teethMap.values());

        return FluorosisResponse.builder()
                .idFluorosis(fluorosisId)
                .creationDate(createdAt.toLocalDateTime().toLocalDate())
                .teethFluorosis(teethFluorosisList)
                .build();
    }

    @Transactional(readOnly = true)
    public Page<FluorosisResponse> getFluorosisByPatientId(String patientId, Pageable pageable) {
        try {
            Page<FluorosisModel> fluorosisPage = fluorosisRepository.findByPatientId(patientId, pageable);
            return fluorosisPage.map(fluorosisModel -> {
                Long fluorosisId = fluorosisModel.getIdFluorosis();

                List<FluorosisToothConditionAssignmentModel> toothConditionAssignments = fluorosisRepository
                        .findToothConditionAssignmentsByFluorosisId(fluorosisId);

                List<FluorosisToothfaceConditionsAssignmentModel> toothFaceConditions = fluorosisRepository
                        .findToothFaceConditionsAssignmentByFluorosisId(fluorosisId);

                return buildFluorosisResponse(fluorosisId,fluorosisModel.getCreatedAt(), toothConditionAssignments, toothFaceConditions);
            });
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_FETCH_FLUOROSIS_BY_PATIENT, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}