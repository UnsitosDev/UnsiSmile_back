package edu.mx.unsis.unsiSmile.controller;

import edu.mx.unsis.unsiSmile.dtos.request.forms.catalogs.CatalogRequest;
import edu.mx.unsis.unsiSmile.dtos.response.CatalogResponse;
import edu.mx.unsis.unsiSmile.service.CatalogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "ANSWER CATALOGS")
@RestController
@RequestMapping("/unsismile/api/v1/catalogs")
@RequiredArgsConstructor
public class CatalogController {

    private final CatalogService catalogService;

    @Operation(summary = "Crea un tipo de catálogo.")
    @PostMapping
    public ResponseEntity<Void> save(@RequestBody CatalogRequest request) {
        catalogService.save(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Obtiene un catálogo por su id.")
    @GetMapping("/{id}")
    public ResponseEntity<CatalogResponse> findById(@PathVariable Long id) {
        CatalogResponse response = catalogService.findById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene todos los tipos de catálogos.")
    @GetMapping
    public ResponseEntity<List<CatalogResponse>> findAll() {
        List<CatalogResponse> response = catalogService.findAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Elimina un catálogo por su id.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        catalogService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
