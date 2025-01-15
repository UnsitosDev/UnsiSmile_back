package edu.mx.unsis.unsiSmile.controller.files;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import edu.mx.unsis.unsiSmile.dtos.response.FileResponse;
import edu.mx.unsis.unsiSmile.service.files.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "FILE")
@RestController
@RequestMapping("/unsismile/api/v1/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @Operation(summary = "Crear un archivo, necesita una respuesta creada")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> upload(
            @RequestPart List<MultipartFile> files,
            @RequestPart @Validated String idPatient,
            @RequestPart @Validated String idQuestion) {
        fileService.upload(files, idPatient, Long.parseLong(idQuestion));
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