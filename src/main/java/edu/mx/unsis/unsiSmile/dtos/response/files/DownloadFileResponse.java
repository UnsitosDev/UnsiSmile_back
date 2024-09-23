package edu.mx.unsis.unsiSmile.dtos.response.files;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DownloadFileResponse {
    private String fileName;
    private String fileUrl;
    private String fileType;
    private String fileSize;
}
