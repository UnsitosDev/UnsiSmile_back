package edu.mx.unsis.unsiSmile.service.professors;

import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.dtos.request.CatalogOptionRequest;
import edu.mx.unsis.unsiSmile.dtos.request.professors.ProfessorClinicalAreaRequest;
import edu.mx.unsis.unsiSmile.dtos.response.professors.ProfessorClinicalAreaResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.CatalogOptionMapper;
import edu.mx.unsis.unsiSmile.mappers.professors.ProfessorClinicalAreaMapper;
import edu.mx.unsis.unsiSmile.model.CatalogOptionModel;
import edu.mx.unsis.unsiSmile.model.PersonModel;
import edu.mx.unsis.unsiSmile.model.professors.ProfessorClinicalAreaModel;
import edu.mx.unsis.unsiSmile.model.professors.ProfessorModel;
import edu.mx.unsis.unsiSmile.repository.ICatalogOptionRepository;
import edu.mx.unsis.unsiSmile.repository.ICatalogRepository;
import edu.mx.unsis.unsiSmile.repository.professors.IProfessorClinicalAreaRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfessorClinicalAreaService {
    private final IProfessorClinicalAreaRepository professorClinicalAreaRepository;
    private final ProfessorClinicalAreaMapper professorClinicalAreaMapper;
    private final CatalogOptionMapper catalogOptionMapper;
    private final ICatalogOptionRepository catalogOptionRepository;
    private final ICatalogRepository catalogRepository;

    @Transactional
    public void createProfessorClinicalArea(@NotNull ProfessorClinicalAreaRequest request) {
        try {
            ProfessorClinicalAreaModel professorClinicalAreaModel = professorClinicalAreaMapper.toEntity(request);
            ProfessorClinicalAreaModel professorClinicalAreaSaved = professorClinicalAreaRepository.save(professorClinicalAreaModel);

            createCatalogOptionForProfessor(professorClinicalAreaSaved);
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

    private String buildFullName(ProfessorClinicalAreaModel professorClinicalAreaSaved) {
        PersonModel person = professorClinicalAreaSaved.getProfessor().getPerson();
        return person.getFirstName() + " " +
                (person.getSecondName() != null ? person.getSecondName() + " " : "") +
                person.getFirstLastName() + " " +
                person.getSecondLastName();
    }

    private void createCatalogOptionForProfessor(ProfessorClinicalAreaModel professorClinicalAreaSaved) {
        String fullName = buildFullName(professorClinicalAreaSaved);
        Optional<CatalogOptionModel> existingOptionOpt = catalogOptionRepository.findByOptionName(fullName);

        if(existingOptionOpt.isPresent()){
            CatalogOptionModel existingOption = existingOptionOpt.get();
            existingOption.setStatusKey(Constants.ACTIVE);
            catalogOptionRepository.save(existingOption);
        } else {
            Long idCatalog = catalogRepository.findIdByCatalogName("Catedráticos responsables de área")
                    .orElseThrow(() -> new RuntimeException("Catalog 'Catedráticos responsables de área' not found"));

            CatalogOptionModel catalogOptionModel = catalogOptionMapper.toEntity(
                    CatalogOptionRequest.builder()
                            .optionName(fullName)
                            .idCatalog(idCatalog)
                            .build()
            );
            catalogOptionRepository.save(catalogOptionModel);
        }
    }

    private void toggleCatalogOptionForProfessor(ProfessorClinicalAreaModel professorClinicalAreaModel) {
        String fullName = buildFullName(professorClinicalAreaModel);
        Optional<CatalogOptionModel> existingOptionOpt = catalogOptionRepository.findByOptionName(fullName);

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
                    .orElseThrow(() -> new AppException("Professor clinical area not found", HttpStatus.NOT_FOUND));

            ProfessorModel professor = professorClinicalAreaModel.getProfessor();

            if (!Constants.ACTIVE.equals(professor.getStatusKey())) {
                throw new AppException("Cannot enable clinical area because the professor is inactive.", HttpStatus.BAD_REQUEST);
            }

            String newStatus = Constants.ACTIVE.equals(professorClinicalAreaModel.getStatusKey())
                    ? Constants.INACTIVE : Constants.ACTIVE;
            professorClinicalAreaModel.setStatusKey(newStatus);
            professorClinicalAreaRepository.save(professorClinicalAreaModel);

            toggleCatalogOptionForProfessor(professorClinicalAreaModel);

        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Fail to enable professor clinical area", e);
        }
    }

}
