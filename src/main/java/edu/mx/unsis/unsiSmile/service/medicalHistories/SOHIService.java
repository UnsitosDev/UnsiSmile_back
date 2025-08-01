package edu.mx.unsis.unsiSmile.service.medicalHistories;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.EnumToothId;
import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.ToothCodeRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.ToothCodeResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.SOHIMapper;
import edu.mx.unsis.unsiSmile.model.medicalrecords.dentalprophylaxis.SOHIModel;
import edu.mx.unsis.unsiSmile.model.medicalrecords.dentalprophylaxis.SOHIToothCodeModel;
import edu.mx.unsis.unsiSmile.model.medicalrecords.teeth.ToothModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.ISOHIRepository;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.ISOHITootCodeRepository;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.treatments.ITreatmentDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SOHIService {
    private final ISOHIRepository sohiRepository;
    private final ISOHITootCodeRepository sohiTootCodeRepository;
    private final ITreatmentDetailRepository treatmentDetailRepository;
    private final SOHIMapper sohiMapper;

    @Transactional
    public void create(ToothCodeRequest request) {
        try {
            if (request == null) {
                throw new AppException(ResponseMessages.REQUEST_CANNOT_BE_NULL, HttpStatus.BAD_REQUEST);
            }

            treatmentDetailRepository.findById(request.getIdTreatment())
                    .orElseThrow(() -> new AppException(
                            String.format(ResponseMessages.TREATMENT_DETAIL_NOT_FOUND, request.getIdTreatment()),
                            HttpStatus.NOT_FOUND));

            SOHIModel sohi = sohiMapper.toEntity(request);
            SOHIModel savedSohi = sohiRepository.save(sohi);

            if (request.getTeeth() != null && !request.getTeeth().isEmpty()) {
                List<SOHIToothCodeModel> toothEntities = request.getTeeth().stream()
                        .filter(Objects::nonNull)
                        .map(tooth -> buildToothCodeEntity(tooth, savedSohi))
                        .collect(Collectors.toList());

                sohiTootCodeRepository.saveAll(toothEntities);
            }
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.ERROR_CREATING_SOHI, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private SOHIToothCodeModel buildToothCodeEntity(ToothCodeRequest.ToothReq toothRequest, SOHIModel sohi) {
        return SOHIToothCodeModel.builder()
                .tooth(ToothModel.builder()
                        .idTooth(toothRequest.getIdTooth().getIdTooth())
                        .build())
                .code(toothRequest.getCode())
                .sohi(sohi)
                .build();
    }

    @Transactional(readOnly = true)
    public ToothCodeResponse getByTreatmentId(Long idTreatment) {
        try {
            if (idTreatment == null || idTreatment <= 0) {
                throw new AppException(ResponseMessages.INVALID_TREATMENT_ID, HttpStatus.BAD_REQUEST);
            }

            SOHIModel sohi = sohiRepository.findByTreatmentDetail_IdTreatmentDetail(idTreatment)
                    .orElseThrow(() -> new AppException(
                            String.format(ResponseMessages.SOHI_NOT_FOUND_FOR_TREATMENT, idTreatment),
                            HttpStatus.NOT_FOUND));

            List<ToothCodeResponse.ToothResp> teeth = sohiTootCodeRepository.findBySohi(sohi)
                    .stream()
                    .map(tooth -> ToothCodeResponse.ToothResp.builder()
                            .id(tooth.getId())
                            .idTooth(EnumToothId.getCodeFromIdTooth(tooth.getTooth().getIdTooth()))
                            .code(tooth.getCode())
                            .build())
                    .toList();

            return ToothCodeResponse.builder()
                    .id(sohi.getIdSohi())
                    .idTreatment(sohi.getTreatmentDetail().getIdTreatmentDetail())
                    .teeth(teeth)
                    .build();
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.ERROR_FETCHING_SOHI, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}