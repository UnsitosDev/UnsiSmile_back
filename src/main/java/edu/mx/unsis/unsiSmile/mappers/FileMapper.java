package edu.mx.unsis.unsiSmile.mappers;

import edu.mx.unsis.unsiSmile.common.AppConstants;
import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.dtos.request.FileRequest;
import edu.mx.unsis.unsiSmile.dtos.response.FileResponse;
import edu.mx.unsis.unsiSmile.model.AnswerModel;
import edu.mx.unsis.unsiSmile.model.files.FileModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FileMapper implements BaseMapper<FileResponse, FileRequest, FileModel> {

    private AppConstants appConstants;

    @Override
    public FileModel toEntity(FileRequest dto) {
        return FileModel.builder()
                .idFile(UUID.randomUUID().toString())
                .fileName(dto.getFileName())
                .fileType(dto.getFileType())
                .filePath(dto.getFilePath())
                .answer(AnswerModel.builder()
                        .idAnswer(dto.getIdAnswer())
                        .build())
                .build();
    }

    @Override
    public FileResponse toDto(FileModel entity) {
        return FileResponse.builder()
                .idFile(entity.getIdFile())
                .fileName(entity.getFileName())
                .filePath(appConstants.getDownloadFilesUrl() + entity.getIdFile())
                .fileType(entity.getFileType())
                .build();
    }

    @Override
    public List<FileResponse> toDtos(List<FileModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(FileRequest request, FileModel entity) {}
}
