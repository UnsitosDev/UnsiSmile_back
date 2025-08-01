package edu.mx.unsis.unsiSmile.controller.patients;

import edu.mx.unsis.unsiSmile.dtos.request.patients.demographics.EthnicGroupRequest;
import edu.mx.unsis.unsiSmile.dtos.response.patients.demographics.EthnicGroupResponse;
import edu.mx.unsis.unsiSmile.service.patients.EthnicGroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/unsismile/api/v1/ethnic-groups")
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

    @Operation(summary = "Obtener una lista paginada de grupos étnicos.")
    @GetMapping
    public ResponseEntity<Page<EthnicGroupResponse>> getAllEthnicGroups(
            @Parameter(description = "Optional parameter to specify a search criterion.")
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ethnicGroup") String order,
            @RequestParam(defaultValue = "true") boolean asc) {
        Sort sort = asc ? Sort.by(order).ascending() : Sort.by(order).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<EthnicGroupResponse> ethnicGroupsResponses = ethnicGroupService.getAllEthnicGroups(pageable, keyword);

        return ResponseEntity.ok(ethnicGroupsResponses);
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