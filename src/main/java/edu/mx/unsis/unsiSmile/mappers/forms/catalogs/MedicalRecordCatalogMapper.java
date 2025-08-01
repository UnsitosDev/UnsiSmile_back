package edu.mx.unsis.unsiSmile.mappers.forms.catalogs;

import edu.mx.unsis.unsiSmile.dtos.request.forms.catalogs.MedicalRecordCatalogRequest;
import edu.mx.unsis.unsiSmile.dtos.response.forms.catalogs.MedicalRecordCatalogResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.forms.catalogs.MedicalRecordCatalogModel;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MedicalRecordCatalogMapper implements BaseMapper<MedicalRecordCatalogResponse, MedicalRecordCatalogRequest, MedicalRecordCatalogModel> {

    @Override
    public MedicalRecordCatalogModel toEntity(MedicalRecordCatalogRequest dto) {
        return MedicalRecordCatalogModel.builder()
                .medicalRecordName(dto.getMedicalRecordName())
                .build();
    }

    @Override
    public MedicalRecordCatalogResponse toDto(MedicalRecordCatalogModel entity) {
        return MedicalRecordCatalogResponse.builder()
                .idMedicalRecordCatalog(entity.getIdMedicalRecordCatalog())
                .medicalRecordName(entity.getMedicalRecordName())
                .medicalRecordNumber(null)
                .appointmentDate(null)
                .formSections(Collections.emptyList())
                .build();
    }

    @Override
    public List<MedicalRecordCatalogResponse> toDtos(List<MedicalRecordCatalogModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(MedicalRecordCatalogRequest request, MedicalRecordCatalogModel entity) {}
}