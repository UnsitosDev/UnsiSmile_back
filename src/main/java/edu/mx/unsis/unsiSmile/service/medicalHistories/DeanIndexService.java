package edu.mx.unsis.unsiSmile.service.medicalHistories;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.request.medicalrecords.fluorosis.DeanIndexToothCodeRequest;
import edu.mx.unsis.unsiSmile.dtos.request.medicalrecords.fluorosis.EnumDeanIndexToothId;
import edu.mx.unsis.unsiSmile.dtos.response.medicalrecords.fluorosis.DeanIndexToothCodeResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalrecords.fluorosis.DeanIndexMapper;
import edu.mx.unsis.unsiSmile.model.medicalrecords.fluorosis.DeanIndexModel;
import edu.mx.unsis.unsiSmile.model.medicalrecords.fluorosis.DeanIndexToothCodeModel;
import edu.mx.unsis.unsiSmile.model.medicalrecords.teeth.ToothModel;
import edu.mx.unsis.unsiSmile.repository.medicalrecords.fluorosis.IDeanIndexRepository;
import edu.mx.unsis.unsiSmile.repository.medicalrecords.fluorosis.IDeanIndexToothCodeRepository;
import edu.mx.unsis.unsiSmile.repository.treatments.ITreatmentDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeanIndexService {
    private final IDeanIndexRepository deanIndexRepository;
    private final IDeanIndexToothCodeRepository deanIndexTootCodeRepository;
    private final ITreatmentDetailRepository treatmentDetailRepository;
    private final DeanIndexMapper deanIndexMapper;

    @Transactional
    public void create(DeanIndexToothCodeRequest request) {
        try {
            if (request == null) {
                throw new AppException(ResponseMessages.REQUEST_CANNOT_BE_NULL, HttpStatus.BAD_REQUEST);
            }

            treatmentDetailRepository.findById(request.getIdTreatment())
                    .orElseThrow(() -> new AppException(
                            String.format(ResponseMessages.TREATMENT_DETAIL_NOT_FOUND, request.getIdTreatment()),
                            HttpStatus.NOT_FOUND));

            DeanIndexModel deanIndex = deanIndexMapper.toEntity(request);
            DeanIndexModel savedSohi = deanIndexRepository.save(deanIndex);

            if (request.getTeeth() != null && !request.getTeeth().isEmpty()) {
                List<DeanIndexToothCodeModel> toothEntities = request.getTeeth().stream()
                        .filter(Objects::nonNull)
                        .map(tooth -> buildToothCodeEntity(tooth, savedSohi))
                        .collect(Collectors.toList());

                deanIndexTootCodeRepository.saveAll(toothEntities);
            }
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.ERROR_CREATING_DEAN_INDEX, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private DeanIndexToothCodeModel buildToothCodeEntity(DeanIndexToothCodeRequest.DeanIndexToothReq toothRequest, DeanIndexModel deanIndex) {
        return DeanIndexToothCodeModel.builder()
                .tooth(ToothModel.builder()
                        .idTooth(toothRequest.getIdTooth().getIdTooth())
                        .build())
                .code(toothRequest.getCode())
                .deanIndex(deanIndex)
                .build();
    }

    @Transactional(readOnly = true)
    public DeanIndexToothCodeResponse getByTreatmentId(Long idTreatment) {
        try {
            if (idTreatment == null || idTreatment <= 0) {
                throw new AppException(ResponseMessages.INVALID_TREATMENT_ID, HttpStatus.BAD_REQUEST);
            }

            DeanIndexModel deanIndex = deanIndexRepository.findByTreatmentDetail_IdTreatmentDetail(idTreatment)
                    .orElseThrow(() -> new AppException(
                            String.format(ResponseMessages.DEAN_INDEX_NOT_FOUND_FOR_TREATMENT, idTreatment),
                            HttpStatus.NOT_FOUND));

            List<DeanIndexToothCodeResponse.DeanIndexToothCodeResp> teeth = deanIndexTootCodeRepository.findByDeanIndex(deanIndex)
                    .stream()
                    .map(tooth -> DeanIndexToothCodeResponse.DeanIndexToothCodeResp.builder()
                            .id(tooth.getIdDeanIndexToothCode())
                            .idTooth(EnumDeanIndexToothId.getCodeFromDeanIndexIdTooth(tooth.getTooth().getIdTooth()))
                            .code(tooth.getCode())
                            .build())
                    .toList();

            return DeanIndexToothCodeResponse.builder()
                    .id(deanIndex.getIdDeanIndex())
                    .idTreatment(deanIndex.getTreatmentDetail().getIdTreatmentDetail())
                    .teeth(teeth)
                    .build();
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.ERROR_FETCHING_DEAN_INDEX, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}