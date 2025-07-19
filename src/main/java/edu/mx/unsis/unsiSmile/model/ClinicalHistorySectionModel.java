package edu.mx.unsis.unsiSmile.model;

import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import edu.mx.unsis.unsiSmile.model.utils.ClinicalHistorySectionModelPk;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString(exclude = {"medicalRecordCatalogModel", "formSectionModel"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "clinical_history_sections")
public class ClinicalHistorySectionModel extends AuditModel {

    @EmbeddedId
    private ClinicalHistorySectionModelPk idClinicalHistorySectionModel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_medical_record_catalog", nullable = false, insertable = false, updatable = false)
    private MedicalRecordCatalogModel medicalRecordCatalogModel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_form_section",  nullable = false, insertable = false, updatable = false)
    private FormSectionModel formSectionModel;

    @Column(name = "section_order")
    private Long order;
}
