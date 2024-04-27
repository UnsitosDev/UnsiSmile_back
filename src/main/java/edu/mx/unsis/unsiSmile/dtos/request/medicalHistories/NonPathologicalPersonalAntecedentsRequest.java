package edu.mx.unsis.unsiSmile.dtos.request.medicalHistories;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NonPathologicalPersonalAntecedentsRequest {
    
    private Long idNonPathologicalPersonalAntecedents;

    @NotNull(message = "The field eatsFruitsVegetables can't be null")
    private Boolean eatsFruitsVegetables;
    @NotNull(message = "The field eatsMeat can't be null")
    private Boolean eatsMeat;
    @NotNull(message = "The field eatsCereals can't be null")
    private Boolean eatsCereals;
    @NotNull(message = "The field eatsJunkFood can't be null")
    private Boolean eatsJunkFood;
    @NotNull(message = "The field drinksWaterDaily can't be null")
    private Boolean drinksWaterDaily;
    @NotNull(message = "The field drinksSodas can't be null")
    private Boolean drinksSodas;
    @NotNull(message = "The field dailySleepHours can't be null")
    private Long dailySleepHours;
    @NotNull(message = "The field timesBathesPerWeek can't be null")
    private Long timesBathesPerWeek;
    @NotNull(message = "The field timesBrushesTeethPerDay can't be null")
    private Long timesBrushesTeethPerDay;
    @NotNull(message = "The field houseWithFloor can't be null")
    private Boolean houseWithFloor;
    @NotNull(message = "The field housingMaterialId can't be null")
    private HousingMaterialRequest housingMaterialId;

}
