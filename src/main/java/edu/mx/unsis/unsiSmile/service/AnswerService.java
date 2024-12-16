package edu.mx.unsis.unsiSmile.service;

import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.dtos.request.AnswerRequest;
import edu.mx.unsis.unsiSmile.dtos.request.AnswerUpdateRequest;
import edu.mx.unsis.unsiSmile.dtos.response.AnswerResponse;
import edu.mx.unsis.unsiSmile.dtos.response.CatalogOptionResponse;
import edu.mx.unsis.unsiSmile.dtos.response.FileResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.AnswerMapper;
import edu.mx.unsis.unsiSmile.model.AnswerModel;
import edu.mx.unsis.unsiSmile.model.PatientClinicalHistoryModel;
import edu.mx.unsis.unsiSmile.model.QuestionModel;
import edu.mx.unsis.unsiSmile.repository.IAnswerRepository;
import edu.mx.unsis.unsiSmile.service.files.FileService;
import edu.mx.unsis.unsiSmile.service.medicalHistories.PatientClinicalHistoryService;
import io.jsonwebtoken.lang.Assert;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final IAnswerRepository answerRepository;
    private final AnswerMapper answerMapper;
    private final CatalogOptionService optionService;
    private final FileService fileService;
    private final PatientClinicalHistoryService patientClinicalHistoryService;

    @Transactional
    public void save(AnswerRequest request) {
        try {
            Assert.notNull(request, "AnswerRequest cannot be null");

            AnswerModel answerModel = answerMapper.toEntity(request);

            PatientClinicalHistoryModel patientClinicalHistoryModel = patientClinicalHistoryService.findById(request.getIdPatientClinicalHistory());

            answerModel.setPatientModel(patientClinicalHistoryModel.getPatient());

            answerRepository.save(answerModel);
        } catch (Exception ex) {
            throw new AppException("Failed to save answer", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public AnswerResponse findById(Long id) {
        try {
            Assert.notNull(id, "Id cannot be null");

            AnswerModel answerModel = answerRepository.findById(id)
                    .orElseThrow(() -> new AppException("Answer not found with id: " + id, HttpStatus.NOT_FOUND));

            return this.toResponse(answerModel);
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AppException("Failed to find answer with id: " + id, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<AnswerResponse> findAll() {
        try {
            List<AnswerModel> answerModelList = answerRepository.findAll();

            if (answerModelList.isEmpty()) {
                throw new AppException("No answers found", HttpStatus.NOT_FOUND);
            } else {
                return answerModelList.stream()
                        .map(this::toResponse)
                        .collect(Collectors.toList());
            }
        } catch (Exception ex) {
            throw new AppException("Failed to fetch answers", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteById(Long id) {
        try {
            Optional<AnswerModel> answerOptional = answerRepository.findById(id);

            answerOptional.ifPresentOrElse(
                    answer -> {
                        answer.setStatusKey(Constants.INACTIVE);
                        answerRepository.save(answer);
                    },
                    () -> {
                        throw new AppException("Answer not found with ID: " + id, HttpStatus.NOT_FOUND);
                    }
            );
        } catch (Exception ex) {
            throw new AppException("Failed to delete answer with ID: " + id, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<AnswerResponse> getAnswersByPatientClinicalHistory(Long patientClinicalHistoryId) {
        try {
            List<AnswerModel> answerModelList = answerRepository.findAllByPatientClinicalHistoryId(patientClinicalHistoryId);

            return answerModelList.stream()
                    .map(this::toResponse)
                    .collect(Collectors.toList());

        } catch (Exception ex) {
            throw new AppException("Failed to fetch answers for Patient Clinical History with ID: " + patientClinicalHistoryId,
                    HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    private AnswerResponse toResponse(AnswerModel answerModel) {
        AnswerResponse answerResponse = answerMapper.toDto(answerModel);
        if (answerModel.getCatalogOptionModel() != null){
            CatalogOptionResponse optionResponse = optionService.findById(answerModel.getCatalogOptionModel().getIdCatalogOption());
            answerResponse.setAnswerCatalogOption(optionResponse);
        }else if(answerModel.getIsFile() != null && answerModel.getIsFile()){
            List<FileResponse> files = fileService.getFilesByAnswer(answerModel.getIdAnswer());
            answerResponse.setFiles(files);
        }
        return answerResponse;
    }

    @Transactional(readOnly = true)
    public Map<Long, AnswerResponse> findAllBySectionAndPatientClinicalHistory(List<QuestionModel> questions, String patientId) {
        try {
            Set<Long> questionIds = questions.stream()
                    .map(QuestionModel::getIdQuestion)
                    .collect(Collectors.toSet());

            List<AnswerModel> answerModelList = answerRepository.findAllByPatientClinicalHistoryId(questionIds, patientId);

            Map<Long, AnswerResponse> answerMap = new HashMap<>();

            for (AnswerModel answerModel : answerModelList) {
                AnswerResponse answerResponse = toResponse(answerModel);
                answerMap.put(answerModel.getQuestionModel().getIdQuestion(), answerResponse);
            }

            return answerMap;
        } catch (Exception ex){
            throw new AppException("Failed to fetch answers for one Section with patientId: " +
                    patientId, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void saveBatch(List<AnswerRequest> requests) {
        try {
            Assert.notNull(requests, "AnswerRequest list cannot be null");
            if (requests.isEmpty()) {
                throw new AppException("AnswerRequest list cannot be empty", HttpStatus.BAD_REQUEST);
            }

            Long idPatientClinicalHistory = requests.getFirst().getIdPatientClinicalHistory();
            PatientClinicalHistoryModel patientClinicalHistoryModel =
                    patientClinicalHistoryService.findById(idPatientClinicalHistory);

            List<AnswerModel> savedAnswers = requests.stream()
                    .map(answerMapper::toEntity)
                    .peek(answerModel -> answerModel.setPatientModel(patientClinicalHistoryModel.getPatient()))
                    .map(answerRepository::save)
                    .toList();

            if (savedAnswers.isEmpty()) {
                throw new AppException("No answers were saved", HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (Exception ex) {
            throw new AppException("Failed to save batch of answers", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void updateBatch(List<AnswerUpdateRequest> requests) {
        try {
            Assert.notNull(requests, "AnswerRequest list cannot be null");
            if (requests.isEmpty()) {
                throw new AppException("AnswerRequest list cannot be empty", HttpStatus.BAD_REQUEST);
            }

            Long idPatientClinicalHistory = requests.getFirst().getIdPatientClinicalHistory();
            PatientClinicalHistoryModel patientClinicalHistoryModel =
                    patientClinicalHistoryService.findById(idPatientClinicalHistory);

            List<AnswerModel> updatedAnswers = requests.stream()
                    .map(answerMapper::toEntity)
                    .peek(answerModel -> answerModel.setPatientModel(patientClinicalHistoryModel.getPatient()))
                    .map(answerRepository::save)
                    .toList();

            if (updatedAnswers.isEmpty()) {
                throw new AppException("No answers were updated", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception ex) {
            throw new AppException("Failed to update batch of answers", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}
