package edu.mx.unsis.unsiSmile.dtos.response.medicalHistories;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NonPathologicalPersonalAntecedentsResponse {
    private Long idNonPathologicalPersonalAntecedents;
    private Boolean eatsFruitsVegetables;
    private Boolean eatsMeat;
    private Boolean eatsCereals;
    private Boolean eatsJunkFood;
    private Boolean drinksWaterDaily;
    private Boolean drinksSodas;
    private Long dailySleepHours;
    private Long timesBathesPerWeek;
    private Long timesBrushesTeethPerDay;
    private Boolean houseWithFloor;
    private HousingMaterialResponse housingMaterial;
}
