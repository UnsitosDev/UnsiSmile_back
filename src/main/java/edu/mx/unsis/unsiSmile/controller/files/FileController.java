package edu.mx.unsis.unsiSmile.controller.files;

import edu.mx.unsis.unsiSmile.dtos.response.FileResponse;
import edu.mx.unsis.unsiSmile.service.files.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @Operation(summary = "Crear un archivo, necesita una respuesta creada")
    @PostMapping
    public ResponseEntity<Void> upload(
            @RequestPart List<MultipartFile> files,
            @RequestPart @Validated String idPatientClinicalHistory,
            @RequestPart @Validated String idQuestion) {
        fileService.upload(files, Long.parseLong(idPatientClinicalHistory), Long.parseLong(idQuestion));
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

}