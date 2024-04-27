package edu.mx.unsis.unsiSmile.controller.medicalHistories;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.HousingMaterialRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.HousingMaterialResponse;
import edu.mx.unsis.unsiSmile.service.medicalHistories.HousingMaterialService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/unsismile/api/v1/medical-histories/housing-materials")
public class HousingMaterialController {

    private final HousingMaterialService housingMaterialService;

    public HousingMaterialController(HousingMaterialService housingMaterialService) {
        this.housingMaterialService = housingMaterialService;
    }

    @PostMapping
    public ResponseEntity<HousingMaterialResponse> createHousingMaterial(@Valid @RequestBody HousingMaterialRequest housingMaterialRequest) {
        HousingMaterialResponse createdHousingMaterial = housingMaterialService.createHousingMaterial(housingMaterialRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdHousingMaterial);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HousingMaterialResponse> getHousingMaterialById(@Valid @PathVariable Long id) {
        HousingMaterialResponse housingMaterialResponse = housingMaterialService.getHousingMaterialById(id);
        return ResponseEntity.ok(housingMaterialResponse);
    }

    @GetMapping
    public ResponseEntity<List<HousingMaterialResponse>> getAllHousingMaterials() {
        List<HousingMaterialResponse> housingMaterials = housingMaterialService.getAllHousingMaterials();
        return ResponseEntity.ok(housingMaterials);
    }

    // @PutMapping("/{id}")
    // public ResponseEntity<HousingMaterialResponse> updateHousingMaterial(@Valid @PathVariable Long id,
    //         @Valid @RequestBody HousingMaterialRequest updatedHousingMaterialRequest) {
    //     HousingMaterialResponse updatedHousingMaterial = housingMaterialService.update(id, updatedHousingMaterialRequest);
    //     return ResponseEntity.ok(updatedHousingMaterial);
    // }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHousingMaterialById(@Valid @PathVariable Long id) {
        housingMaterialService.deleteHousingMaterialById(id);
        return ResponseEntity.noContent().build();
    }
}
