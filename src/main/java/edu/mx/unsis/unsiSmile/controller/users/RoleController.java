package edu.mx.unsis.unsiSmile.controller.users;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.mx.unsis.unsiSmile.dtos.response.users.RoleResponse;
import edu.mx.unsis.unsiSmile.service.users.RoleService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/unsismile/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponse> getRoleById(@PathVariable Long id) {
        RoleResponse roleResponse = roleService.getRoleById(id);
        return ResponseEntity.ok(roleResponse);
    }

    @GetMapping
    public ResponseEntity<List<RoleResponse>> getAllRoles() {
        List<RoleResponse> allRoles = roleService.getAllRoles();
        return ResponseEntity.ok(allRoles);
    }
}
