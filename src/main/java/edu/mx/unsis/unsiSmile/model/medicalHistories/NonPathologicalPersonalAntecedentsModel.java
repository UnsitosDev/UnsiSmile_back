package edu.mx.unsis.unsiSmile.model.medicalHistories;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "NonPathologicalPersonalAntecedents")
public class NonPathologicalPersonalAntecedentsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_non_pathological_personal_antecedents")
    private Long idNonPathologicalPersonalAntecedents;

    @Column(name = "eats_fruits_vegetables")
    private Boolean eatsFruitsVegetables;

    @Column(name = "eats_meat")
    private Boolean eatsMeat;

    @Column(name = "eats_cereals")
    private Boolean eatsCereals;

    @Column(name = "eats_junk_food")
    private Boolean eatsJunkFood;

    @Column(name = "drinks_water_daily")
    private Boolean drinksWaterDaily;

    @Column(name = "drinks_sodas")
    private Boolean drinksSodas;

    @Column(name = "daily_sleep_hours")
    private Long dailySleepHours;

    @Column(name = "times_bathes_per_week")
    private Long timesBathesPerWeek;

    @Column(name = "times_brushes_teeth_per_day")
    private Long timesBrushesTeethPerDay;

    @Column(name = "house_with_floor")
    private Boolean houseWithFloor;

    @ManyToOne
    @JoinColumn(name = "fk_housing_material")
    private HousingMaterialModel housingMaterial;
}
