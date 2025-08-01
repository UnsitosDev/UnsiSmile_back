package edu.mx.unsis.unsiSmile.model.treatments;

import edu.mx.unsis.unsiSmile.model.MedicalRecordCatalogModel;
import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "treatments")
public class TreatmentModel extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_treatment")
    private Long idTreatment;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @ManyToOne
    @JoinColumn(name = "fk_treatment_scope", referencedColumnName = "id_treatment_scope", nullable = false)
    private TreatmentScopeModel treatmentScope;

    @Column(name = "cost", precision = 10, scale = 2)
    private BigDecimal cost;

    @ManyToOne
    @JoinColumn(name = "fk_medical_record_catalog", referencedColumnName = "id_medical_record_catalog")
    private MedicalRecordCatalogModel medicalRecordCatalog;
}