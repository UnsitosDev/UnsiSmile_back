package edu.mx.unsis.unsiSmile.service.files;

import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.dtos.response.FileResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.AnswerMapper;
import edu.mx.unsis.unsiSmile.mappers.FileMapper;
import edu.mx.unsis.unsiSmile.model.AnswerModel;
import edu.mx.unsis.unsiSmile.model.files.FileModel;
import edu.mx.unsis.unsiSmile.repository.IAnswerRepository;
import edu.mx.unsis.unsiSmile.repository.files.IFileRepository;
import io.jsonwebtoken.lang.Assert;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileService {
    private final IFileRepository fileRepository;
    private final FileMapper fileMapper;
    private final IAnswerRepository answerRepository;
    private final AnswerMapper answerMapper;

    public void upload(List<MultipartFile> files, UUID idPatient, Long idQuestion) {
        if (files.isEmpty() || idPatient == null || idQuestion == null) {
            throw new AppException("Empty file, idQuestion or idPatientClinicalHistory", HttpStatus.BAD_REQUEST);
        }

        Path uploadDir = Paths.get(Constants.UPLOAD_PATH);
        if (!Files.exists(uploadDir)) {
            try {
                Files.createDirectories(uploadDir);
            } catch (Exception e) {
                throw new AppException("Could not create upload directory", HttpStatus.INTERNAL_SERVER_ERROR, e);
            }
        }

        Long answerId = this.createFromFile(idPatient, idQuestion);

        for (MultipartFile file : files) {
            try {
                byte[] bytes = file.getBytes();
                UUID uuid = UUID.randomUUID();
                Path path = Paths.get(Constants.UPLOAD_PATH + uuid);
                String fileName = file.getOriginalFilename();
                assert fileName != null : "Filename is null";
                String ext = fileName.substring(fileName.lastIndexOf(".") + 1);  // Extraer la extensión sin el punto
                Files.write(path, bytes);

                FileModel fileModel = FileModel.builder()
                        .idFile(uuid)
                        .filePath(path.toString())
                        .fileName(fileName)
                        .fileType(ext)
                        .answer(AnswerModel.builder().idAnswer(answerId).build())
                        .build();

                fileRepository.save(fileModel);
            } catch (Exception e) {
                throw new AppException("Error while uploading file", HttpStatus.INTERNAL_SERVER_ERROR, e);
            }
        }
    }

    public void deleteFile(UUID idFile) {
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

    public FileModel updateFile(UUID idFile, MultipartFile newFile) {
        FileModel fileModel = fileRepository.findById(idFile)
                .orElseThrow(() -> new AppException("File not found", HttpStatus.NOT_FOUND));

        try {
            Path oldFilePath = Paths.get(fileModel.getFilePath());
            Files.deleteIfExists(oldFilePath);  // Eliminar el archivo antiguo

            byte[] bytes = newFile.getBytes();
            String uuid = UUID.randomUUID().toString().replace("-", "");
            Path newPath = Paths.get(Constants.UPLOAD_PATH + uuid);
            String newFileName = newFile.getOriginalFilename();
            assert newFileName != null : "New file name is null";
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
    public FileResponse findById(UUID id) {
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

    public ResponseEntity<byte[]> downloadFileById(UUID id) {
        FileModel fileModel = fileRepository.findById(id)
                .orElseThrow(() -> new AppException("File not found", HttpStatus.NOT_FOUND));

        try {
            Path filePath = Paths.get(fileModel.getFilePath());
            byte[] fileBytes = Files.readAllBytes(filePath);  // Leer los bytes del archivo

            return ResponseEntity.status(HttpStatus.OK)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileModel.getFileName() + "\"")
                    .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(filePath))
                    .body(fileBytes);
        } catch (Exception e) {
            throw new AppException("Error while downloading file", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    private Long createFromFile(UUID idPatient, Long idQuestion) {
        try {
            Optional<AnswerModel> existingAnswer =  answerRepository.findByQuestionModelIdQuestionAndPatientModel_IdPatient(
                    idQuestion, idPatient
            );

            if (existingAnswer.isPresent()) {
                return existingAnswer.get().getIdAnswer();
            }

            AnswerModel newAnswerModel = answerMapper.toEntityFromFile(idPatient, idQuestion);

            newAnswerModel = answerRepository.save(newAnswerModel);

            return newAnswerModel.getIdAnswer();
        } catch (Exception ex) {
            throw new AppException("Failed to save answer", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

}
