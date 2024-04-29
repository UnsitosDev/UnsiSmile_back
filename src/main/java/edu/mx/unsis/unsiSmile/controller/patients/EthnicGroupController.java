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

import edu.mx.unsis.unsiSmile.dtos.request.patients.EthnicGroupRequest;
import edu.mx.unsis.unsiSmile.dtos.response.patients.EthnicGroupResponse;
import edu.mx.unsis.unsiSmile.service.patients.EthnicGroupService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/unsismile/api/v1/patients/ethnic-groups")
public class EthnicGroupController {

    private final EthnicGroupService ethnicGroupService;

    public EthnicGroupController(EthnicGroupService ethnicGroupService) {
        this.ethnicGroupService = ethnicGroupService;
    }

    @PostMapping
    public ResponseEntity<EthnicGroupResponse> createEthnicGroup(@Valid @RequestBody EthnicGroupRequest ethnicGroupRequest) {
        EthnicGroupResponse createdEthnicGroup = ethnicGroupService.createEthnicGroup(ethnicGroupRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEthnicGroup);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EthnicGroupResponse> getEthnicGroupById(@Valid @PathVariable Long id) {
        EthnicGroupResponse ethnicGroupResponse = ethnicGroupService.getEthnicGroupById(id);
        return ResponseEntity.ok(ethnicGroupResponse);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<EthnicGroupResponse> getEthnicGroupByName(@Valid @PathVariable String name) {
        EthnicGroupResponse ethnicGroupResponse = ethnicGroupService.getEthnicGroupByName(name);
        return ResponseEntity.ok(ethnicGroupResponse);
    }

    @GetMapping
    public ResponseEntity<List<EthnicGroupResponse>> getAllEthnicGroups() {
        List<EthnicGroupResponse> allEthnicGroups = ethnicGroupService.getAllEthnicGroups();
        return ResponseEntity.ok(allEthnicGroups);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EthnicGroupResponse> updateEthnicGroup(@Valid @PathVariable Long id, @Valid @RequestBody EthnicGroupRequest updatedEthnicGroupRequest) {
        EthnicGroupResponse updatedEthnicGroup = ethnicGroupService.updateEthnicGroup(id, updatedEthnicGroupRequest);
        return ResponseEntity.ok(updatedEthnicGroup);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEthnicGroupById(@Valid @PathVariable Long id) {
        ethnicGroupService.deleteEthnicGroupById(id);
        return ResponseEntity.noContent().build();
    }
}