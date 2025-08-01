package edu.mx.unsis.unsiSmile.controller.addresses;

import edu.mx.unsis.unsiSmile.dtos.request.addresses.HousingRequest;
import edu.mx.unsis.unsiSmile.dtos.response.addresses.HousingResponse;
import edu.mx.unsis.unsiSmile.service.addresses.HousingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/unsismile/api/v1/housing")
@Validated
public class HousingController {

    private final HousingService housingService;

    public HousingController(HousingService housingService) {
        this.housingService = housingService;
    }

    @PostMapping
    public ResponseEntity<HousingResponse> createHousing(@Valid @RequestBody HousingRequest housingRequest) {
        HousingResponse createdHousing = housingService.createHousing(housingRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdHousing);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HousingResponse> getHousingById(@PathVariable String id) {
        HousingResponse housingResponse = housingService.getHousingById(id);
        return ResponseEntity.ok(housingResponse);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<HousingResponse> getHousingByCategory(@PathVariable String category) {
        HousingResponse housingResponse = housingService.getHousingByCategory(category);
        return ResponseEntity.ok(housingResponse);
    }

    @GetMapping("")
    public ResponseEntity<List<HousingResponse>> getAllHousing(
            @RequestParam(value = "search", required = false) String search) {

        List<HousingResponse> housings = housingService.getHousingsBySearch(search);
        return ResponseEntity.ok(housings);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HousingResponse> updateHousing(@PathVariable String id, @Valid @RequestBody HousingRequest updatedHousingRequest) {
        HousingResponse updatedHousing = housingService.updateHousing(id, updatedHousingRequest);
        return ResponseEntity.ok(updatedHousing);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHousingById(@PathVariable String id) {
        housingService.deleteHousingById(id);
        return ResponseEntity.noContent().build();
    }
}