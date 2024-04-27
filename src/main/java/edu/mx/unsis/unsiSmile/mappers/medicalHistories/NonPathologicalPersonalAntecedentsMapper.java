package edu.mx.unsis.unsiSmile.mappers.medicalHistories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.NonPathologicalPersonalAntecedentsRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.NonPathologicalPersonalAntecedentsResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.NonPathologicalPersonalAntecedentsModel;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class NonPathologicalPersonalAntecedentsMapper implements BaseMapper<NonPathologicalPersonalAntecedentsResponse, NonPathologicalPersonalAntecedentsRequest, NonPathologicalPersonalAntecedentsModel> {

    private final HousingMaterialMapper housingMaterialMapper;

    @Override
    public NonPathologicalPersonalAntecedentsModel toEntity(NonPathologicalPersonalAntecedentsRequest request) {



        return NonPathologicalPersonalAntecedentsModel.builder()
                .idNonPathologicalPersonalAntecedents(request.getIdNonPathologicalPersonalAntecedents())
                .eatsFruitsVegetables(request.getEatsFruitsVegetables())
                .eatsMeat(request.getEatsMeat())
                .eatsCereals(request.getEatsCereals())
                .eatsJunkFood(request.getEatsJunkFood())
                .drinksWaterDaily(request.getDrinksWaterDaily())
                .drinksSodas(request.getDrinksSodas())
                .dailySleepHours(request.getDailySleepHours())
                .timesBathesPerWeek(request.getTimesBathesPerWeek())
                .timesBrushesTeethPerDay(request.getTimesBrushesTeethPerDay())
                .houseWithFloor(request.getHouseWithFloor())
                .housingMaterial(housingMaterialMapper.toEntity(request.getHousingMaterialId()))
                .build();
    }

    @Override
    public NonPathologicalPersonalAntecedentsResponse toDto(NonPathologicalPersonalAntecedentsModel entity) {
        return NonPathologicalPersonalAntecedentsResponse.builder()
                .idNonPathologicalPersonalAntecedents(entity.getIdNonPathologicalPersonalAntecedents())
                .eatsFruitsVegetables(entity.getEatsFruitsVegetables())
                .eatsMeat(entity.getEatsMeat())
                .eatsCereals(entity.getEatsCereals())
                .eatsJunkFood(entity.getEatsJunkFood())
                .drinksWaterDaily(entity.getDrinksWaterDaily())
                .drinksSodas(entity.getDrinksSodas())
                .dailySleepHours(entity.getDailySleepHours())
                .timesBathesPerWeek(entity.getTimesBathesPerWeek())
                .timesBrushesTeethPerDay(entity.getTimesBrushesTeethPerDay())
                .houseWithFloor(entity.getHouseWithFloor())
                .housingMaterial(housingMaterialMapper.toDto(entity.getHousingMaterial()))
                .build();
    }

    @Override
    public List<NonPathologicalPersonalAntecedentsResponse> toDtos(List<NonPathologicalPersonalAntecedentsModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(NonPathologicalPersonalAntecedentsRequest request, NonPathologicalPersonalAntecedentsModel entity) {
        entity.setEatsFruitsVegetables(request.getEatsFruitsVegetables());
        entity.setEatsMeat(request.getEatsMeat());
        entity.setEatsCereals(request.getEatsCereals());
        entity.setEatsJunkFood(request.getEatsJunkFood());
        entity.setDrinksWaterDaily(request.getDrinksWaterDaily());
        entity.setDrinksSodas(request.getDrinksSodas());
        entity.setDailySleepHours(request.getDailySleepHours());
        entity.setTimesBathesPerWeek(request.getTimesBathesPerWeek());
        entity.setTimesBrushesTeethPerDay(request.getTimesBrushesTeethPerDay());
        entity.setHouseWithFloor(request.getHouseWithFloor());
        entity.setHousingMaterial(housingMaterialMapper.toEntity(request.getHousingMaterialId()));
    }
}
