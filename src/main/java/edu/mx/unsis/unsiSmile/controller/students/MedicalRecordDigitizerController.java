package edu.mx.unsis.unsiSmile.controller.students;

import edu.mx.unsis.unsiSmile.dtos.request.students.MedicalRecordDigitizerRequest;
import edu.mx.unsis.unsiSmile.dtos.response.students.MedicalRecordDigitizerResponse;
import edu.mx.unsis.unsiSmile.service.students.MedicalRecordDigitizerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/unsismile/api/v1/medical-record-digitizers")
@RequiredArgsConstructor
public class MedicalRecordDigitizerController {

    private final MedicalRecordDigitizerService digitizerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody MedicalRecordDigitizerRequest request) {
        digitizerService.createDigitizer(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalRecordDigitizerResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(digitizerService.getDigitizerById(id));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void update(@Valid @RequestBody MedicalRecordDigitizerRequest request) {
        digitizerService.updateDigitizer(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        digitizerService.deleteDigitizer(id);
    }

    @GetMapping
    public ResponseEntity<Page<MedicalRecordDigitizerResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "student.person.firstName") String order,
            @RequestParam(defaultValue = "true") boolean asc,
            @RequestParam(required = false) String keyword) {

        Sort sort = asc ? Sort.by(order).ascending() : Sort.by(order).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(digitizerService.getAllDigitizers(pageable, keyword));
    }
}