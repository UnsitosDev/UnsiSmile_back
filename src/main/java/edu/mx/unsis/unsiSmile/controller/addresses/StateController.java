package edu.mx.unsis.unsiSmile.controller.addresses;

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

    @GetMapping
    public ResponseEntity<List<StateResponse>> getAllStates() {
        List<StateResponse> allStates = stateService.getAllStates();
        return ResponseEntity.ok(allStates);
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