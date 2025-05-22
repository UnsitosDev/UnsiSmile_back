package edu.mx.unsis.unsiSmile.mappers.professors;

import edu.mx.unsis.unsiSmile.dtos.request.professors.ProfessorClinicalAreaRequest;
import edu.mx.unsis.unsiSmile.dtos.response.professors.ProfessorClinicalAreaResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.professors.ClinicalAreaModel;
import edu.mx.unsis.unsiSmile.model.professors.ProfessorClinicalAreaModel;
import edu.mx.unsis.unsiSmile.model.professors.ProfessorModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ProfessorClinicalAreaMapper implements BaseMapper<ProfessorClinicalAreaResponse, ProfessorClinicalAreaRequest, ProfessorClinicalAreaModel> {
    private final ClinicalAreaMapper ClinicalAreaMapper;

    @Override
    public ProfessorClinicalAreaModel toEntity(ProfessorClinicalAreaRequest dto) {
        return ProfessorClinicalAreaModel.builder()
                .idProfessorClinicalArea(dto.getIdProfessorClinicalArea())
                .professor(ProfessorModel.builder()
                    .idProfessor(dto.getIdProfessor())
                    .build())
                .clinicalArea(ClinicalAreaModel.builder()
                    .idClinicalArea(dto.getIdClinicalArea())
                    .build())
                .startDate(LocalDate.now())
                .build();
    }

    @Override
    public ProfessorClinicalAreaResponse toDto(ProfessorClinicalAreaModel entity) {
        return ProfessorClinicalAreaResponse.builder()
                .idProfessorClinicalArea(entity.getIdProfessorClinicalArea())
                .professorName(entity.getProfessor().getPerson().getFullName())
                .clinicalArea(ClinicalAreaMapper.toDto(entity.getClinicalArea()))
                .build();
    }

    @Override
    public List<ProfessorClinicalAreaResponse> toDtos(List<ProfessorClinicalAreaModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public void updateEntity(ProfessorClinicalAreaRequest request, ProfessorClinicalAreaModel entity) {
        entity.setProfessor(ProfessorModel.builder()
            .idProfessor(request.getIdProfessor())
            .build());
        entity.setClinicalArea(ClinicalAreaModel.builder()
            .idClinicalArea(request.getIdClinicalArea())
            .build());
    }
}
