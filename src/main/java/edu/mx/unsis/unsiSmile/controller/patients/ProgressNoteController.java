package edu.mx.unsis.unsiSmile.controller.patients;

import edu.mx.unsis.unsiSmile.dtos.request.patients.ProgressNoteRequest;
import edu.mx.unsis.unsiSmile.dtos.response.patients.ProgressNoteResponse;
import edu.mx.unsis.unsiSmile.service.patients.ProgressNoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "PROGRESS NOTES")
@RestController
@RequestMapping("/unsismile/api/v1/progress-notes")
@RequiredArgsConstructor
public class ProgressNoteController {

    private final ProgressNoteService progressNoteService;

    @Operation(summary = "Obtener una lista paginada de notas de evolución de un paciente")
    @GetMapping("/{patientId}")
    public ResponseEntity<Page<ProgressNoteResponse>> getProgressNotesByPatient(
            @PathVariable String patientId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String order,
            @RequestParam(defaultValue = "false") boolean asc) {

        Sort sort = asc ? Sort.by(order).ascending() : Sort.by(order).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<ProgressNoteResponse> progressNotes = progressNoteService.getProgressNotesByPatient(patientId, pageable);

        return ResponseEntity.ok(progressNotes);
    }

    @PostMapping
    @Operation(summary = "Crea un registro de nota de evolución.")
    public ResponseEntity<ProgressNoteResponse> createProgressNote(@RequestBody ProgressNoteRequest request) {
        ProgressNoteResponse progressNote = progressNoteService.createProgressNote(request);
        return ResponseEntity.ok(progressNote);
    }

    @Operation(summary = "Carga el archivo de nota de evolución.")
    @PostMapping(value = "/files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadProgressNote(@RequestPart List<MultipartFile> progressNoteFiles,
                                                   @RequestParam String progressNoteId) {
        progressNoteService.uploadProgressNote(progressNoteFiles, progressNoteId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Genera el pdf basado en la información de la nota de evolución.")
    @GetMapping("files/{id}/generate-pdf")
    public ResponseEntity<byte[]> buildProgressNotePdfById(@PathVariable String id) {
        return progressNoteService.buildProgressNotePdfById(id);
    }

    @Operation(summary = "Descarga el archivo de nota de evolución.")
    @GetMapping("files/{id}/download")
    public ResponseEntity<byte[]> downloadProgressNoteFile(@PathVariable String id) {
        return progressNoteService.downloadProgressNoteFile(id);
    }
}