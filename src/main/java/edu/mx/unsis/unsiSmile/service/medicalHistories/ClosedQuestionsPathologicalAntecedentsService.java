package edu.mx.unsis.unsiSmile.service.medicalHistories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.ClosedQuestionPathologicalAntecedentsRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.ClosedQuestionPathologicalAntecedentsResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.ClosedQuestionsPathologicalAntecedentsMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.ClosedQuestionsPathologicalAntecedentsModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IClosedQuestionsPathologicalAntecedentsRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClosedQuestionsPathologicalAntecedentsService {

    private final IClosedQuestionsPathologicalAntecedentsRepository closedQuestionsRepository;
    private final ClosedQuestionsPathologicalAntecedentsMapper mapper;

    @Transactional
    public ClosedQuestionPathologicalAntecedentsResponse createClosedQuestion(@NonNull ClosedQuestionPathologicalAntecedentsRequest request) {
        try {
            Assert.notNull(request, "ClosedQuestionsPathologicalAntecedentsRequest cannot be null");

            ClosedQuestionsPathologicalAntecedentsModel model = mapper.toEntity(request);
            ClosedQuestionsPathologicalAntecedentsModel savedModel = closedQuestionsRepository.save(model);

            return mapper.toDto(savedModel);
        } catch (Exception ex) {
            throw new AppException("Failed to create closed question", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public ClosedQuestionPathologicalAntecedentsResponse getClosedQuestionById(@NonNull Long id) {
        try {
            ClosedQuestionsPathologicalAntecedentsModel model = closedQuestionsRepository.findById(id)
                    .orElseThrow(() -> new AppException("Closed question not found with ID: " + id, HttpStatus.NOT_FOUND));
            return mapper.toDto(model);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch closed question", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<ClosedQuestionPathologicalAntecedentsResponse> getAllClosedQuestions() {
        try {
            List<ClosedQuestionsPathologicalAntecedentsModel> allModels = closedQuestionsRepository.findAll();
            return allModels.stream()
                    .map(mapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException("Failed to fetch closed questions", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public ClosedQuestionPathologicalAntecedentsResponse updateClosedQuestion(@NonNull Long id, @NonNull ClosedQuestionPathologicalAntecedentsRequest updatedRequest) {
        try {
            Assert.notNull(updatedRequest, "Updated ClosedQuestionsPathologicalAntecedentsRequest cannot be null");

            ClosedQuestionsPathologicalAntecedentsModel model = closedQuestionsRepository.findById(id)
                    .orElseThrow(() -> new AppException("Closed question not found with ID: " + id, HttpStatus.NOT_FOUND));

            mapper.updateEntity(updatedRequest, model);

            ClosedQuestionsPathologicalAntecedentsModel updatedModel = closedQuestionsRepository.save(model);

            return mapper.toDto(updatedModel);
        } catch (Exception ex) {
            throw new AppException("Failed to update closed question", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteClosedQuestion(@NonNull Long id) {
        try {
            if (!closedQuestionsRepository.existsById(id)) {
                throw new AppException("Closed question not found with ID: " + id, HttpStatus.NOT_FOUND);
            }
            closedQuestionsRepository.deleteById(id);
        } catch (Exception ex) {
            throw new AppException("Failed to delete closed question", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}
