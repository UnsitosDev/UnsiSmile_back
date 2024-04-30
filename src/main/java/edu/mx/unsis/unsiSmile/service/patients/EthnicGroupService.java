package edu.mx.unsis.unsiSmile.service.patients;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import edu.mx.unsis.unsiSmile.dtos.request.patients.EthnicGroupRequest;
import edu.mx.unsis.unsiSmile.dtos.response.patients.EthnicGroupResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.patients.EthnicGroupMapper;
import edu.mx.unsis.unsiSmile.model.patients.EthnicGroupModel;
import edu.mx.unsis.unsiSmile.repository.patients.IEthnicGroupRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EthnicGroupService {

    private final IEthnicGroupRepository ethnicGroupRepository;
    private final EthnicGroupMapper ethnicGroupMapper;

    @Transactional
    public EthnicGroupResponse createEthnicGroup(@NonNull EthnicGroupRequest ethnicGroupRequest) {
        try {
            Assert.notNull(ethnicGroupRequest, "EthnicGroupRequest cannot be null");

            // Map the DTO request to the entity
            EthnicGroupModel ethnicGroupModel = ethnicGroupMapper.toEntity(ethnicGroupRequest);

            // Save the entity to the database
            EthnicGroupModel savedEthnicGroup = ethnicGroupRepository.save(ethnicGroupModel);

            // Map the saved entity back to a response DTO
            return ethnicGroupMapper.toDto(savedEthnicGroup);
        } catch (Exception ex) {
            throw new AppException("Failed to create ethnic group", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public EthnicGroupResponse getEthnicGroupById(@NonNull Long idEthnicGroup) {
        try {
            EthnicGroupModel ethnicGroupModel = ethnicGroupRepository.findByIdEthnicGroup(idEthnicGroup)
                    .orElseThrow(() -> new AppException("Ethnic group not found with ID: " + idEthnicGroup, HttpStatus.NOT_FOUND));

            return ethnicGroupMapper.toDto(ethnicGroupModel);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch ethnic group by ID", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public EthnicGroupResponse getEthnicGroupByName(@NonNull String ethnicGroup) {
        try {
            EthnicGroupModel ethnicGroupModel = ethnicGroupRepository.findByEthnicGroup(ethnicGroup)
                    .orElseThrow(() -> new AppException("Ethnic group not found with name: " + ethnicGroup, HttpStatus.NOT_FOUND));

            return ethnicGroupMapper.toDto(ethnicGroupModel);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch ethnic group by name", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<EthnicGroupResponse> getAllEthnicGroups() {
        try {
            List<EthnicGroupModel> allEthnicGroups = ethnicGroupRepository.findAll();
            return allEthnicGroups.stream()
                    .map(ethnicGroupMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException("Failed to fetch all ethnic groups", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public EthnicGroupResponse updateEthnicGroup(@NonNull Long idEthnicGroup, @NonNull EthnicGroupRequest updatedEthnicGroupRequest) {
        try {
            Assert.notNull(updatedEthnicGroupRequest, "Updated EthnicGroupRequest cannot be null");

            // Find the ethnic group in the database
            EthnicGroupModel ethnicGroupModel = ethnicGroupRepository.findByIdEthnicGroup(idEthnicGroup)
                    .orElseThrow(() -> new AppException("Ethnic group not found with ID: " + idEthnicGroup, HttpStatus.NOT_FOUND));

            // Update the ethnic group entity with the new data
            ethnicGroupMapper.updateEntity(updatedEthnicGroupRequest, ethnicGroupModel);

            // Save the updated entity
            EthnicGroupModel updatedEthnicGroup = ethnicGroupRepository.save(ethnicGroupModel);

            // Map the updated entity back to a response DTO
            return ethnicGroupMapper.toDto(updatedEthnicGroup);
        } catch (Exception ex) {
            throw new AppException("Failed to update ethnic group", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteEthnicGroupById(@NonNull Long idEthnicGroup) {
        try {
            // Check if the ethnic group exists
            if (!ethnicGroupRepository.existsById(idEthnicGroup)) {
                throw new AppException("Ethnic group not found with ID: " + idEthnicGroup, HttpStatus.NOT_FOUND);
            }
            ethnicGroupRepository.deleteById(idEthnicGroup);
        } catch (Exception ex) {
            throw new AppException("Failed to delete ethnic group", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}
