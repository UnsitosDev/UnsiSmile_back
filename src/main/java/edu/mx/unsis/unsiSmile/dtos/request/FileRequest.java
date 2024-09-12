package edu.mx.unsis.unsiSmile.dtos.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileRequest {

    @NotNull(message = "The File Name cannot be null.")
    private String fileName;

    @NotNull(message = "The File Path cannot be null.")
    private String filePath;

    @NotNull(message = "The File Type cannot be null.")
    private String fileType;

    @NotNull(message = "The Id Answer cannot be null.")
    private Long idAnswer;
}
