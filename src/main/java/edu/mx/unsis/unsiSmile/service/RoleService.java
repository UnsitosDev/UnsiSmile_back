package edu.mx.unsis.unsiSmile.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mx.unsis.unsiSmile.authenticationProviders.model.RoleModel;
import edu.mx.unsis.unsiSmile.dtos.response.RoleResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.RoleMapper;
import edu.mx.unsis.unsiSmile.repository.IRoleRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final IRoleRepository roleRepository;
    private final RoleMapper roleMapper;



    @Transactional(readOnly = true)
    public RoleResponse getRoleById(Long id) {
        try {
            RoleModel roleModel = roleRepository.findById(id)
                    .orElseThrow(() -> new AppException("Role not found with ID: " + id, HttpStatus.NOT_FOUND));
            return roleMapper.toDto(roleModel);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch role", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<RoleResponse> getAllRoles() {
        try {
            List<RoleModel> allRoles = roleRepository.findAll();
            return roleMapper.toDtos(allRoles);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch roles", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    
}
