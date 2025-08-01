package edu.mx.unsis.unsiSmile.controller;

import edu.mx.unsis.unsiSmile.dtos.request.forms.catalogs.CatalogOptionRequest;
import edu.mx.unsis.unsiSmile.dtos.response.forms.catalogs.CatalogOptionResponse;
import edu.mx.unsis.unsiSmile.service.forms.catalogs.CatalogOptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "CATALOG OPTIONS")
@RestController
@RequestMapping("/unsismile/api/v1/catalog-options")
@RequiredArgsConstructor
public class CatalogOptionController {

    private final CatalogOptionService catalogOptionService;

    @Operation(summary = "Crea una opción de respuesta para un catálogo.")
    @PostMapping
    public ResponseEntity<Void> save(@RequestBody CatalogOptionRequest request) {
        catalogOptionService.save(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Obtiene una opcion mediante su id")
    @GetMapping("/{id}")
    public ResponseEntity<CatalogOptionResponse> findById(@PathVariable Long id) {
        CatalogOptionResponse response = catalogOptionService.findById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene todas las opciones de respuesta.")
    @GetMapping
    public ResponseEntity<List<CatalogOptionResponse>> findAll() {
        List<CatalogOptionResponse> response = catalogOptionService.findAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Elimina una opción de respuesta mediante su id.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        catalogOptionService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Devuelve todas las opciones de un catálogo mediante el id del catálogo.")
    @GetMapping("/catalog/{catalogId}")
    public ResponseEntity<List<CatalogOptionResponse>> getOptionsByCatalog(@PathVariable Long catalogId) {
        List<CatalogOptionResponse> response = catalogOptionService.getOptionsByCatalog(catalogId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
