package edu.mx.unsis.unsiSmile.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "vital_signs")
public class VitalSignsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vital_signs")
    private Long idVitalSigns;

    @Column(name = "weight")
    private Float weight;

    @Column(name = "height")
    private Float height;

    @Column(name = "temperature")
    private Float temperature;

    @Column(name = "heart_rate")
    private Float heartRate;

    @Column(name = "respiratory_rate")
    private Float respiratoryRate;

    @Column(name = "blood_pressure")
    private Float bloodPressure;

    @Column(name = "oxygen_saturation")
    private Float oxygenSaturation;

    @Column(name = "glucose")
    private Float glucose;

    @Column(name = "pulse")
    private Float pulse;

}