package edu.mx.unsis.unsiSmile.mappers.patients;

import edu.mx.unsis.unsiSmile.dtos.response.patients.ProgressNoteFileResponse;
import edu.mx.unsis.unsiSmile.model.patients.ProgressNoteFileModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
@AllArgsConstructor
public class ProgressNoteFileMapper {

    public ProgressNoteFileResponse toDto(ProgressNoteFileModel entity, Timestamp creationDate) {
        return ProgressNoteFileResponse.builder()
                .idProgressNoteFile(entity.getIdProgressNoteFile())
                .fileName(formatDate(creationDate))
                .url(entity.getUrl())
                .extension(entity.getExtension())
                .build();
    }

    private String formatDate(Timestamp timestamp) {
        Instant instant = timestamp.toInstant();
        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}