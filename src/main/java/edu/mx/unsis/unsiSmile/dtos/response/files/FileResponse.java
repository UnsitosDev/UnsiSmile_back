package edu.mx.unsis.unsiSmile.dtos.response.files;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileResponse {

    private String idFile;

    private String fileName;

    private String filePath;

    private String fileType;
}
