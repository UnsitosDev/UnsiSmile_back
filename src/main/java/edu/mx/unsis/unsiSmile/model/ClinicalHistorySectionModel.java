package edu.mx.unsis.unsiSmile.model;

import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import edu.mx.unsis.unsiSmile.model.utils.ClinicalHistorySectionModelPk;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString(exclude = {"clinicalHistoryCatalogModel", "formSectionModel"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "clinical_history_sections")
public class ClinicalHistorySectionModel extends AuditModel {

    @EmbeddedId
    private ClinicalHistorySectionModelPk idClinicalHistorySectionModel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_clinical_history_catalog", nullable = false, insertable = false, updatable = false)
    private ClinicalHistoryCatalogModel clinicalHistoryCatalogModel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_form_section",  nullable = false, insertable = false, updatable = false)
    private FormSectionModel formSectionModel;
}
