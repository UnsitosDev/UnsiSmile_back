package edu.mx.unsis.unsiSmile.service.medicalHistories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.OpenAnswerPathologicalRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.OpenAnswerPathologicalResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.OpenAnswerPathologicalMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.MedicalHistoryModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.OpenAnswerPathologicalModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IOpenAnswerPathologicalRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OpenAnswerPathologicalService {

    private final IOpenAnswerPathologicalRepository openAnswerNonPathologicalRepository;
    private final OpenAnswerPathologicalMapper openAnswerNonPathologicalMapper;

    @Transactional
    public OpenAnswerPathologicalResponse createOpenAnswerNonPathological(@NonNull OpenAnswerPathologicalRequest request) {
        try {
            Assert.notNull(request, "OpenAnswerNonPathologicalRequest cannot be null");

            OpenAnswerPathologicalModel model = openAnswerNonPathologicalMapper.toEntity(request);
            OpenAnswerPathologicalModel savedModel = openAnswerNonPathologicalRepository.save(model);

            return openAnswerNonPathologicalMapper.toDto(savedModel);
        } catch (Exception ex) {
            throw new AppException("Failed to create open answer non-pathological", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public OpenAnswerPathologicalResponse getOpenAnswerNonPathologicalById(@NonNull Long id) {
        try {
            OpenAnswerPathologicalModel model = openAnswerNonPathologicalRepository.findById(id)
                    .orElseThrow(() -> new AppException("Open answer non-pathological not found with ID: " + id, HttpStatus.NOT_FOUND));

            return openAnswerNonPathologicalMapper.toDto(model);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch open answer non-pathological", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public List<OpenAnswerPathologicalResponse> getAllOpenAnswersNonPathological() {
        try {
            List<OpenAnswerPathologicalModel> models = openAnswerNonPathologicalRepository.findAll();
            return models.stream()
                    .map(openAnswerNonPathologicalMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException("Failed to fetch all open answers non-pathological", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public OpenAnswerPathologicalResponse updateOpenAnswerNonPathological(@NonNull Long id, @NonNull OpenAnswerPathologicalRequest request) {
        try {
            Assert.notNull(request, "OpenAnswerNonPathologicalRequest cannot be null");

            OpenAnswerPathologicalModel model = openAnswerNonPathologicalRepository.findById(id)
                    .orElseThrow(() -> new AppException("Open answer non-pathological not found with ID: " + id, HttpStatus.NOT_FOUND));

            openAnswerNonPathologicalMapper.updateEntity(request, model);
            OpenAnswerPathologicalModel updatedModel = openAnswerNonPathologicalRepository.save(model);

            return openAnswerNonPathologicalMapper.toDto(updatedModel);
        } catch (Exception ex) {
            throw new AppException("Failed to update open answer non-pathological", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public void deleteOpenAnswerNonPathologicalById(@NonNull Long id) {
        try {
            if (!openAnswerNonPathologicalRepository.existsById(id)) {
                throw new AppException("Open answer non-pathological not found with ID: " + id, HttpStatus.NOT_FOUND);
            }

            openAnswerNonPathologicalRepository.deleteById(id);
        } catch (Exception ex) {
            throw new AppException("Failed to delete open answer non-pathological", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public List<OpenAnswerPathologicalResponse> getOpenAnswersNonPathologicalByMedicalHistory(@NonNull MedicalHistoryModel medicalHistory) {
        try {
            List<OpenAnswerPathologicalModel> models = openAnswerNonPathologicalRepository.findByMedicalHistory(medicalHistory);
            return models.stream()
                    .map(openAnswerNonPathologicalMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException("Failed to fetch open answers non-pathological by medical history", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
