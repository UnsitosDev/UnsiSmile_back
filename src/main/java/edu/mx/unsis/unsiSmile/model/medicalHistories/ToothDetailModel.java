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
@Table(name = "tooth_detail")
public class ToothDetailModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tooth_detail")
    private Long idToothDetail;

    @ManyToOne
    @JoinColumn(name = "fk_id_dental_code")
    private DentalCodeModel dentalCode;

    @ManyToOne
    @JoinColumn(name = "fk_tooth_condition")
    private ToothConditionModel toothCondition;

    @ManyToOne
    @JoinColumn(name = "fk_tooth_region")
    private ToothRegionModel toothRegion;

    @ManyToOne
    @JoinColumn(name = "fk_odontogram")
    private OdontogramModel odontogram;
}
