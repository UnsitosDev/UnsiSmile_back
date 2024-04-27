package edu.mx.unsis.unsiSmile.service.medicalHistories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.OpenQuestionPathologicalAntecedentsRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.OpenQuestionPathologicalAntecedentsResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.OpenQuestionPathologicalAntecedentsMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.OpenQuestionsPathologicalAntecedentsModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IOpenQuestionsPathologicalAntecedentsRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OpenQuestionsPathologicalAntecedentsService {

    private final IOpenQuestionsPathologicalAntecedentsRepository openQuestionsRepository;
    private final OpenQuestionPathologicalAntecedentsMapper mapper;

    @Transactional
    public OpenQuestionPathologicalAntecedentsResponse createOpenQuestion(@NonNull OpenQuestionPathologicalAntecedentsRequest request) {
        try {
            Assert.notNull(request, "OpenQuestionsPathologicalAntecedentsRequest cannot be null");

            OpenQuestionsPathologicalAntecedentsModel model = mapper.toEntity(request);
            OpenQuestionsPathologicalAntecedentsModel savedModel = openQuestionsRepository.save(model);

            return mapper.toDto(savedModel);
        } catch (Exception ex) {
            throw new AppException("Failed to create open question", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public OpenQuestionPathologicalAntecedentsResponse getOpenQuestionById(@NonNull Long id) {
        try {
            OpenQuestionsPathologicalAntecedentsModel model = openQuestionsRepository.findById(id)
                    .orElseThrow(() -> new AppException("Open question not found with ID: " + id, HttpStatus.NOT_FOUND));
            return mapper.toDto(model);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch open question", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<OpenQuestionPathologicalAntecedentsResponse> getAllOpenQuestions() {
        try {
            List<OpenQuestionsPathologicalAntecedentsModel> allModels = openQuestionsRepository.findAll();
            return allModels.stream()
                    .map(mapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException("Failed to fetch open questions", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public OpenQuestionPathologicalAntecedentsResponse updateOpenQuestion(@NonNull Long id, @NonNull OpenQuestionPathologicalAntecedentsRequest updatedRequest) {
        try {
            Assert.notNull(updatedRequest, "Updated OpenQuestionsPathologicalAntecedentsRequest cannot be null");

            OpenQuestionsPathologicalAntecedentsModel model = openQuestionsRepository.findById(id)
                    .orElseThrow(() -> new AppException("Open question not found with ID: " + id, HttpStatus.NOT_FOUND));

            mapper.updateEntity(updatedRequest, model);

            OpenQuestionsPathologicalAntecedentsModel updatedModel = openQuestionsRepository.save(model);

            return mapper.toDto(updatedModel);
        } catch (Exception ex) {
            throw new AppException("Failed to update open question", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteOpenQuestion(@NonNull Long id) {
        try {
            if (!openQuestionsRepository.existsById(id)) {
                throw new AppException("Open question not found with ID: " + id, HttpStatus.NOT_FOUND);
            }
            openQuestionsRepository.deleteById(id);
        } catch (Exception ex) {
            throw new AppException("Failed to delete open question", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}
