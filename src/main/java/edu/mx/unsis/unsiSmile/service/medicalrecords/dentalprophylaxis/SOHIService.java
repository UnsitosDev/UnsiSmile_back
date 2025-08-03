package edu.mx.unsis.unsiSmile.service.medicalrecords.dentalprophylaxis;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.request.medicalrecords.dentalprophylaxis.EnumToothId;
import edu.mx.unsis.unsiSmile.dtos.request.medicalrecords.dentalprophylaxis.ToothCodeRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalrecords.dentalprophylaxis.ToothCodeResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalrecords.dentalprophylaxis.SOHIMapper;
import edu.mx.unsis.unsiSmile.model.medicalrecords.dentalprophylaxis.SOHIModel;
import edu.mx.unsis.unsiSmile.model.medicalrecords.dentalprophylaxis.SOHIToothCodeModel;
import edu.mx.unsis.unsiSmile.model.medicalrecords.teeth.ToothModel;
import edu.mx.unsis.unsiSmile.repository.medicalrecords.dentalprophylaxis.ISOHIRepository;
import edu.mx.unsis.unsiSmile.repository.medicalrecords.dentalprophylaxis.ISOHITootCodeRepository;
import edu.mx.unsis.unsiSmile.service.patients.PatientMedicalRecordService;
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
    private final PatientMedicalRecordService patientMedicalRecordService;
    private final SOHIMapper sohiMapper;

    @Transactional
    public void create(ToothCodeRequest request) {
        try {
            if (request == null) {
                throw new AppException(ResponseMessages.REQUEST_CANNOT_BE_NULL, HttpStatus.BAD_REQUEST);
            }

            patientMedicalRecordService.findById(request.getIdPatientMedicalRecord());

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
    public ToothCodeResponse getByPatientMedicalRecordId(Long idPatientMedicalRecord) {
        try {
            if (idPatientMedicalRecord == null || idPatientMedicalRecord <= 0) {
                throw new AppException(ResponseMessages.INVALID_PATIENT_MEDICAL_RECORD_ID, HttpStatus.BAD_REQUEST);
            }

            SOHIModel sohi = sohiRepository.findByPatientMedicalRecord_IdPatientMedicalRecord(idPatientMedicalRecord)
                    .orElseThrow(() -> new AppException(
                            String.format(ResponseMessages.SOHI_NOT_FOUND_FOR_MEDICAL_RECORD, idPatientMedicalRecord),
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
                    .idPatientMedicalRecord(sohi.getPatientMedicalRecord().getIdPatientMedicalRecord())
                    .teeth(teeth)
                    .build();
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.ERROR_FETCHING_SOHI, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}