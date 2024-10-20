package edu.mx.unsis.unsiSmile.service;

import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.dtos.request.CatalogOptionRequest;
import edu.mx.unsis.unsiSmile.dtos.response.CatalogOptionResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.CatalogOptionMapper;
import edu.mx.unsis.unsiSmile.model.CatalogOptionModel;
import edu.mx.unsis.unsiSmile.repository.ICatalogOptionRepository;
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
public class CatalogOptionService {
    private final ICatalogOptionRepository catalogOptionRepository;
    private final CatalogOptionMapper catalogOptionMapper;

    @Transactional
    public void save(CatalogOptionRequest request) {
        try {
            Assert.notNull(request, "CatalogOptionRequest cannot be null");

            CatalogOptionModel catalogOptionModel = catalogOptionMapper.toEntity(request);

            catalogOptionRepository.save(catalogOptionModel);
        } catch (Exception ex) {
            throw new AppException("Failed to save catalog option", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public CatalogOptionResponse findById(Long id) {
        try {
            Assert.notNull(id, "Id cannot be null");

            CatalogOptionModel catalogOptionModel = catalogOptionRepository.findById(id)
                    .orElseThrow(() -> new AppException("Catalog option not found with id: " + id, HttpStatus.NOT_FOUND));

            return catalogOptionMapper.toDto(catalogOptionModel);
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AppException("Failed to find catalog option with id: " + id, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<CatalogOptionResponse> findAll() {
        try {
            List<CatalogOptionModel> catalogOptionModelList = catalogOptionRepository.findAll();

            if (catalogOptionModelList.isEmpty()) {
                throw new AppException("No catalog options found", HttpStatus.NOT_FOUND);
            } else {
                return catalogOptionModelList.stream()
                        .map(catalogOptionMapper::toDto)
                        .collect(Collectors.toList());
            }
        } catch (Exception ex) {
            throw new AppException("Failed to fetch catalog options", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteById(Long id) {
        try {
            Optional<CatalogOptionModel> catalogOptionOptional = catalogOptionRepository.findById(id);

            catalogOptionOptional.ifPresentOrElse(
                    option -> {
                        option.setStatusKey(Constants.INACTIVE);
                        catalogOptionRepository.save(option);
                    },
                    () -> {
                        throw new AppException("Catalog option not found with ID: " + id, HttpStatus.NOT_FOUND);
                    }
            );
        } catch (Exception ex) {
            throw new AppException("Failed to delete catalog option with ID: " + id, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<CatalogOptionResponse> getOptionsByCatalog(Long catalogId) {
        try {
            List<CatalogOptionModel> optionModelList = catalogOptionRepository.findAllByCatalogId(catalogId);
            return optionModelList.stream()
                    .map(catalogOptionMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex){
            throw new AppException("Failed to fetch catalog options", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}
