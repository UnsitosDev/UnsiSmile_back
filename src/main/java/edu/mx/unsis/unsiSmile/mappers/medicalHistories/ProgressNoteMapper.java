package edu.mx.unsis.unsiSmile.mappers.medicalHistories;

import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.ProgressNoteResponse;
import edu.mx.unsis.unsiSmile.model.patients.ProgressNoteModel;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
public class ProgressNoteMapper {
    public ProgressNoteResponse toDto(ProgressNoteModel entity) {
        String fileName = formatDate(entity.getCreatedAt());

        return ProgressNoteResponse.builder()
                .idProgressNote(entity.getIdProgressNote())
                .fileName(fileName)
                .extention(entity.getExtention())
                .url(entity.getUrl())
                .build();
    }

    private String formatDate(Timestamp timestamp) {
        Instant instant = timestamp.toInstant();
        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
