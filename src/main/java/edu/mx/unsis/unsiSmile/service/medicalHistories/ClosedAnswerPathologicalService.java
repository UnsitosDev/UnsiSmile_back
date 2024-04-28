package edu.mx.unsis.unsiSmile.service.medicalHistories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.ClosedAnswerPathologicalRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.ClosedAnswerPathologicalResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.ClosedAnswerPathologicalMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.ClosedAnswerPathologicalModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.MedicalHistoryModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IClosedAnswerPathologicalRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClosedAnswerPathologicalService {

    private final IClosedAnswerPathologicalRepository closedAnswerNonPathologicalRepository;
    private final ClosedAnswerPathologicalMapper closedAnswerNonPathologicalMapper;

    @Transactional
    public ClosedAnswerPathologicalResponse createClosedAnswerNonPathological(@NonNull ClosedAnswerPathologicalRequest request) {
        try {
            Assert.notNull(request, "ClosedAnswerNonPathologicalRequest cannot be null");

            ClosedAnswerPathologicalModel model = closedAnswerNonPathologicalMapper.toEntity(request);
            ClosedAnswerPathologicalModel savedModel = closedAnswerNonPathologicalRepository.save(model);

            return closedAnswerNonPathologicalMapper.toDto(savedModel);
        } catch (Exception ex) {
            throw new AppException("Failed to create closed answer non-pathological", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public ClosedAnswerPathologicalResponse getClosedAnswerNonPathologicalById(@NonNull Long id) {
        try {
            ClosedAnswerPathologicalModel model = closedAnswerNonPathologicalRepository.findById(id)
                    .orElseThrow(() -> new AppException("Closed answer non-pathological not found with ID: " + id, HttpStatus.NOT_FOUND));

            return closedAnswerNonPathologicalMapper.toDto(model);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch closed answer non-pathological", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public List<ClosedAnswerPathologicalResponse> getAllClosedAnswersNonPathological() {
        try {
            List<ClosedAnswerPathologicalModel> models = closedAnswerNonPathologicalRepository.findAll();
            return models.stream()
                    .map(closedAnswerNonPathologicalMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException("Failed to fetch all closed answers non-pathological", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ClosedAnswerPathologicalResponse updateClosedAnswerNonPathological(@NonNull Long id, @NonNull ClosedAnswerPathologicalRequest request) {
        try {
            Assert.notNull(request, "ClosedAnswerNonPathologicalRequest cannot be null");

            ClosedAnswerPathologicalModel model = closedAnswerNonPathologicalRepository.findById(id)
                    .orElseThrow(() -> new AppException("Closed answer non-pathological not found with ID: " + id, HttpStatus.NOT_FOUND));

            closedAnswerNonPathologicalMapper.updateEntity(request, model);
            ClosedAnswerPathologicalModel updatedModel = closedAnswerNonPathologicalRepository.save(model);

            return closedAnswerNonPathologicalMapper.toDto(updatedModel);
        } catch (Exception ex) {
            throw new AppException("Failed to update closed answer non-pathological", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public void deleteClosedAnswerNonPathologicalById(@NonNull Long id) {
        try {
            if (!closedAnswerNonPathologicalRepository.existsById(id)) {
                throw new AppException("Closed answer non-pathological not found with ID: " + id, HttpStatus.NOT_FOUND);
            }

            closedAnswerNonPathologicalRepository.deleteById(id);
        } catch (Exception ex) {
            throw new AppException("Failed to delete closed answer non-pathological", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public List<ClosedAnswerPathologicalResponse> getClosedAnswersNonPathologicalByMedicalHistory(@NonNull MedicalHistoryModel medicalHistory) {
        try {
            List<ClosedAnswerPathologicalModel> models = closedAnswerNonPathologicalRepository.findByMedicalHistory(medicalHistory);
            return models.stream()
                    .map(closedAnswerNonPathologicalMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException("Failed to fetch closed answers non-pathological by medical history", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
