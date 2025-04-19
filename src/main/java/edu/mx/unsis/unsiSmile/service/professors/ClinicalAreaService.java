package edu.mx.unsis.unsiSmile.service.professors;

import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.request.professors.ClinicalAreaRequest;
import edu.mx.unsis.unsiSmile.dtos.response.professors.ClinicalAreaResponse;
import edu.mx.unsis.unsiSmile.dtos.response.professors.ProfessorResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.professors.ClinicalAreaMapper;
import edu.mx.unsis.unsiSmile.model.professors.ClinicalAreaModel;
import edu.mx.unsis.unsiSmile.repository.professors.IClinicalAreaRepository;
import io.jsonwebtoken.lang.Assert;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClinicalAreaService {
    private final IClinicalAreaRepository clinicalAreaRepository;
    private final ClinicalAreaMapper clinicalAreaMapper;
    private final ProfessorClinicalAreaService professorClinicalAreaService;

    @Transactional
    public void createClinicalArea(ClinicalAreaRequest request) {
        try {
            Assert.notNull(request, ResponseMessages.REQUEST_CANNOT_BE_NULL);
            ClinicalAreaModel clinicalAreaModel = clinicalAreaMapper.toEntity(request);
            clinicalAreaRepository.save(clinicalAreaModel);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ResponseMessages.FAILED_CREATE_CLINICAL_AREA, HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Transactional
    public ClinicalAreaResponse getClinicalArea(@NotNull Long idClinicalArea, Pageable professorPageable) {
        try {
            ClinicalAreaModel clinicalAreaModel = clinicalAreaRepository.findByIdClinicalAreaAndStatusKey(idClinicalArea, Constants.ACTIVE)
                .orElseThrow(() -> new AppException(
                        String.format(ResponseMessages.CLINICAL_AREA_NOT_FOUND, idClinicalArea),
                        HttpStatus.NOT_FOUND));

            Page<ProfessorResponse> professorResponses = professorClinicalAreaService.getProfessorsByClinicalAreaId(clinicalAreaModel, professorPageable);

            ClinicalAreaResponse response = clinicalAreaMapper.toDto(clinicalAreaModel);

            response.setProfessors(professorResponses);

            return response;
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ResponseMessages.FAILED_FETCH_CLINICAL_AREA, HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Transactional
    public Page<ClinicalAreaResponse> getAllClinicalAreas(Pageable pageable, String keyword) {
        try {
            Page<ClinicalAreaModel> clinicalAreas = (keyword == null || keyword.isBlank())
                    ? clinicalAreaRepository.findAllByStatusKey(Constants.ACTIVE, pageable)
                    : clinicalAreaRepository.findAllBySearchInput(keyword, Constants.ACTIVE, pageable);

            return clinicalAreas.map(clinicalAreaMapper::toDto);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ResponseMessages.FAILED_FETCH_CLINICAL_AREAS, HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Transactional
    public void updateClinicalArea(@NotNull Long idClinicalArea, ClinicalAreaRequest request) {
        try {
            ClinicalAreaModel clinicalAreaModel = clinicalAreaRepository.findById(idClinicalArea)
                .orElseThrow(() -> new AppException(
                        String.format(ResponseMessages.CLINICAL_AREA_NOT_FOUND, idClinicalArea),
                        HttpStatus.NOT_FOUND));
            clinicalAreaMapper.updateEntity(request, clinicalAreaModel);
            clinicalAreaRepository.save(clinicalAreaModel);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ResponseMessages.FAILED_UPDATE_CLINICAL_AREA, HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Transactional
    public void deleteClinicalArea(@NotNull Long idClinicalArea) {
        try {
            ClinicalAreaModel clinicalAreaModel = clinicalAreaRepository.findById(idClinicalArea)
                .orElseThrow(() -> new AppException(
                        String.format(ResponseMessages.CLINICAL_AREA_NOT_FOUND, idClinicalArea),
                        HttpStatus.NOT_FOUND));
            clinicalAreaModel.setStatusKey(Constants.INACTIVE);
            clinicalAreaRepository.save(clinicalAreaModel);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ResponseMessages.FAILED_DELETE_CLINICAL_AREA, HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }
}
