package edu.mx.unsis.unsiSmile.service.medicalHistories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.NonPathologicalPersonalAntecedentsRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.NonPathologicalPersonalAntecedentsResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.NonPathologicalPersonalAntecedentsMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.NonPathologicalPersonalAntecedentsModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.INonPathologicalPersonalAntecedentsRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NonPathologicalPersonalAntecedentsService {

    private final INonPathologicalPersonalAntecedentsRepository nonPathologicalPersonalAntecedentsRepository;
    private final NonPathologicalPersonalAntecedentsMapper nonPathologicalPersonalAntecedentsMapper;

    @Transactional
    public NonPathologicalPersonalAntecedentsResponse createNonPathologicalPersonalAntecedents(@NonNull NonPathologicalPersonalAntecedentsRequest nonPathologicalPersonalAntecedentsRequest) {
        try {
            Assert.notNull(nonPathologicalPersonalAntecedentsRequest, "NonPathologicalPersonalAntecedentsRequest cannot be null");

            NonPathologicalPersonalAntecedentsModel nonPathologicalPersonalAntecedentsModel = nonPathologicalPersonalAntecedentsMapper.toEntity(nonPathologicalPersonalAntecedentsRequest);
            NonPathologicalPersonalAntecedentsModel savedNonPathologicalPersonalAntecedents = nonPathologicalPersonalAntecedentsRepository.save(nonPathologicalPersonalAntecedentsModel);

            return nonPathologicalPersonalAntecedentsMapper.toDto(savedNonPathologicalPersonalAntecedents);
        } catch (Exception ex) {
            throw new AppException("Failed to create non-pathological personal antecedents", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public NonPathologicalPersonalAntecedentsResponse getNonPathologicalPersonalAntecedentsById(@NonNull Long id) {
        try {
            NonPathologicalPersonalAntecedentsModel nonPathologicalPersonalAntecedentsModel = nonPathologicalPersonalAntecedentsRepository.findById(id)
                    .orElseThrow(() -> new AppException("Non-pathological personal antecedents not found with ID: " + id, HttpStatus.NOT_FOUND));

            return nonPathologicalPersonalAntecedentsMapper.toDto(nonPathologicalPersonalAntecedentsModel);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch non-pathological personal antecedents", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<NonPathologicalPersonalAntecedentsResponse> getAllNonPathologicalPersonalAntecedents() {
        try {
            List<NonPathologicalPersonalAntecedentsModel> allNonPathologicalPersonalAntecedents = nonPathologicalPersonalAntecedentsRepository.findAll();
            return allNonPathologicalPersonalAntecedents.stream()
                    .map(nonPathologicalPersonalAntecedentsMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException("Failed to fetch all non-pathological personal antecedents", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public NonPathologicalPersonalAntecedentsResponse updateNonPathologicalPersonalAntecedents(@NonNull Long id, @NonNull NonPathologicalPersonalAntecedentsRequest updatedNonPathologicalPersonalAntecedentsRequest) {
        try {
            Assert.notNull(updatedNonPathologicalPersonalAntecedentsRequest, "Updated NonPathologicalPersonalAntecedentsRequest cannot be null");

            NonPathologicalPersonalAntecedentsModel existingNonPathologicalPersonalAntecedents = nonPathologicalPersonalAntecedentsRepository.findById(id)
                    .orElseThrow(() -> new AppException("Non-pathological personal antecedents not found with ID: " + id, HttpStatus.NOT_FOUND));

            NonPathologicalPersonalAntecedentsModel updatedNonPathologicalPersonalAntecedents = nonPathologicalPersonalAntecedentsMapper.toEntity(updatedNonPathologicalPersonalAntecedentsRequest);
            updatedNonPathologicalPersonalAntecedents.setIdNonPathologicalPersonalAntecedents(existingNonPathologicalPersonalAntecedents.getIdNonPathologicalPersonalAntecedents());

            NonPathologicalPersonalAntecedentsModel savedNonPathologicalPersonalAntecedents = nonPathologicalPersonalAntecedentsRepository.save(updatedNonPathologicalPersonalAntecedents);
            return nonPathologicalPersonalAntecedentsMapper.toDto(savedNonPathologicalPersonalAntecedents);
        } catch (Exception ex) {
            throw new AppException("Failed to update non-pathological personal antecedents", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteNonPathologicalPersonalAntecedents(@NonNull Long id) {
        try {
            if (!nonPathologicalPersonalAntecedentsRepository.existsById(id)) {
                throw new AppException("Non-pathological personal antecedents not found with ID: " + id, HttpStatus.NOT_FOUND);
            }
            nonPathologicalPersonalAntecedentsRepository.deleteById(id);
        } catch (Exception ex) {
            throw new AppException("Failed to delete non-pathological personal antecedents", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}
