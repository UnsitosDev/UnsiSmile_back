package edu.mx.unsis.unsiSmile.mappers.students;

import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.dtos.request.students.MedicalRecordDigitizerRequest;
import edu.mx.unsis.unsiSmile.dtos.response.students.MedicalRecordDigitizerResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.students.MedicalRecordDigitizerModel;
import edu.mx.unsis.unsiSmile.model.students.StudentModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@AllArgsConstructor
public class MedicalRecordDigitizerMapper implements BaseMapper<MedicalRecordDigitizerResponse, MedicalRecordDigitizerRequest, MedicalRecordDigitizerModel> {

    @Override
    public MedicalRecordDigitizerModel toEntity(MedicalRecordDigitizerRequest dto) {
        return MedicalRecordDigitizerModel.builder()
                .idMedicalRecordDigitizer(dto.getIdMedicalRecordDigitizer())
                .student(StudentModel.builder()
                        .enrollment(dto.getIdStudent())
                        .build())
                .startDate(LocalDate.now())
                .build();
    }

    @Override
    public MedicalRecordDigitizerResponse toDto(MedicalRecordDigitizerModel entity) {
        return MedicalRecordDigitizerResponse.builder()
                .idMedicalRecordDigitizer(entity.getIdMedicalRecordDigitizer())
                .studentFullName(entity.getStudent().getPerson().getFullName())
                .idStudent(entity.getStudent().getEnrollment())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .status(addStatus(entity))
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
        entity.setStudent(StudentModel.builder()
                .enrollment(request.getIdStudent())
                .build());
    }

    private String addStatus(MedicalRecordDigitizerModel model) {
        if (model.getEndDate() != null) {
            return "FINALIZADO";
        } else if (Constants.ACTIVE.equalsIgnoreCase(model.getStatusKey())) {
            return "ACTIVO";
        } else {
            return "INACTIVO";
        }
    }
}