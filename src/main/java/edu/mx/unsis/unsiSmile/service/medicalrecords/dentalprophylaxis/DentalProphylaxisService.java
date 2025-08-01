package edu.mx.unsis.unsiSmile.service.medicalrecords.dentalprophylaxis;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.request.medicalrecords.dentalprophylaxis.DentalProphylaxisRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalrecords.dentalprophylaxis.DentalProphylaxisResponse;
import edu.mx.unsis.unsiSmile.dtos.response.medicalrecords.teeth.ConditionResponse;
import edu.mx.unsis.unsiSmile.dtos.response.medicalrecords.teeth.FaceResponse;
import edu.mx.unsis.unsiSmile.dtos.response.medicalrecords.teeth.ToothResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalrecords.dentalprophylaxis.DentalProphylaxisMapper;
import edu.mx.unsis.unsiSmile.model.medicalrecords.dentalprophylaxis.DentalProphylaxisModel;
import edu.mx.unsis.unsiSmile.model.medicalrecords.dentalprophylaxis.ProphylaxisToothConditionAssignmentModel;
import edu.mx.unsis.unsiSmile.model.medicalrecords.dentalprophylaxis.ProphylaxisToothfaceConditionsAssignmentModel;
import edu.mx.unsis.unsiSmile.repository.medicalrecords.dentalprophylaxis.IDentalProphylaxisRepository;
import edu.mx.unsis.unsiSmile.repository.medicalrecords.dentalprophylaxis.IProphylaxisToothConditionAssignmentRepository;
import edu.mx.unsis.unsiSmile.repository.medicalrecords.dentalprophylaxis.IProphylaxisToothfaceConditionsAssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
        } catch (DataIntegrityViolationException e) {
            throw new AppException(ResponseMessages.DUPLICATE_ENTRY, HttpStatus.CONFLICT, e);
        } catch (Exception e) {
            throw new AppException(e.getCause().toString(), HttpStatus.BAD_REQUEST, e);
        }

    }

    @Transactional
    public Page<DentalProphylaxisResponse> getDentalProphylaxisByTreatmentId(Long idTreatment, Pageable pageable) {
        try {
            Page<DentalProphylaxisModel> dentalProphylaxisPage =
                    dentalProphylaxisRepository.findByTreatmentId(idTreatment, pageable);

            return mapToDentalProphylaxisResponsePage(dentalProphylaxisPage);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(String.format(ResponseMessages.FAILED_FETCH_DENTAL_PROPHYLAXIS_BY_TREATMENT, idTreatment),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private DentalProphylaxisResponse buildDentalProphylaxisResponse(
            Long dentalProphylaxisId,
            Timestamp createdAt,
            BigDecimal percentage,
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
                .percentage(percentage)
                .teethProphylaxis(teethProphylaxisList)
                .build();
    }

    @Transactional(readOnly = true)
    public Page<DentalProphylaxisResponse> getDentalProphylaxisByPatientId(String patientId, Pageable pageable) {
        try {
            Page<DentalProphylaxisModel> dentalProphylaxisPage = dentalProphylaxisRepository.findByPatientId(patientId, pageable);
            return mapToDentalProphylaxisResponsePage(dentalProphylaxisPage);
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_FETCH_DENTAL_PROPHYLAXIS_BY_PATIENT, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    private Page<DentalProphylaxisResponse> mapToDentalProphylaxisResponsePage(Page<DentalProphylaxisModel> page) {
        return page.map(dentalProphylaxisModel -> {
            Long dentalProphylaxisId = dentalProphylaxisModel.getIdDentalProphylaxis();

            List<ProphylaxisToothConditionAssignmentModel> toothConditionAssignments =
                    dentalProphylaxisRepository.findToothConditionAssignmentsByProphylaxisId(dentalProphylaxisId);

            List<ProphylaxisToothfaceConditionsAssignmentModel> toothFaceConditions =
                    dentalProphylaxisRepository.findToothFaceConditionsAssignmentByProphylaxisId(dentalProphylaxisId);

            return buildDentalProphylaxisResponse(
                    dentalProphylaxisId,
                    dentalProphylaxisModel.getCreatedAt(),
                    dentalProphylaxisModel.getPercentage(),
                    toothConditionAssignments,
                    toothFaceConditions
            );
        });
    }
}