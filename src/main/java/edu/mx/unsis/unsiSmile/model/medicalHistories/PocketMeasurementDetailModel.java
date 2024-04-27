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
@Table(name = "pocket_measurement_detail")
public class PocketMeasurementDetailModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pocket_measurement_detail")
    private Long idPocketMeasurementDetail;

    @ManyToOne
    @JoinColumn(name = "fk_tooth_regions_periodontogram", referencedColumnName = "id_tooth_regions_periodontogram")
    private ToothRegionPeriodontogramModel toothRegionsPeriodontogram;

    @ManyToOne
    @JoinColumn(name = "fk_dental_code", referencedColumnName = "id_dental_code")
    private DentalCodeModel dentalCode;

    @ManyToOne
    @JoinColumn(name = "fk_regions_measurement_pockets", referencedColumnName = "id_regions_measurement_pockets")
    private RegionMeasurementPocketsModel regionsMeasurementPockets;

    @ManyToOne
    @JoinColumn(name = "fk_periodontogram", referencedColumnName = "id_periodontogram")
    private PeriodontogramModel periodontogram;

    @Column(name = "measurement")
    private Float measurement;
}

