package edu.mx.unsis.unsiSmile.service;

import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.dtos.request.forms.catalogs.CatalogRequest;
import edu.mx.unsis.unsiSmile.dtos.response.CatalogOptionResponse;
import edu.mx.unsis.unsiSmile.dtos.response.CatalogResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.CatalogMapper;
import edu.mx.unsis.unsiSmile.model.forms.catalogs.CatalogModel;
import edu.mx.unsis.unsiSmile.repository.ICatalogRepository;
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
public class CatalogService {
    private final ICatalogRepository catalogRepository;
    private final CatalogMapper catalogMapper;
    private final CatalogOptionService catalogOptionService;

    @Transactional
    public void save(CatalogRequest request) {
        try {
            Assert.notNull(request, "CatalogRequest cannot be null");

            CatalogModel catalogModel = catalogMapper.toEntity(request);

            catalogRepository.save(catalogModel);
        } catch (Exception ex) {
            throw new AppException("Failed to save catalog", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public CatalogResponse findById(Long id) {
        try {
            Assert.notNull(id, "Id cannot be null");

            CatalogModel catalogModel = catalogRepository.findById(id)
                    .orElseThrow(() -> new AppException("Catalog not found with id: " + id, HttpStatus.NOT_FOUND));

            return this.toResponse(catalogModel);
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AppException("Failed to find catalog with id: " + id, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<CatalogResponse> findAll() {
        try {
            List<CatalogModel> catalogModelList = catalogRepository.findAll();

            if (catalogModelList.isEmpty()) {
                throw new AppException("No catalogs found", HttpStatus.NOT_FOUND);
            } else {
                return catalogModelList.stream()
                        .map(this::toResponse)
                        .collect(Collectors.toList());
            }
        } catch (Exception ex) {
            throw new AppException("Failed to fetch catalogs", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteById(Long id) {
        try {
            Optional<CatalogModel> catalogOptional = catalogRepository.findById(id);

            catalogOptional.ifPresentOrElse(
                    catalog -> {
                        catalog.setStatusKey(Constants.INACTIVE);
                        catalogRepository.save(catalog);
                    },
                    () -> {
                        throw new AppException("Catalog not found with ID: " + id, HttpStatus.NOT_FOUND);
                    }
            );
        } catch (Exception ex) {
            throw new AppException("Failed to delete catalog with ID: " + id, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    private CatalogResponse toResponse(CatalogModel catalogModel){
        List<CatalogOptionResponse> optionResponseList = catalogOptionService.getOptionsByCatalog(catalogModel.getIdCatalog());
        CatalogResponse catalogResponse = catalogMapper.toDto(catalogModel);
        catalogResponse.setCatalogOptions(optionResponseList);
        return catalogResponse;
    }
}
