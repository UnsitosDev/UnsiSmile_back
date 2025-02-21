package edu.mx.unsis.unsiSmile.service.professors;

import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.dtos.request.professors.ProfessorClinicalAreaRequest;
import edu.mx.unsis.unsiSmile.dtos.response.professors.ProfessorClinicalAreaResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.professors.ProfessorClinicalAreaMapper;
import edu.mx.unsis.unsiSmile.model.professors.ProfessorClinicalAreaModel;
import edu.mx.unsis.unsiSmile.repository.professors.IProfessorClinicalAreaRepository;
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
    private final ProfessorClinicalAreaMapper professorClinicalAreaMapper;

    @Transactional
    public void createProfessorClinicalArea(@NotNull ProfessorClinicalAreaRequest request) {
        try {
            ProfessorClinicalAreaModel professorClinicalAreaModel = professorClinicalAreaMapper.toEntity(request);
            professorClinicalAreaRepository.save(professorClinicalAreaModel);
        } catch (Exception e) {
            throw new RuntimeException("Fail to create professor clinical area", e);
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
}
