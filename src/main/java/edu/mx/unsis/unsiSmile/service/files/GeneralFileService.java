package edu.mx.unsis.unsiSmile.service.files;

import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.response.files.FileResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.model.files.GeneralFileModel;
import edu.mx.unsis.unsiSmile.repository.files.IGeneralFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GeneralFileService {
    private final FileService fileService;
    private final IGeneralFileRepository generalFileRepository;

    @Transactional
    public void uploadGeneralFiles(List<MultipartFile> files) {
        if (files.isEmpty()) {
            throw new AppException(ResponseMessages.EMPTY_FILE, HttpStatus.BAD_REQUEST);
        }

        fileService.processUpload(files, null, true);
    }

    @Transactional
    public ResponseEntity<byte[]> downloadGeneralFileById(String id) {
        GeneralFileModel fileModel = generalFileRepository.findById(id)
                .orElseThrow(() -> new AppException(ResponseMessages.FILE_NOT_FOUND, HttpStatus.NOT_FOUND));
        return fileService.buildFileDownloadResponse(fileModel.getUrl(), fileModel.getName());
    }

    @Transactional(readOnly = true)
    public List<FileResponse> getAllGeneralFiles() {
        try {
            List<GeneralFileModel> generalFiles = generalFileRepository.findAllByStatusKey(Constants.ACTIVE);

            if (generalFiles.isEmpty()) {
                throw new AppException(ResponseMessages.FILE_NOT_FOUND, HttpStatus.NOT_FOUND);
            }

            return generalFiles.stream()
                    .map(file -> FileResponse.builder()
                            .idFile(file.getIdFile())
                            .fileName(file.getName())
                            .filePath(file.getUrl())
                            .fileType(file.getExtension())
                            .build())
                    .collect(Collectors.toList());

        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_TO_FETCH_FILES,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public void deleteGeneralFileById(String id) {
        try {
            GeneralFileModel fileModel = generalFileRepository.findById(id)
                    .orElseThrow(() -> new AppException(ResponseMessages.FILE_NOT_FOUND, HttpStatus.NOT_FOUND));

            Path filePath = Paths.get(fileModel.getUrl());
            Files.deleteIfExists(filePath);

            fileModel.setStatusKey(Constants.INACTIVE);
            generalFileRepository.save(fileModel);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ResponseMessages.FAILED_TO_DELETE_FILE, HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }
}
