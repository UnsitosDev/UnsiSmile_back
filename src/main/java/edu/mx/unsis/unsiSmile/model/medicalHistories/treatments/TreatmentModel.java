package edu.mx.unsis.unsiSmile.model.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.model.ClinicalHistoryCatalogModel;
import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
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
    @JoinColumn(name = "fk_scope_type", referencedColumnName = "id_scope_type", nullable = false)
    private ScopeTypeModel scopeType;

    @Column(name = "cost", precision = 10, scale = 2)
    private BigDecimal cost;

    @ManyToOne
    @JoinColumn(name = "fk_clinical_history_catalog", referencedColumnName = "id_clinical_history_catalog")
    private ClinicalHistoryCatalogModel clinicalHistoryCatalog;
}