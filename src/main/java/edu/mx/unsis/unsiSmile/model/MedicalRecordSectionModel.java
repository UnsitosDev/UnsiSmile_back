package edu.mx.unsis.unsiSmile.model;

import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import edu.mx.unsis.unsiSmile.model.utils.MedicalRecordSectionModelPk;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString(exclude = {"medicalRecordCatalogModel", "formSectionModel"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "medical_record_sections")
public class MedicalRecordSectionModel extends AuditModel {

    @EmbeddedId
    private MedicalRecordSectionModelPk idMedicalRecordSectionModel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_medical_record_catalog", nullable = false, insertable = false, updatable = false)
    private MedicalRecordCatalogModel medicalRecordCatalogModel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_form_section",  nullable = false, insertable = false, updatable = false)
    private FormSectionModel formSectionModel;

    @Column(name = "section_order")
    private Long order;
}
