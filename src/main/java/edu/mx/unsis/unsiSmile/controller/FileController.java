package edu.mx.unsis.unsiSmile.controller;

import edu.mx.unsis.unsiSmile.dtos.request.FileRequest;
import edu.mx.unsis.unsiSmile.dtos.response.FileResponse;
import edu.mx.unsis.unsiSmile.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "-------------------FILE")
@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @Operation(summary = "Crear un archivo, **necesita una respuesta creada")
    @PostMapping
    public ResponseEntity<FileResponse> save(@RequestBody FileRequest request) {
        FileResponse response = fileService.save(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
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

    @Operation(summary = "Elimina un archivo por id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        fileService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Obtiene todos los archivos asociados a una respuesta")
    @GetMapping("/answer/{answerId}")
    public ResponseEntity<List<FileResponse>> getFilesByAnswer(@PathVariable Long answerId) {
        List<FileResponse> response = fileService.getFilesByAnswer(answerId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
