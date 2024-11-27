package edu.mx.unsis.unsiSmile.mappers;

import edu.mx.unsis.unsiSmile.dtos.request.AnswerRequest;
import edu.mx.unsis.unsiSmile.dtos.response.AnswerResponse;
import edu.mx.unsis.unsiSmile.model.AnswerModel;
import edu.mx.unsis.unsiSmile.model.CatalogOptionModel;
import edu.mx.unsis.unsiSmile.model.PatientClinicalHistoryModel;
import edu.mx.unsis.unsiSmile.model.QuestionModel;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
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

    public void updateEntity(AnswerRequest request, AnswerModel entity) {
        updateField(request.getAnswerBoolean(), entity::setAnswerBoolean);
        updateField(request.getAnswerNumeric(), value -> {
            if (value.compareTo(BigDecimal.ZERO) != 0) {
                entity.setAnswerNumeric(value);
            }
        });
        updateField(request.getAnswerText(), entity::setAnswerText);
        updateField(request.getAnswerDate(), entity::setAnswerDate);
        updateCatalogOption(request.getIdCatalogOption(), entity);
    }

    private <T> void updateField(T value, Consumer<T> updater) {
        Optional.ofNullable(value).ifPresent(updater);
    }

    private void updateCatalogOption(Long idCatalogOption, AnswerModel entity) {
        Optional.ofNullable(idCatalogOption).ifPresent(id ->
                entity.setCatalogOptionModel(CatalogOptionModel.builder()
                        .idCatalogOption(id)
                        .build())
        );
    }

    public AnswerModel toEntityFromFile(Long idPatientClinicalHistory, Long idQuestion) {
        return AnswerModel.builder()
                .patientClinicalHistoryModel(PatientClinicalHistoryModel.builder()
                        .idPatientClinicalHistory(idPatientClinicalHistory)
                        .build())
                .questionModel(QuestionModel.builder()
                        .idQuestion(idQuestion)
                        .build())
                .isFile(true)
                .build();
    }

}
