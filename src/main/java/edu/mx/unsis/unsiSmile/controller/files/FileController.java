package edu.mx.unsis.unsiSmile.controller.files;

import edu.mx.unsis.unsiSmile.dtos.response.files.FileResponse;
import edu.mx.unsis.unsiSmile.service.files.FileService;
import edu.mx.unsis.unsiSmile.service.files.GeneralFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "FILE")
@RestController
@RequestMapping("/unsismile/api/v1/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;
    private final GeneralFileService generalFileService;

    @Operation(summary = "Crear un archivo, necesita una respuesta creada")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> upload(
            @RequestPart List<MultipartFile> files,
            @RequestPart @Validated String idPatientMedicalRecord,
            @RequestPart @Validated String idQuestion) {
        fileService.upload(files, Long.parseLong(idPatientMedicalRecord), Long.parseLong(idQuestion));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Obtiene todos los archivos asociados a una respuesta")
    @GetMapping("/answer/{answerId}")
    public ResponseEntity<List<FileResponse>> getFilesByAnswer(@PathVariable Long answerId) {
        List<FileResponse> response = fileService.getFilesByAnswer(answerId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene un archivo para su descarga")
    @GetMapping("file/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String id) {
        return fileService.downloadFileById(id);
    }

    @Operation(summary = "Carga los archivos generales para descarga y llenado (consentimiento/asentimiento informado)")
    @PostMapping(value = "/general-files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadGeneralFiles(
            @RequestPart List<MultipartFile> files) {
        generalFileService.uploadGeneralFiles(files);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Obtiene todos los archivos generales")
    @GetMapping("/general-files")
    public ResponseEntity<List<FileResponse>> getAllGeneralFiles() {
        List<FileResponse> response = generalFileService.getAllGeneralFiles();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene un archivo general para su descarga")
    @GetMapping("/general-files/{id}")
    public ResponseEntity<byte[]> downloadGeneralFile(@PathVariable String id) {
        return generalFileService.downloadGeneralFileById(id);
    }

    @Operation(summary = "Elimina un archivo general")
    @DeleteMapping("/general-files/{id}")
    public ResponseEntity<Void> deleteGeneralFile(@PathVariable String id) {
        generalFileService.deleteGeneralFileById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}