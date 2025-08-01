package edu.mx.unsis.unsiSmile.mappers;

import edu.mx.unsis.unsiSmile.dtos.request.forms.answers.AnswerRequest;
import edu.mx.unsis.unsiSmile.dtos.response.AnswerResponse;
import edu.mx.unsis.unsiSmile.model.forms.answers.AnswerModel;
import edu.mx.unsis.unsiSmile.model.forms.catalogs.CatalogOptionModel;
import edu.mx.unsis.unsiSmile.model.forms.questions.QuestionModel;
import edu.mx.unsis.unsiSmile.model.patients.PatientMedicalRecordModel;
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
                .patientMedicalRecordModel(PatientMedicalRecordModel.builder()
                        .idPatientMedicalRecord(dto.getIdPatientMedicalRecord())
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

    public AnswerModel toEntityFromFile(PatientMedicalRecordModel patientMedicalRecord, Long idQuestion) {
        return AnswerModel.builder()
                .patientModel(patientMedicalRecord != null ?
                        PatientModel.builder()
                                .idPatient(patientMedicalRecord.getPatient().getIdPatient())
                                .build() : null)
                .patientMedicalRecordModel(patientMedicalRecord)
                .questionModel(QuestionModel.builder()
                        .idQuestion(idQuestion)
                        .build())
                .isFile(true)
                .build();
    }

}
