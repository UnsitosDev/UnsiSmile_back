package edu.mx.unsis.unsiSmile.service.forms.answers;

import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.dtos.request.forms.answers.AnswerTypeRequest;
import edu.mx.unsis.unsiSmile.dtos.response.forms.answers.AnswerTypeResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.forms.answers.AnswerTypeMapper;
import edu.mx.unsis.unsiSmile.model.forms.answers.AnswerTypeModel;
import edu.mx.unsis.unsiSmile.repository.forms.answers.IAnswerTypeRepository;
import io.jsonwebtoken.lang.Assert;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnswerTypeService {

    private final IAnswerTypeRepository answerTypeRepository;
    private final AnswerTypeMapper answerTypeMapper;

    @Transactional
    public void save(AnswerTypeRequest request) {
        try {
            Assert.notNull(request, "AnswerTypeRequest cannot be null");

            AnswerTypeModel answerTypeModel = answerTypeMapper.toEntity(request);

            answerTypeRepository.save(answerTypeModel);
        } catch (Exception ex) {
            throw new AppException("Failed to save answer type", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public AnswerTypeResponse findById(Long id) {
        try {
            Assert.notNull(id, "Id cannot be null");

            AnswerTypeModel answerTypeModel = answerTypeRepository.findById(id)
                    .orElseThrow(() -> new AppException("Answer type not found with id: " + id, HttpStatus.NOT_FOUND));

            return answerTypeMapper.toDto(answerTypeModel);
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AppException("Failed to find answer type with id: " + id, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<AnswerTypeResponse> findAll() {
        try {
            List<AnswerTypeModel> answerTypeModelList = answerTypeRepository.findAll();

            if (answerTypeModelList.isEmpty()) {
                throw new AppException("No answer types found", HttpStatus.NOT_FOUND);
            } else {
                return answerTypeModelList.stream()
                        .map(answerTypeMapper::toDto)
                        .collect(Collectors.toList());
            }
        } catch (Exception ex) {
            throw new AppException("Failed to fetch answer types", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteById(Long id) {
        try {
            Optional<AnswerTypeModel> answerTypeOptional = answerTypeRepository.findById(id);

            answerTypeOptional.ifPresentOrElse(
                    answerType -> {
                        answerType.setStatusKey(Constants.INACTIVE);
                        answerTypeRepository.save(answerType);
                    },
                    () -> {
                        throw new AppException("Answer type not found with ID: " + id, HttpStatus.NOT_FOUND);
                    }
            );
        } catch (Exception ex) {
            throw new AppException("Failed to delete answer type with ID: " + id, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}