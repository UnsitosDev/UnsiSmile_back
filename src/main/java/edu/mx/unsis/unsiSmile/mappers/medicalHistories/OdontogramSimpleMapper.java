package edu.mx.unsis.unsiSmile.mappers.medicalHistories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.OdontogramRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.OdontogramSimpleResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.odontogram.OdontogramModel;

@Component
public class OdontogramSimpleMapper
                implements BaseMapper<OdontogramSimpleResponse, OdontogramRequest, OdontogramModel> {

        @Override
        public OdontogramModel toEntity(OdontogramRequest dto) {
                return OdontogramModel.builder()
                                .build();
        }

        @Override
        public OdontogramSimpleResponse toDto(OdontogramModel entity) {
                return OdontogramSimpleResponse.builder()
                                .idOdontogram(entity.getIdOdontogram())
                                .idPatient(entity.getPatient().getIdPatient())
                                .creationDate(entity.getCreatedAt().toLocalDateTime().toLocalDate())
                                .build();
        }

        @Override
        public List<OdontogramSimpleResponse> toDtos(List<OdontogramModel> entities) {
                return entities.stream()
                                .map(this::toDto)
                                .collect(Collectors.toList());
        }

        @Override
        public void updateEntity(OdontogramRequest request, OdontogramModel entity) {

        }

}
