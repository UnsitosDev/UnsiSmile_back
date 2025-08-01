package edu.mx.unsis.unsiSmile.mappers.medicalrecords.odontograms;

import edu.mx.unsis.unsiSmile.dtos.request.medicalrecords.odontograms.OdontogramRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalrecords.odontograms.OdontogramSimpleResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.medicalrecords.odontogram.OdontogramModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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