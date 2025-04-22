package edu.mx.unsis.unsiSmile.service.medicalHistories;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.request.AnswerRequest;
import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.DentalProphylaxisRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.ConditionResponse;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.DentalProphylaxisResponse;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.FaceResponse;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.ToothResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.DentalProphylaxisMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.DentalProphylaxisModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.odontogram.ProphylaxisToothConditionAssignmentModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.odontogram.ProphylaxisToothfaceConditionsAssignmentModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IDentalProphylaxisRepository;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IProphylaxisToothConditionAssignmentRepository;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IProphylaxisToothfaceConditionsAssignmentRepository;
import edu.mx.unsis.unsiSmile.service.AnswerService;
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
public class DentalProphylaxisService {

    private final IDentalProphylaxisRepository dentalProphylaxisRepository;
    private final IProphylaxisToothfaceConditionsAssignmentRepository toothFaceConditionAssignmentRepository;
    private final IProphylaxisToothConditionAssignmentRepository toothConditionAssignmentRepository;
    private final DentalProphylaxisMapper dentalProphylaxisMapper;
    private final AnswerService answerService;

    @Transactional(readOnly = true)
    public List<DentalProphylaxisResponse> getAllDentalProphylaxis() {
        try {
            List<DentalProphylaxisModel> allDentalProphylaxis = dentalProphylaxisRepository.findAll();
            return allDentalProphylaxis.stream()
                    .map(dentalProphylaxisMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_FETCH_DENTAL_PROPHYLAXIS, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteDentalProphylaxis(@NonNull Long id) {
        try {
            if (!dentalProphylaxisRepository.existsById(id)) {
                throw new AppException(ResponseMessages.DENTAL_PROPHYLAXIS_NOT_FOUND + id, HttpStatus.NOT_FOUND);
            }
            dentalProphylaxisRepository.deleteById(id);
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_DELETE_DENTAL_PROPHYLAXIS, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void saveDentalProphylaxis(DentalProphylaxisRequest dentalProphylaxisDTO) {
        try {
            DentalProphylaxisModel dentalProphylaxis = DentalProphylaxisMapper.toDentalProphylaxisModel(dentalProphylaxisDTO);

            dentalProphylaxis = dentalProphylaxisRepository.save(dentalProphylaxis);

            for (ProphylaxisToothConditionAssignmentModel assignment : dentalProphylaxis.getToothConditionAssignments()) {
                assignment.setDentalProphylaxis(dentalProphylaxis);
                toothConditionAssignmentRepository.save(assignment);
            }

            for (ProphylaxisToothfaceConditionsAssignmentModel condition : dentalProphylaxis.getToothFaceConditionsAssignments()) {
                condition.setDentalProphylaxis(dentalProphylaxis);
                toothFaceConditionAssignmentRepository.save(condition);
            }

            answerService.saveBatch(
                    List.of(
                            AnswerRequest.builder()
                                    .idQuestion(dentalProphylaxisDTO.getIdQuestion())
                                    .idPatientClinicalHistory(
                                            dentalProphylaxisDTO.getIdPatientClinicalHistory())
                                    .answerText("")
                                    .build()));

        } catch (DataIntegrityViolationException e) {
            throw new AppException(ResponseMessages.DUPLICATE_ENTRY, HttpStatus.CONFLICT, e);
        } catch (Exception e) {
            throw new AppException(e.getCause().toString(), HttpStatus.BAD_REQUEST, e);
        }

    }

    public DentalProphylaxisResponse getDentalProphylaxisByFormSectionId(Long formSectionId, String patientId) {
        DentalProphylaxisModel dentalProphylaxisModel = dentalProphylaxisRepository.findByFormSectionIdAndPatientId(formSectionId, patientId)
                .orElseThrow(() -> new AppException(ResponseMessages.DENTAL_PROPHYLAXIS_NOT_FOUND_BY_SECTION + formSectionId, HttpStatus.NOT_FOUND));

        Long dentalProphylaxisId = dentalProphylaxisModel.getIdDentalProphylaxis();

        List<ProphylaxisToothConditionAssignmentModel> toothConditionAssignments = dentalProphylaxisRepository
                .findToothConditionAssignmentsByProphylaxisId(dentalProphylaxisId);

        List<ProphylaxisToothfaceConditionsAssignmentModel> toothFaceConditions = dentalProphylaxisRepository
                .findToothFaceConditionsAssignmentByProphylaxisId(dentalProphylaxisId);

        return buildDentalProphylaxisResponse(dentalProphylaxisId, dentalProphylaxisModel.getCreatedAt(), toothConditionAssignments, toothFaceConditions);
    }

    private DentalProphylaxisResponse buildDentalProphylaxisResponse(
            Long dentalProphylaxisId,
            Timestamp createdAt,
            List<ProphylaxisToothConditionAssignmentModel> toothConditionAssignments,
            List<ProphylaxisToothfaceConditionsAssignmentModel> toothFaceConditions) {

        Map<String, ToothResponse> teethMap = new HashMap<>();

        for (ProphylaxisToothConditionAssignmentModel tca : toothConditionAssignments) {
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

        for (ProphylaxisToothfaceConditionsAssignmentModel tfca : toothFaceConditions) {
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

        List<ToothResponse> teethProphylaxisList = new ArrayList<>(teethMap.values());

        return DentalProphylaxisResponse.builder()
                .idDentalProphylaxis(dentalProphylaxisId)
                .creationDate(createdAt.toLocalDateTime().toLocalDate())
                .teethProphylaxis(teethProphylaxisList)
                .build();
    }

    @Transactional(readOnly = true)
    public Page<DentalProphylaxisResponse> getDentalProphylaxisByPatientId(String patientId, Pageable pageable) {
        try {
            Page<DentalProphylaxisModel> dentalProphylaxisPage = dentalProphylaxisRepository.findByPatient_IdPatientOrderByCreatedAtDesc(patientId, pageable);
            return dentalProphylaxisPage.map(dentalProphylaxisModel -> {
                Long dentalProphylaxisId = dentalProphylaxisModel.getIdDentalProphylaxis();

                List<ProphylaxisToothConditionAssignmentModel> toothConditionAssignments = dentalProphylaxisRepository
                        .findToothConditionAssignmentsByProphylaxisId(dentalProphylaxisId);

                List<ProphylaxisToothfaceConditionsAssignmentModel> toothFaceConditions = dentalProphylaxisRepository
                        .findToothFaceConditionsAssignmentByProphylaxisId(dentalProphylaxisId);

                return buildDentalProphylaxisResponse(dentalProphylaxisId,dentalProphylaxisModel.getCreatedAt(), toothConditionAssignments, toothFaceConditions);
            });
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_FETCH_DENTAL_PROPHYLAXIS_BY_PATIENT, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}