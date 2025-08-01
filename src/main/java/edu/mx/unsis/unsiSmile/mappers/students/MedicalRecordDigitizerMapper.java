package edu.mx.unsis.unsiSmile.mappers.students;

import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.dtos.request.digitizers.MedicalRecordDigitizerRequest;
import edu.mx.unsis.unsiSmile.dtos.response.students.MedicalRecordDigitizerResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.digitizers.MedicalRecordDigitizerModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class MedicalRecordDigitizerMapper implements BaseMapper<MedicalRecordDigitizerResponse, MedicalRecordDigitizerRequest, MedicalRecordDigitizerModel> {

    @Override
    public MedicalRecordDigitizerModel toEntity(MedicalRecordDigitizerRequest dto) {
        return MedicalRecordDigitizerModel.builder()
                .idMedicalRecordDigitizer(dto.getIdMedicalRecordDigitizer())
                .build();
    }

    @Override
    public MedicalRecordDigitizerResponse toDto(MedicalRecordDigitizerModel entity) {
        return MedicalRecordDigitizerResponse.builder()
                .idMedicalRecordDigitizer(entity.getIdMedicalRecordDigitizer())
                .username(entity.getUser().getUsername())
                .startDate(entity.getCreatedAt().toLocalDateTime().toLocalDate())
                .status(Constants.ACTIVE.equals(entity.getStatusKey()))
                .build();
    }

    @Override
    public List<MedicalRecordDigitizerResponse> toDtos(List<MedicalRecordDigitizerModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public void updateEntity(MedicalRecordDigitizerRequest request, MedicalRecordDigitizerModel entity) {
    }
}