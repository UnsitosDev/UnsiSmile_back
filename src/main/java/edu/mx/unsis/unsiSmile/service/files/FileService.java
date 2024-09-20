package edu.mx.unsis.unsiSmile.service.files;

import edu.mx.unsis.unsiSmile.dtos.response.FileResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.FileMapper;
import edu.mx.unsis.unsiSmile.model.files.FileModel;
import edu.mx.unsis.unsiSmile.repository.files.IFileRepository;
import io.jsonwebtoken.lang.Assert;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileService {
    private final IFileRepository fileRepository;
    private final FileMapper fileMapper;
    private final String uploadPath = "uploads/";

    public UUID upload(MultipartFile file) {
        if (file.isEmpty()) {
            return null;
        }

        try {
            byte [] bytes = file.getBytes();
            String originalName = URLEncoder.encode(Objects.requireNonNull(file.getOriginalFilename()), StandardCharsets.UTF_8);
            String uuid = UUID.randomUUID().toString().replace("-", "");
            Path path = Paths.get(uploadPath + uuid);
            String fileName = file.getOriginalFilename();
            String ext = fileName.substring(fileName.lastIndexOf("."));
            Files.write(path, bytes);

            FileModel fileModel = FileModel.builder()
                    .idFile(uuid)
                    .filePath(path.toString())
                    .fileName(fileName)
                    .fileType(ext)
                    .build();

            fileRepository.save(fileModel);
            return UUID.fromString(uuid);
        }catch (Exception e){
            throw new AppException("", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }

    }

    public void deleteFile(String idFile) {
        FileModel fileModel = fileRepository.findById(idFile)
                .orElseThrow(() -> new AppException("File not found", HttpStatus.NOT_FOUND));

        try {
            Path filePath = Paths.get(fileModel.getFilePath());
            Files.deleteIfExists(filePath);  // Eliminar el archivo físico

            fileRepository.delete(fileModel);  // Eliminar el registro de la base de datos
        } catch (Exception e) {
            throw new AppException("Error while deleting file", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    public FileModel updateFile(String idFile, MultipartFile newFile) {
        FileModel fileModel = fileRepository.findById(idFile)
                .orElseThrow(() -> new AppException("File not found", HttpStatus.NOT_FOUND));

        try {

            Path oldFilePath = Paths.get(fileModel.getFilePath());
            Files.deleteIfExists(oldFilePath);

            byte[] bytes = newFile.getBytes();
            String originalName = URLEncoder.encode(Objects.requireNonNull(newFile.getOriginalFilename()), StandardCharsets.UTF_8);
            String uuid = UUID.randomUUID().toString().replace("-", "");
            Path newPath = Paths.get(uploadPath + uuid);
            String newFileName = newFile.getOriginalFilename();
            String ext = newFileName.substring(newFileName.lastIndexOf(".") + 1);  // Extraer la nueva extensión
            Files.write(newPath, bytes);

            // Actualizar el modelo con la nueva información
            fileModel.setFilePath(newPath.toString());
            fileModel.setFileName(newFileName);
            fileModel.setFileType(ext);

            return fileRepository.save(fileModel);  // Guardar los cambios en la base de datos
        } catch (Exception e) {
            throw new AppException("Error while updating file", HttpStatus.INTERNAL_SERVER_ERROR, e);
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
