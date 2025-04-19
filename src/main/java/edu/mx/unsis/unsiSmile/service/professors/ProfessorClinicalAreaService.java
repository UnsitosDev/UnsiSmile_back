package edu.mx.unsis.unsiSmile.service.professors;

import edu.mx.unsis.unsiSmile.authenticationProviders.model.ERole;
import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.request.CatalogOptionRequest;
import edu.mx.unsis.unsiSmile.dtos.request.professors.ProfessorClinicalAreaRequest;
import edu.mx.unsis.unsiSmile.dtos.response.professors.ProfessorClinicalAreaResponse;
import edu.mx.unsis.unsiSmile.dtos.response.professors.ProfessorResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.CatalogOptionMapper;
import edu.mx.unsis.unsiSmile.mappers.professors.ProfessorClinicalAreaMapper;
import edu.mx.unsis.unsiSmile.mappers.professors.ProfessorMapper;
import edu.mx.unsis.unsiSmile.model.CatalogOptionModel;
import edu.mx.unsis.unsiSmile.model.professors.ClinicalAreaModel;
import edu.mx.unsis.unsiSmile.model.professors.ProfessorClinicalAreaModel;
import edu.mx.unsis.unsiSmile.model.professors.ProfessorModel;
import edu.mx.unsis.unsiSmile.repository.ICatalogOptionRepository;
import edu.mx.unsis.unsiSmile.repository.ICatalogRepository;
import edu.mx.unsis.unsiSmile.repository.professors.IProfessorClinicalAreaRepository;
import edu.mx.unsis.unsiSmile.repository.professors.IProfessorRepository;
import edu.mx.unsis.unsiSmile.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfessorClinicalAreaService {
    private final IProfessorClinicalAreaRepository professorClinicalAreaRepository;
    private final ProfessorClinicalAreaMapper professorClinicalAreaMapper;
    private final CatalogOptionMapper catalogOptionMapper;
    private final ICatalogOptionRepository catalogOptionRepository;
    private final ICatalogRepository catalogRepository;
    private final UserService userService;
    private final IProfessorRepository professorRepository;
    private final ProfessorMapper professorMapper;

    @Transactional
    public void createProfessorClinicalArea(@NotNull ProfessorClinicalAreaRequest request) {
        try {
            ProfessorModel professorModel = getProfessorById(request.getIdProfessor());

            ProfessorClinicalAreaModel professorClinicalAreaModel = professorClinicalAreaMapper.toEntity(request);
            ProfessorClinicalAreaModel professorClinicalAreaSaved = professorClinicalAreaRepository.save(professorClinicalAreaModel);

            createCatalogOptionForProfessor(professorClinicalAreaSaved);

            assignRoleToProfessor(professorModel, ERole.ROLE_CLINICAL_AREA_SUPERVISOR);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ResponseMessages.ERROR_CREATING_PROGRESS_NOTE, HttpStatus.INTERNAL_SERVER_ERROR);
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
            throw new AppException(ResponseMessages.ERROR_FETCHING_PROGRESS_NOTES, HttpStatus.INTERNAL_SERVER_ERROR);
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
            throw new AppException(ResponseMessages.ERROR_FETCHING_PROGRESS_NOTES, HttpStatus.INTERNAL_SERVER_ERROR);
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
            throw new AppException(ResponseMessages.ERROR_CREATING_PROGRESS_NOTE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public void deleteProfessorClinicalArea(@NotNull Long id) {
        try {
            ProfessorClinicalAreaModel professorClinicalAreaModel = professorClinicalAreaRepository.findById(id)
                    .orElseThrow(() -> new AppException(ResponseMessages.PROFESSOR_CLINICAL_AREA_NOT_FOUND, HttpStatus.NOT_FOUND));
            professorClinicalAreaModel.setStatusKey(Constants.INACTIVE);
            professorClinicalAreaRepository.save(professorClinicalAreaModel);

            boolean hasOtherActiveAreas = professorClinicalAreaRepository
                    .existsByProfessorAndStatusKey(professorClinicalAreaModel.getProfessor(), Constants.ACTIVE);
            if (!hasOtherActiveAreas) {
                assignRoleToProfessor(professorClinicalAreaModel.getProfessor(), ERole.ROLE_PROFESSOR);
            }
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ResponseMessages.ERROR_CREATING_PROGRESS_NOTE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void createCatalogOptionForProfessor(ProfessorClinicalAreaModel professorClinicalAreaSaved) {
        String professorId = professorClinicalAreaSaved.getProfessor().getIdProfessor();
        Optional<CatalogOptionModel> existingOptionOpt = catalogOptionRepository.findByOptionName(professorId);

        if(existingOptionOpt.isPresent()){
            CatalogOptionModel existingOption = existingOptionOpt.get();
            existingOption.setStatusKey(Constants.ACTIVE);
            catalogOptionRepository.save(existingOption);
        } else {
            Long idCatalog = catalogRepository.findIdByCatalogName("Catedráticos responsables de área")
                    .orElseThrow(() -> new AppException(ResponseMessages.CATALOG_NOT_FOUND, HttpStatus.NOT_FOUND));

            CatalogOptionModel catalogOptionModel = catalogOptionMapper.toEntity(
                    CatalogOptionRequest.builder()
                            .optionName(professorId)
                            .idCatalog(idCatalog)
                            .build()
            );
            catalogOptionRepository.save(catalogOptionModel);
        }
    }

    private void toggleCatalogOptionForProfessor(ProfessorClinicalAreaModel professorClinicalAreaModel) {
        String professorId = professorClinicalAreaModel.getProfessor().getIdProfessor();
        Optional<CatalogOptionModel> existingOptionOpt = catalogOptionRepository.findByOptionName(professorId);

        if (existingOptionOpt.isPresent()) {
            CatalogOptionModel existingOption = existingOptionOpt.get();
            String newStatus = Constants.ACTIVE.equals(existingOption.getStatusKey()) ? Constants.INACTIVE : Constants.ACTIVE;
            existingOption.setStatusKey(newStatus);
            catalogOptionRepository.save(existingOption);
        }
    }

    @Transactional
    public void toggleProfessorClinicalAreaStatus(@NotNull Long id) {
        try {
            ProfessorClinicalAreaModel professorClinicalAreaModel = professorClinicalAreaRepository.findById(id)
                    .orElseThrow(() -> new AppException(ResponseMessages.PROFESSOR_CLINICAL_AREA_NOT_FOUND, HttpStatus.NOT_FOUND));

            ProfessorModel professor = professorClinicalAreaModel.getProfessor();

            if (!Constants.ACTIVE.equals(professor.getStatusKey())) {
                throw new AppException(ResponseMessages.CANNOT_ENABLE_CLINICAL_AREA_PROFESSOR_INACTIVE, HttpStatus.BAD_REQUEST);
            }

            String newStatus = Constants.ACTIVE.equals(professorClinicalAreaModel.getStatusKey())
                    ? Constants.INACTIVE : Constants.ACTIVE;
            professorClinicalAreaModel.setStatusKey(newStatus);
            professorClinicalAreaRepository.save(professorClinicalAreaModel);

            toggleCatalogOptionForProfessor(professorClinicalAreaModel);

        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ResponseMessages.FAILED_TO_ENABLE_PROFESSOR_CLINICAL_AREA, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ProfessorModel getProfessorById(String idProfessor) {
        return professorRepository.findById(idProfessor)
                .orElseThrow(() -> new AppException(ResponseMessages.PROFESSOR_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    private void assignRoleToProfessor(ProfessorModel professorModel, ERole role) {
        try {
            userService.changeRole(professorModel.getIdProfessor(), role.toString());
        } catch (Exception e) {
            throw new AppException(ResponseMessages.FAILED_CHANGE_ROLE, HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Transactional
    public Page<ProfessorResponse> getProfessorsByClinicalAreaId(@NotNull ClinicalAreaModel clinicalArea, Pageable professorPageable) {
        try {
            Page<ProfessorClinicalAreaModel> professorClinicalAreaPage =
                    professorClinicalAreaRepository.findByClinicalAreaAndStatusKey(
                            clinicalArea,
                            Constants.ACTIVE,
                            professorPageable
                    );

            if (professorClinicalAreaPage.isEmpty()) {
                return Page.empty(professorPageable);
            }

            return professorClinicalAreaPage.map(professorClinicalArea -> {
                ProfessorResponse response = professorMapper.toDto(professorClinicalArea.getProfessor());
                response.setIdProfessorClinicalArea(professorClinicalArea.getIdProfessorClinicalArea());
                return response;
            });

        } catch (Exception e) {
            throw new RuntimeException(ResponseMessages.FAILED_TO_FETCH_PROFESSORS_BY_CLINICAL_AREA, e);
        }
    }

    @Transactional
    public void deleteByClinicalArea(ClinicalAreaModel clinicalArea) {
        try {
            List<ProfessorClinicalAreaModel> professorClinicalAreas = professorClinicalAreaRepository.findByClinicalAreaAndStatusKey(clinicalArea, Constants.ACTIVE);
            for (ProfessorClinicalAreaModel professorClinicalArea : professorClinicalAreas) {
                deleteProfessorClinicalArea(professorClinicalArea.getIdProfessorClinicalArea());
            }
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ResponseMessages.FAILED_TO_DELETE_PROFESSOR_CLINICAL_AREAS, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
