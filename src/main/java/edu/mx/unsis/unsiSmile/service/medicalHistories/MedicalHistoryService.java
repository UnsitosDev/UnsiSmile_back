package edu.mx.unsis.unsiSmile.service.medicalHistories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.MedicalHistoryRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.MedicalHistoryResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.MedicalHistoryMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.MedicalHistoryModel;
import edu.mx.unsis.unsiSmile.model.patients.PatientModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IMedicalHistoryRepository;
import edu.mx.unsis.unsiSmile.service.patients.PatientService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedicalHistoryService {

    private final IMedicalHistoryRepository medicalHistoryRepository;
    private final MedicalHistoryMapper medicalHistoryMapper;
    private final PatientService patientService;

    @Transactional
    public MedicalHistoryResponse createMedicalHistory(@NonNull MedicalHistoryRequest request) {
        try {
            Assert.notNull(request, "MedicalHistoryRequest cannot be null");

            MedicalHistoryModel model = medicalHistoryMapper.toEntity(request);
            MedicalHistoryModel savedModel = medicalHistoryRepository.save(model);

            return medicalHistoryMapper.toDto(savedModel);
        } catch (Exception ex) {
            throw new AppException("Failed to create medical history", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public MedicalHistoryResponse getMedicalHistoryById(@NonNull Long id) {
        try {
            MedicalHistoryModel model = medicalHistoryRepository.findById(id)
                    .orElseThrow(() -> new AppException("Medical history not found with ID: " + id, HttpStatus.NOT_FOUND));

            return medicalHistoryMapper.toDto(model);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch medical history", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public List<MedicalHistoryResponse> getAllMedicalHistories() {
        try {
            List<MedicalHistoryModel> allModels = medicalHistoryRepository.findAll();
            return allModels.stream()
                    .map(medicalHistoryMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException("Failed to fetch all medical histories", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public MedicalHistoryResponse updateMedicalHistory(@NonNull Long id, @NonNull MedicalHistoryRequest request) {
        try {
            Assert.notNull(request, "Updated MedicalHistoryRequest cannot be null");

            MedicalHistoryModel existingModel = medicalHistoryRepository.findById(id)
                    .orElseThrow(() -> new AppException("Medical history not found with ID: " + id, HttpStatus.NOT_FOUND));

            MedicalHistoryModel updatedModel = medicalHistoryMapper.toEntity(request);
            updatedModel.setIdMedicalHistory(existingModel.getIdMedicalHistory()); // Ensure ID consistency

            MedicalHistoryModel savedModel = medicalHistoryRepository.save(updatedModel);

            return medicalHistoryMapper.toDto(savedModel);
        } catch (Exception ex) {
            throw new AppException("Failed to update medical history", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public void deleteMedicalHistory(@NonNull Long id) {
        try {
            if (!medicalHistoryRepository.existsById(id)) {
                throw new AppException("Medical history not found with ID: " + id, HttpStatus.NOT_FOUND);
            }
            medicalHistoryRepository.deleteById(id);
        } catch (Exception ex) {
            throw new AppException("Failed to delete medical history", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @Transactional(readOnly = true)
    public MedicalHistoryModel getMedicalHistoryModel(Long idPatient) {
        PatientModel patientModel = patientService.getPatientModel(idPatient);
        try {
            return medicalHistoryRepository.findById(patientModel.getMedicalHistory().getIdMedicalHistory())
                    .orElseThrow(() -> new AppException("Medical history not found for patient with ID: " + idPatient,
                            HttpStatus.NOT_FOUND));
        } catch (Exception ex) {
            throw new AppException("Failed to fetch medical history for patient with ID: " + idPatient,
                    HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}
