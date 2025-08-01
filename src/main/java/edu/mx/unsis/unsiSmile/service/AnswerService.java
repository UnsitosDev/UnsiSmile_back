package edu.mx.unsis.unsiSmile.service;

import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.dtos.request.forms.answers.AnswerRequest;
import edu.mx.unsis.unsiSmile.dtos.request.forms.answers.AnswerUpdateRequest;
import edu.mx.unsis.unsiSmile.dtos.response.forms.answers.AnswerResponse;
import edu.mx.unsis.unsiSmile.dtos.response.forms.catalogs.CatalogOptionResponse;
import edu.mx.unsis.unsiSmile.dtos.response.files.FileResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.AnswerMapper;
import edu.mx.unsis.unsiSmile.model.forms.answers.AnswerModel;
import edu.mx.unsis.unsiSmile.model.forms.questions.QuestionModel;
import edu.mx.unsis.unsiSmile.model.patients.PatientMedicalRecordModel;
import edu.mx.unsis.unsiSmile.repository.IAnswerRepository;
import edu.mx.unsis.unsiSmile.service.files.FileService;
import edu.mx.unsis.unsiSmile.service.medicalHistories.PatientMedicalRecordService;
import io.jsonwebtoken.lang.Assert;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
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
    private final PatientMedicalRecordService patientMedicalRecordService;

    @Transactional
    public void save(AnswerRequest request) {
        try {
            Assert.notNull(request, "AnswerRequest cannot be null");

            AnswerModel answerModel = answerMapper.toEntity(request);

            PatientMedicalRecordModel patientMedicalRecordModel = patientMedicalRecordService.findById(request.getIdPatientMedicalRecord());

            answerModel.setPatientModel(patientMedicalRecordModel.getPatient());

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
    public List<AnswerResponse> getAnswersByPatientMedicalRecord(Long patientMedicalRecordId) {
        try {
            List<AnswerModel> answerModelList = answerRepository.findAllByPatientMedicalRecordId(patientMedicalRecordId);

            return answerModelList.stream()
                    .map(this::toResponse)
                    .collect(Collectors.toList());

        } catch (Exception ex) {
            throw new AppException("Failed to fetch answers for Patient Medical Record with ID: " + patientMedicalRecordId,
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
    public Map<Long, AnswerResponse> findAllBySectionAndPatientMedicalRecord(List<QuestionModel> questions, String patientId, Long patientMedicalRecordId) {
        try {
            Set<Long> questionIds = questions.stream()
                    .map(QuestionModel::getIdQuestion)
                    .collect(Collectors.toSet());

            List<AnswerModel> answerModelList;

            if (StringUtils.isBlank(patientId) && (patientMedicalRecordId == null || patientMedicalRecordId == 0)) {
                answerModelList = answerRepository.findByQuestion_Ids(questionIds);
            } else {
                answerModelList = (patientMedicalRecordId == null || patientMedicalRecordId == 0)
                        ? answerRepository.findAllByPatientIdAndQuestionIds(questionIds, patientId)
                        : answerRepository.findAllByPatientMedicalRecordIdAndPatientIdAndQuestionIds(questionIds, patientId, patientMedicalRecordId);
            }

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

            Long idPatientMedicalRecord = requests.getFirst().getIdPatientMedicalRecord();
            PatientMedicalRecordModel patientMedicalRecordModel =
                    patientMedicalRecordService.findById(idPatientMedicalRecord);

            List<AnswerModel> savedAnswers = requests.stream()
                    .map(answerMapper::toEntity)
                    .peek(answerModel -> answerModel.setPatientModel(patientMedicalRecordModel.getPatient()))
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

            Long idPatientMedicalRecord = requests.getFirst().getIdPatientMedicalRecord();
            PatientMedicalRecordModel patientMedicalRecordModel =
                    patientMedicalRecordService.findById(idPatientMedicalRecord);

            List<AnswerModel> updatedAnswers = requests.stream()
                    .map(answerMapper::toEntity)
                    .peek(answerModel -> answerModel.setPatientModel(patientMedicalRecordModel.getPatient()))
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
