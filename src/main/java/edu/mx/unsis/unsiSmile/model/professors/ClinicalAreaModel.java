package edu.mx.unsis.unsiSmile.model.professors;

import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "clinical_areas")
public class ClinicalAreaModel extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_clinical_area", nullable = false)
    private Long idClinicalArea;

    @Column(name = "clinical_area", length = 100, nullable = false)
    private String clinicalArea;
}
