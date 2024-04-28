package edu.mx.unsis.unsiSmile.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.mx.unsis.unsiSmile.dtos.response.addresses.StateResponse;
import edu.mx.unsis.unsiSmile.service.addresses.StateService;
import jakarta.validation.Valid;

@RestController
s@RequestMapping("/unsismile/api/v1/addresses/state")
public class StateController {

    private final StateService stateService;

    public StateController(StateService stateService) {
        this.stateService = stateService;
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
}