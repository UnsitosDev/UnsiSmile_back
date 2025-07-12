package edu.mx.unsis.unsiSmile.service.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.treatments.TreatmentDetailDigitizerRequest;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.model.medicalHistories.teeth.ToothModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.treatments.TreatmentDetailModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.treatments.TreatmentDetailToothModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.teeth.IToothRepository;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.treatments.ITreatmentDetailToothRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TreatmentDetailToothDigitizerService {

    private final ITreatmentDetailToothRepository treatmentDetailToothRepository;
    private final TreatmentDetailToothService treatmentDetailToothService;
    private final IToothRepository toothRepository;

    @Transactional
    public void createTreatmentDetailTeeth(TreatmentDetailDigitizerRequest request) {
        try {
            treatmentDetailToothService.verifyTreatmentDetailExists(request.getIdTreatmentDetail());

            List<String> toothIds = request.getTeeth().stream()
                    .map(TreatmentDetailDigitizerRequest.TreatmentToothRequest::getIdTooth)
                    .toList();

            for (String toothId : toothIds) {
                if (!toothRepository.existsById(toothId)) {
                    throw new AppException(
                            String.format(ResponseMessages.TOOTH_NOT_FOUND, toothId),
                            HttpStatus.NOT_FOUND
                    );
                }
            }

            if (hasDuplicates(toothIds)) {
                throw new AppException(ResponseMessages.DUPLICATE_TEETH_IDS, HttpStatus.BAD_REQUEST);
            }

            for (TreatmentDetailDigitizerRequest.TreatmentToothRequest toothRequest : request.getTeeth()) {
                if (toothRequest.getEndDate() == null) {
                    throw new AppException(
                            String.format(ResponseMessages.TOOTH_END_DATE_CANNOT_BE_NULL, toothRequest.getIdTooth()),
                            HttpStatus.BAD_REQUEST
                    );
                }
                TreatmentDetailToothModel model = TreatmentDetailToothModel.builder()
                        .treatmentDetail(TreatmentDetailModel.builder()
                                .idTreatmentDetail(request.getIdTreatmentDetail())
                                .build())
                        .tooth(ToothModel.builder().idTooth(toothRequest.getIdTooth()).build())
                        .startDate(request.getStartDate())
                        .endDate(toothRequest.getEndDate())
                        .build();

                treatmentDetailToothRepository.save(model);
            }

        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_CREATE_TREATMENT_DETAIL_TEETH, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    private boolean hasDuplicates(List<String> list) {
        return list.size() != list.stream().distinct().count();
    }

    @Transactional
    public void handleTeethByScope(String existingScope, String newScope, Long treatmentDetailId,
                                   TreatmentDetailDigitizerRequest request) {

        boolean wasTooth = Constants.TOOTH.equals(existingScope);
        boolean isTooth = Constants.TOOTH.equals(newScope);

        if (wasTooth && !isTooth) {
            // CASO 1: Eliminar todos los dientes
            treatmentDetailToothService.deleteAllByTreatmentDetailId(treatmentDetailId);

        } else if (!wasTooth && isTooth) {
            // CASO 2: Agregar nuevos dientes
            List<TreatmentDetailDigitizerRequest.TreatmentToothRequest> teeth = request.getTeeth();

            if (teeth == null || teeth.isEmpty()) {
                throw new AppException(ResponseMessages.TREATMENT_DETAIL_TOOTH_REQUEST_CANNOT_BE_NULL,
                        HttpStatus.BAD_REQUEST);
            }

            // Seteamos el ID del detalle de tratamiento
            request.setIdTreatmentDetail(treatmentDetailId);
            createTreatmentDetailTeeth(request);

        } else if (wasTooth) {
            // CASO 3: Actualizar dientes
            List<TreatmentDetailDigitizerRequest.TreatmentToothRequest> updatedTeeth = request.getTeeth();

            if (updatedTeeth == null || updatedTeeth.isEmpty()) {
                throw new AppException(ResponseMessages.TREATMENT_DETAIL_TOOTH_REQUEST_CANNOT_BE_NULL,
                        HttpStatus.BAD_REQUEST);
            }

            List<String> currentTeeth = treatmentDetailToothService.getAllTeethByTreatmentDetailId(treatmentDetailId);
            List<String> updatedToothIds = updatedTeeth.stream()
                    .map(TreatmentDetailDigitizerRequest.TreatmentToothRequest::getIdTooth)
                    .toList();

            // Identificar dientes a eliminar y agregar
            List<String> toDelete = currentTeeth.stream()
                    .filter(d -> !updatedToothIds.contains(d))
                    .toList();

            List<TreatmentDetailDigitizerRequest.TreatmentToothRequest> toAdd = updatedTeeth.stream()
                    .filter(tooth -> !currentTeeth.contains(tooth.getIdTooth()))
                    .toList();

            if (!toDelete.isEmpty()) {
                treatmentDetailToothService.deleteTeethByCodes(treatmentDetailId, toDelete);
            }

            if (!toAdd.isEmpty()) {
                TreatmentDetailDigitizerRequest toAddRequest = TreatmentDetailDigitizerRequest.builder()
                        .idTreatmentDetail(treatmentDetailId)
                        .teeth(toAdd)
                        .startDate(request.getStartDate())
                        .build();
                createTreatmentDetailTeeth(toAddRequest);
            }
        }
    }
}