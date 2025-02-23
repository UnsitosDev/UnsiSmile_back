package edu.mx.unsis.unsiSmile.service.professors;

import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.dtos.request.professors.ClinicalAreaRequest;
import edu.mx.unsis.unsiSmile.dtos.response.professors.ClinicalAreaResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.professors.ClinicalAreaMapper;
import edu.mx.unsis.unsiSmile.model.professors.ClinicalAreaModel;
import edu.mx.unsis.unsiSmile.repository.professors.IClinicalAreaRepository;
import io.jsonwebtoken.lang.Assert;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClinicalAreaService {
    private final IClinicalAreaRepository clinicalAreaRepository;
    private final ClinicalAreaMapper clinicalAreaMapper;

    @Transactional
    public void createClinicalArea(ClinicalAreaRequest request) {
        try {
            Assert.notNull(request, "Request must not be null");
            ClinicalAreaModel clinicalAreaModel = clinicalAreaMapper.toEntity(request);
            clinicalAreaRepository.save(clinicalAreaModel);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException("Fail to create clinical area", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Transactional
    public ClinicalAreaResponse getClinicalArea(Long idClinicalArea) {
        try {
            ClinicalAreaModel clinicalAreaModel = clinicalAreaRepository.findById(idClinicalArea)
                .orElseThrow(() -> new AppException("Clinical area not found", HttpStatus.NOT_FOUND));
            return clinicalAreaMapper.toDto(clinicalAreaModel);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException("Fail to get clinical area", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Transactional
    public Page<ClinicalAreaResponse> getAllClinicalAreas(Pageable pageable, String keyword) {
        try {
            Page<ClinicalAreaModel> clinicalAreas;
            if (keyword == null || keyword.isEmpty()) {
                clinicalAreas  = clinicalAreaRepository.findAll(pageable);
            } else {
                clinicalAreas = clinicalAreaRepository.findAllBySearchInput(keyword, pageable);
            }
            return clinicalAreas.map(clinicalAreaMapper::toDto);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException("Fail to get clinical areas", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Transactional
    public void updateClinicalArea(ClinicalAreaRequest request) {
        try {
            ClinicalAreaModel clinicalAreaModel = clinicalAreaRepository.findById(request.getIdClinicalArea())
                .orElseThrow(() -> new AppException("Clinical area not found", HttpStatus.NOT_FOUND));
            clinicalAreaMapper.updateEntity(request, clinicalAreaModel);
            clinicalAreaRepository.save(clinicalAreaModel);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException("Fail to update clinical area", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Transactional
    public void deleteClinicalArea(Long idClinicalArea) {
        try {
            ClinicalAreaModel clinicalAreaModel = clinicalAreaRepository.findById(idClinicalArea)
                .orElseThrow(() -> new AppException("Clinical area not found", HttpStatus.NOT_FOUND));
            clinicalAreaModel.setStatusKey(Constants.INACTIVE);
            clinicalAreaRepository.save(clinicalAreaModel);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException("Fail to delete clinical area", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }
}
