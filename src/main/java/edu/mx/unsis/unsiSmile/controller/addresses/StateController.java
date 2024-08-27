package edu.mx.unsis.unsiSmile.controller.addresses;

import java.util.List;

import edu.mx.unsis.unsiSmile.dtos.response.students.StudentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.mx.unsis.unsiSmile.dtos.request.addresses.StateRequest;
import edu.mx.unsis.unsiSmile.dtos.response.addresses.StateResponse;
import edu.mx.unsis.unsiSmile.service.addresses.StateService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/unsismile/api/v1/address/states")
public class StateController {

    private final StateService stateService;

    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    @PostMapping
    public ResponseEntity<StateResponse> createState(@Valid @RequestBody StateRequest stateRequest) {
        StateResponse createdState = stateService.createState(stateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdState);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StateResponse> getStateById(@Valid @PathVariable String id) {
        StateResponse stateResponse = stateService.getStateById(id);
        return ResponseEntity.ok(stateResponse);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<StateResponse> getStateByName(@Valid @PathVariable String name) {
        StateResponse stateResponse = stateService.getStateByName(name);
        return ResponseEntity.ok(stateResponse);
    }

    @Operation(summary = "Obtener una lista paginada de estados")
    @GetMapping
    public ResponseEntity<Page<StateResponse>> getAllStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String order,
            @RequestParam(defaultValue = "true") boolean asc) {
        Sort sort = asc ? Sort.by(order).ascending() : Sort.by(order).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<StateResponse> stateResponses = stateService.getAllStates(pageable);

        return ResponseEntity.ok(stateResponses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StateResponse> updateState(@Valid @PathVariable String id, @Valid @RequestBody StateRequest updatedStateRequest) {
        StateResponse updatedState = stateService.updateState(id, updatedStateRequest);
        return ResponseEntity.ok(updatedState);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStateById(@Valid @PathVariable String id) {
        stateService.deleteStateById(id);
        return ResponseEntity.noContent().build();
    }
}