package edu.mx.unsis.unsiSmile.service.medicalHistories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.HousingMaterialRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.HousingMaterialResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.HousingMaterialMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.HousingMaterialModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IHousingMaterialRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HousingMaterialService {

    private final IHousingMaterialRepository housingMaterialRepository;
    private final HousingMaterialMapper housingMaterialMapper;

    public HousingMaterialResponse createHousingMaterial(HousingMaterialRequest request) {
        try {
            HousingMaterialModel housingMaterialModel = housingMaterialMapper.toEntity(request);
            HousingMaterialModel savedHousingMaterial = housingMaterialRepository.save(housingMaterialModel);
            return housingMaterialMapper.toDto(savedHousingMaterial);
        } catch (Exception ex) {
            throw new AppException("Failed to create housing material", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public HousingMaterialResponse getHousingMaterialById(Long id) {
        try {
            HousingMaterialModel housingMaterialModel = housingMaterialRepository.findById(id)
                    .orElseThrow(() -> new AppException("Housing material not found with ID: " + id, HttpStatus.NOT_FOUND));
            return housingMaterialMapper.toDto(housingMaterialModel);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch housing material", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<HousingMaterialResponse> getAllHousingMaterials() {
        try {
            List<HousingMaterialModel> housingMaterials = housingMaterialRepository.findAll();
            return housingMaterials.stream()
                    .map(housingMaterialMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException("Failed to fetch housing materials", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void deleteHousingMaterialById(Long id) {
        try {
            if (!housingMaterialRepository.existsById(id)) {
                throw new AppException("Housing material not found with ID: " + id, HttpStatus.NOT_FOUND);
            }
            housingMaterialRepository.deleteById(id);
        } catch (Exception ex) {
            throw new AppException("Failed to delete housing material", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
