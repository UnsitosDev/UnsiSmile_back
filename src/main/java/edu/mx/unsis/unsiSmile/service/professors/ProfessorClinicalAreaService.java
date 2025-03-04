package edu.mx.unsis.unsiSmile.service.professors;

import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.request.professors.ProfessorClinicalAreaRequest;
import edu.mx.unsis.unsiSmile.dtos.response.professors.ProfessorClinicalAreaResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.professors.ProfessorClinicalAreaMapper;
import edu.mx.unsis.unsiSmile.model.professors.ProfessorClinicalAreaModel;
import edu.mx.unsis.unsiSmile.model.professors.ProfessorModel;
import edu.mx.unsis.unsiSmile.repository.professors.IClinicalAreaRepository;
import edu.mx.unsis.unsiSmile.repository.professors.IProfessorClinicalAreaRepository;
import edu.mx.unsis.unsiSmile.repository.professors.IProfessorRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfessorClinicalAreaService {
    private final IProfessorClinicalAreaRepository professorClinicalAreaRepository;
    private final IProfessorRepository professorRepository;
    private final IClinicalAreaRepository clinicalAreaRepository;
    private final ProfessorClinicalAreaMapper professorClinicalAreaMapper;

    @Transactional
    public void createProfessorClinicalArea(@NotNull ProfessorClinicalAreaRequest request) {
        try {
            if (!professorRepository.existsById(request.getIdProfessor())) {
                throw new AppException(String.format(ResponseMessages.PROFESSOR_NOT_FOUND, request.getIdProfessor()),
                        HttpStatus.NOT_FOUND);
            }

            if (!clinicalAreaRepository.existsById(request.getIdClinicalArea())) {
                throw new AppException(String.format(ResponseMessages.CLINICAL_AREA_NOT_FOUND, request.getIdClinicalArea()),
                        HttpStatus.NOT_FOUND);
            }

            ProfessorClinicalAreaModel professorClinicalAreaModel = professorClinicalAreaMapper.toEntity(request);
            professorClinicalAreaRepository.save(professorClinicalAreaModel);

        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(ResponseMessages.ERROR_CREATING_RELATION, e);
        }
    }

    @Transactional
    public ProfessorClinicalAreaResponse getProfessorClinicalAreaById(@NotNull Long id) {
        try {
            ProfessorClinicalAreaModel professorClinicalAreaModel = professorClinicalAreaRepository.findById(id)
                    .orElseThrow(() -> new AppException("Professor clinical area not found", HttpStatus.NOT_FOUND));
            return professorClinicalAreaMapper.toDto(professorClinicalAreaModel);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Fail to get professor clinical area by id", e);
        }
    }

    @Transactional
    public Page<ProfessorClinicalAreaResponse> getAllProfessorClinicalAreas(Pageable pageable, String keyword) {
        try {
            Page<ProfessorClinicalAreaModel> professorClinicalAreas;
            if (keyword == null || keyword.isEmpty()) {
                professorClinicalAreas = professorClinicalAreaRepository.findAll(pageable);
            } else {
                professorClinicalAreas = professorClinicalAreaRepository.findAllBySearchInput(keyword, pageable);
            }
            return professorClinicalAreas.map(professorClinicalAreaMapper::toDto);
        } catch (Exception e) {
            throw new RuntimeException("Fail to get all professor clinical areas", e);
        }
    }

    @Transactional
    public void updateProfessorClinicalArea(@NotNull ProfessorClinicalAreaRequest request) {
        try {
            ProfessorClinicalAreaModel professorClinicalAreaModel = professorClinicalAreaRepository.findById(request.getIdProfessorClinicalArea())
                    .orElseThrow(() -> new AppException("Professor clinical area not found", HttpStatus.NOT_FOUND));
            professorClinicalAreaMapper.updateEntity(request, professorClinicalAreaModel);
            professorClinicalAreaRepository.save(professorClinicalAreaModel);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Fail to update professor clinical area", e);
        }
    }

    @Transactional
    public void deleteProfessorClinicalArea(@NotNull Long id) {
        try {
            ProfessorClinicalAreaModel professorClinicalAreaModel = professorClinicalAreaRepository.findById(id)
                    .orElseThrow(() -> new AppException("Professor clinical area not found", HttpStatus.NOT_FOUND));
            professorClinicalAreaModel.setStatusKey(Constants.INACTIVE);
            professorClinicalAreaRepository.save(professorClinicalAreaModel);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Fail to delete professor clinical area", e);
        }
    }

    @Transactional
    public void toggleProfessorClinicalAreaStatus(@NotNull Long id) {
        try {
            ProfessorClinicalAreaModel professorClinicalAreaModel = professorClinicalAreaRepository.findById(id)
                    .orElseThrow(() -> new AppException("Professor clinical area not found", HttpStatus.NOT_FOUND));

            ProfessorModel professor = professorClinicalAreaModel.getProfessor();

            if (!Constants.ACTIVE.equals(professor.getStatusKey())) {
                throw new AppException("Cannot enable clinical area because the professor is inactive.", HttpStatus.BAD_REQUEST);
            }

            String newStatus = Constants.ACTIVE.equals(professorClinicalAreaModel.getStatusKey())
                    ? Constants.INACTIVE : Constants.ACTIVE;
            professorClinicalAreaModel.setStatusKey(newStatus);
            professorClinicalAreaRepository.save(professorClinicalAreaModel);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Fail to enable professor clinical area", e);
        }
    }
}
