package edu.mx.unsis.unsiSmile.dtos.response.files;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileResponse {

    private String idFile;
    private String fileName;
    private String filePath;
    private String fileType;
    private LocalDateTime creationDate;
}
