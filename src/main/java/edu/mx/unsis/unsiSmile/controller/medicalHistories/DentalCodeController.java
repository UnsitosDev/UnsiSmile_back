package edu.mx.unsis.unsiSmile.controller.medicalHistories;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.DentalCodeRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.DentalCodeResponse;
import edu.mx.unsis.unsiSmile.service.medicalHistories.DentalCodeService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/unsismile/api/v1/medical-histories/dental-codes")
public class DentalCodeController {

    private final DentalCodeService dentalCodeService;

    public DentalCodeController(DentalCodeService dentalCodeService) {
        this.dentalCodeService = dentalCodeService;
    }

    @PostMapping
    public ResponseEntity<DentalCodeResponse> createDentalCode(@Valid @RequestBody DentalCodeRequest request) {
        DentalCodeResponse createdDentalCodeResponse = dentalCodeService.createDentalCode(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDentalCodeResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DentalCodeResponse> getDentalCodeById(@Valid @PathVariable Long id) {
        DentalCodeResponse dentalCodeResponse = dentalCodeService.getDentalCodeById(id);
        return ResponseEntity.ok(dentalCodeResponse);
    }

    @GetMapping
    public ResponseEntity<List<DentalCodeResponse>> getAllDentalCodes() {
        List<DentalCodeResponse> allDentalCodes = dentalCodeService.getAllDentalCodes();
        return ResponseEntity.ok(allDentalCodes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DentalCodeResponse> updateDentalCode(@Valid @PathVariable Long id,
                                                                @Valid @RequestBody DentalCodeRequest updateRequest) {
        DentalCodeResponse updatedDentalCodeResponse = dentalCodeService.updateDentalCode(id, updateRequest);
        return ResponseEntity.ok(updatedDentalCodeResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDentalCode(@Valid @PathVariable Long id) {
        dentalCodeService.deleteDentalCode(id);
        return ResponseEntity.noContent().build();
    }

    // @GetMapping("/by-code")
    // public ResponseEntity<DentalCodeResponse> getDentalCodeByCode(@RequestParam(value = "code") String code) {
    //     Optional<DentalCodeModel> dentalCodeModel = dentalCodeService.findByCode(code);
    //     if (dentalCodeModel.isPresent()) {
    //         return ResponseEntity.ok(dentalCodeService..toDto(dentalCodeModel.get()));
    //     } else {
    //         return ResponseEntity.notFound().build();
    //     }
    // }
}
