package edu.mx.unsis.unsiSmile.mappers;

import edu.mx.unsis.unsiSmile.dtos.request.AnswerRequest;
import edu.mx.unsis.unsiSmile.dtos.response.AnswerResponse;
import edu.mx.unsis.unsiSmile.model.AnswerModel;
import edu.mx.unsis.unsiSmile.model.CatalogOptionModel;
import edu.mx.unsis.unsiSmile.model.PatientClinicalHistoryModel;
import edu.mx.unsis.unsiSmile.model.QuestionModel;
import edu.mx.unsis.unsiSmile.model.patients.PatientModel;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AnswerMapper implements BaseMapper<AnswerResponse, AnswerRequest, AnswerModel> {

    @Override
    public AnswerModel toEntity(AnswerRequest dto) {
        return AnswerModel.builder()
                .patientClinicalHistoryModel(PatientClinicalHistoryModel.builder()
                        .idPatientClinicalHistory(dto.getIdPatientClinicalHistory())
                        .build())
                .questionModel(QuestionModel.builder()
                        .idQuestion(dto.getIdQuestion())
                        .build())
                .answerBoolean(dto.getAnswerBoolean())
                .answerNumeric(dto.getAnswerNumeric() != null && dto.getAnswerNumeric().compareTo(BigDecimal.ZERO)
                        != 0 ? dto.getAnswerNumeric() : null)
                .answerText(dto.getAnswerText())
                .answerDate(dto.getAnswerDate())
                .catalogOptionModel(dto.getIdCatalogOption() != null ? CatalogOptionModel.builder()
                        .idCatalogOption(dto.getIdCatalogOption())
                        .build() : null)
                .build();
    }

    @Override
    public AnswerResponse toDto(AnswerModel entity) {
        return AnswerResponse.builder()
                .idAnswer(entity.getIdAnswer())
                .answerBoolean(entity.getAnswerBoolean())
                .answerNumeric(entity.getAnswerNumeric())
                .answerText(entity.getAnswerText())
                .answerDate(entity.getAnswerDate())
                .build();
    }

    @Override
    public List<AnswerResponse> toDtos(List<AnswerModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(AnswerRequest request, AnswerModel entity) {}

    public AnswerModel toEntityFromFile(PatientClinicalHistoryModel patientClinicalHistory, Long idQuestion) {
        return AnswerModel.builder()
                .patientModel(patientClinicalHistory != null ?
                        PatientModel.builder()
                                .idPatient(patientClinicalHistory.getPatient().getIdPatient())
                                .build() : null)
                .patientClinicalHistoryModel(patientClinicalHistory)
                .questionModel(QuestionModel.builder()
                        .idQuestion(idQuestion)
                        .build())
                .isFile(true)
                .build();
    }

}
