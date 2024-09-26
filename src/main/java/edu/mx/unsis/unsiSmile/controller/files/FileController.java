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
import java.util.UUID;

@Tag(name = "FILE")
@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @Operation(summary = "Crear un archivo, necesita una respuesta creada")
    @PostMapping
    public UUID upload(@RequestPart MultipartFile file, @RequestPart @Validated Long answerId) {
        return fileService.upload(file, answerId);
    }

    @Operation(summary = "Busca un archivo por su id")
    @GetMapping("/{id}")
    public ResponseEntity<FileResponse> findById(@PathVariable String id) {
        FileResponse response = fileService.findById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Busca todos los archivos")
    @GetMapping
    public ResponseEntity<List<FileResponse>> findAll() {
        List<FileResponse> response = fileService.findAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene todos los archivos asociados a una respuesta")
    @GetMapping("/answer/{answerId}")
    public ResponseEntity<List<FileResponse>> getFilesByAnswer(@PathVariable Long answerId) {
        List<FileResponse> response = fileService.getFilesByAnswer(answerId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene un archivo para su descarga")
    @GetMapping("file/{id}")
    public ResponseEntity<byte[]> getFileById(@PathVariable String id) {
        return fileService.downloadFileById(id);
    }

}