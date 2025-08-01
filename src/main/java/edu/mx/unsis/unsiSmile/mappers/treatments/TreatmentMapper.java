package edu.mx.unsis.unsiSmile.mappers.treatments;

import edu.mx.unsis.unsiSmile.dtos.request.treatments.TreatmentRequest;
import edu.mx.unsis.unsiSmile.dtos.response.treatments.TreatmentResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.forms.catalogs.MedicalRecordCatalogModel;
import edu.mx.unsis.unsiSmile.model.treatments.TreatmentModel;
import edu.mx.unsis.unsiSmile.model.treatments.TreatmentScopeModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class TreatmentMapper implements BaseMapper<TreatmentResponse, TreatmentRequest, TreatmentModel> {

    private final TreatmentScopeMapper treatmentScopeMapper;

    @Override
    public TreatmentModel toEntity(TreatmentRequest dto) {
        return TreatmentModel.builder()
                .idTreatment(dto.getIdTreatment())
                .name(dto.getName())
                .treatmentScope(TreatmentScopeModel.builder()
                        .idTreatmentScope(dto.getTreatmentScopeId())
                        .build())
                .cost(dto.getCost() != null ? dto.getCost() : BigDecimal.ZERO)
                .medicalRecordCatalog(MedicalRecordCatalogModel.builder()
                                .idMedicalRecordCatalog(dto.getMedicalRecordCatalogId())
                                .build())
                .build();
    }

    @Override
    public TreatmentResponse toDto(TreatmentModel entity) {
        return TreatmentResponse.builder()
                .idTreatment(entity.getIdTreatment())
                .name(entity.getName())
                .treatmentScope(treatmentScopeMapper.toDto(entity.getTreatmentScope()))
                .cost(entity.getCost() != null ? entity.getCost() : BigDecimal.ZERO)
                .medicalRecordCatalogId(entity.getMedicalRecordCatalog() != null ?
                        entity.getMedicalRecordCatalog().getIdMedicalRecordCatalog() : null)
                .medicalRecordCatalogName(entity.getMedicalRecordCatalog() != null ?
                        entity.getMedicalRecordCatalog().getMedicalRecordName() : null)
                .build();
    }

    @Override
    public List<TreatmentResponse> toDtos(List<TreatmentModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(TreatmentRequest request, TreatmentModel entity) {
        entity.setName(request.getName());
        entity.setTreatmentScope(TreatmentScopeModel.builder()
                .idTreatmentScope(request.getTreatmentScopeId())
                .build());
        entity.setCost(request.getCost() != null ? request.getCost() : entity.getCost());
        entity.setMedicalRecordCatalog(
                MedicalRecordCatalogModel.builder()
                        .idMedicalRecordCatalog(request.getMedicalRecordCatalogId())
                        .build());
    }
}