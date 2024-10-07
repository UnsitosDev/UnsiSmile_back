package edu.mx.unsis.unsiSmile.controller;

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

import edu.mx.unsis.unsiSmile.dtos.request.GenderRequest;
import edu.mx.unsis.unsiSmile.dtos.response.GenderResponse;
import edu.mx.unsis.unsiSmile.service.GenderService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/unsismile/api/v1/genders")
public class GenderController {

        private final GenderService genderService;

        public GenderController(GenderService genderService) {
                this.genderService = genderService;
        }

        @PostMapping
        public ResponseEntity<Void> createGender(@Valid @RequestBody GenderRequest genderRequest) {
                genderService.createGender(genderRequest);
                return new ResponseEntity<>(HttpStatus.CREATED);
        }

        @GetMapping("/{id}")
        public ResponseEntity<GenderResponse> getGenderById(@Valid @PathVariable Long id) {
                GenderResponse genderResponse = genderService.getGenderById(id);
                return ResponseEntity.ok(genderResponse);
        }

        @GetMapping
        public ResponseEntity<List<GenderResponse>> getAllGenders() {
                List<GenderResponse> allGenders = genderService.getAllGenders();
                return ResponseEntity.ok(allGenders);
        }

        @PutMapping("/{id}")
        public ResponseEntity<GenderResponse> updateGender(@Valid @PathVariable Long id,
                        @Valid @RequestBody GenderRequest updatedGenderRequest) {
                GenderResponse updatedGender = genderService.updateGender(id, updatedGenderRequest);
                return ResponseEntity.ok(updatedGender);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<?> deleteGenderById(@Valid @PathVariable Long id) {
                genderService.deleteGenderById(id);
                return ResponseEntity.noContent().build();
        }
}
