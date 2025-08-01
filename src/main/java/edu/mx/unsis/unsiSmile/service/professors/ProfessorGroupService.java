package edu.mx.unsis.unsiSmile.service.professors;

import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.dtos.request.professors.ProfessorGroupRequest;
import edu.mx.unsis.unsiSmile.dtos.response.professors.ProfessorGroupResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.professors.ProfessorGroupMapper;
import edu.mx.unsis.unsiSmile.model.professors.ProfessorGroupModel;
import edu.mx.unsis.unsiSmile.repository.professors.IProfessorGroupRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfessorGroupService {

    private final IProfessorGroupRepository professorGroupRepository;
    private final ProfessorGroupMapper professorGroupMapper;

    @Transactional
    public void createProfessorGroup(@NotNull ProfessorGroupRequest request) {
        try {
            ProfessorGroupModel professorGroupModel = professorGroupMapper.toEntity(request);
            professorGroupRepository.save(professorGroupModel);
        } catch (Exception e) {
            throw new RuntimeException("Fail to create professor group", e);
        }
    }

    @Transactional(readOnly = true)
    public ProfessorGroupResponse getProfessorGroupResponse(@NotNull Long id) {
        try {
            ProfessorGroupModel professorGroupModel = professorGroupRepository.findById(id)
                    .orElseThrow(() -> new AppException("Porfessor group not found", HttpStatus.NOT_FOUND));
            return professorGroupMapper.toDto(professorGroupModel);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Fail to get professor group", e);
        }
    }

    @Transactional(readOnly = true)
    public Page<ProfessorGroupResponse> getAllProfessorGroup(Pageable pageable, String keyword) {
        try {
            Page<ProfessorGroupModel> professorGroups;
            if (keyword == null || keyword.isEmpty()) {
                professorGroups = professorGroupRepository.findAll(pageable);
            } else {
                professorGroups = professorGroupRepository.findAllBySearchInput(keyword, pageable);
            }
            return professorGroups.map(professorGroupMapper::toDto);
        } catch (Exception e) {
            throw new RuntimeException("Fail to get all professor groups", e);
        }
    }

    @Transactional
    public void updateProfessorGroup(@NotNull Long id, @NotNull ProfessorGroupRequest request) {
        try {
            ProfessorGroupModel professorGroupModel = professorGroupRepository.findById(id)
                    .orElseThrow(() -> new AppException("Professor group not found", HttpStatus.NOT_FOUND));
            professorGroupMapper.updateEntity(request, professorGroupModel);
            professorGroupRepository.save(professorGroupModel);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Fail to update professor group", e);
        }
    }

    @Transactional
    public void deleteProfessorGroup(@NotNull Long id) {
        try {
            ProfessorGroupModel professorGroupModel = professorGroupRepository.findById(id)
                    .orElseThrow(() -> new AppException("Professor group not found", HttpStatus.NOT_FOUND));
            professorGroupModel.setStatusKey(Constants.INACTIVE);
            professorGroupRepository.save(professorGroupModel);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Fail to delete professor group", e);
        }
    }

    @Transactional(readOnly = true)
    public Page<ProfessorGroupResponse> getAllProfessorGroupByProfessorId(Pageable pageable, String id) {
        try {
            Page<ProfessorGroupModel> professorGroups = professorGroupRepository.findAllByProfessor_IdProfessor(id, pageable);
            return professorGroups.map(professorGroupMapper::toDto);
        } catch (Exception e) {
            throw new RuntimeException("Fail to get all professor groups", e);
        }
    }
}