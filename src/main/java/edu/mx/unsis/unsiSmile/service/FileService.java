package edu.mx.unsis.unsiSmile.service;

import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.dtos.request.FileRequest;
import edu.mx.unsis.unsiSmile.dtos.response.FileResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.FileMapper;
import edu.mx.unsis.unsiSmile.model.FileModel;
import edu.mx.unsis.unsiSmile.repository.IFileRepository;
import io.jsonwebtoken.lang.Assert;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileService {
    private final IFileRepository fileRepository;
    private final FileMapper fileMapper;

    @Transactional
    public FileResponse save(FileRequest file) {
        try {
            Assert.notNull(file, "FileRequest cannot be null");

            FileModel fileModel = fileMapper.toEntity(file);

            FileModel fileSaved = fileRepository.save(fileModel);

            return fileMapper.toDto(fileSaved);
        } catch (Exception ex) {
            throw new AppException("Failed to save file", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public FileResponse findById(String id) {
        try {
            Assert.notNull(id, "Id cannot be null");

            FileModel fileModel = fileRepository.findById(id)
                    .orElseThrow(() -> new AppException("File not found with id: " + id, HttpStatus.NOT_FOUND));

            return fileMapper.toDto(fileModel);
        }catch (AppException ex){
            throw ex;
        }catch (Exception ex){
            throw new AppException("Failed to find file with id: " + id, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<FileResponse> findAll() {
        try {
            List<FileModel> fileModelList = fileRepository.findAll();

            if (fileModelList.isEmpty()) {
                throw new AppException("No files found", HttpStatus.NOT_FOUND);
            } else {
                return fileModelList.stream()
                        .map(fileMapper::toDto)
                        .collect(Collectors.toList());
            }
        } catch (Exception ex) {
            throw new AppException("Failed to fetch files", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteById(String id) {
        try {
            Optional<FileModel> fileOptional = fileRepository.findById(id);

            fileOptional.ifPresentOrElse(
                    file -> {
                        file.setStatusKey(Constants.INACTIVE);

                        fileRepository.save(file);
                    },
                    () -> {
                        throw new AppException("File not found with ID: " + id, HttpStatus.NOT_FOUND);
                    }
            );
        } catch (Exception ex) {
            throw new AppException("Failed to delete file with ID: " + id, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<FileResponse> getFilesByAnswer(Long answerId) {
        try {
            List<FileModel> fileModelList = fileRepository.findAllByAnswerId(answerId);

            return fileModelList.stream()
                    .map(fileMapper::toDto)
                    .collect(Collectors.toList());

        } catch (Exception ex) {
            throw new AppException("Failed to fetch files for answer with ID: " + answerId,
                    HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}
