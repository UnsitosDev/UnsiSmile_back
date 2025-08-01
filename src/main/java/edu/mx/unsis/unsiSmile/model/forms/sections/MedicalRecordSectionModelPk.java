package edu.mx.unsis.unsiSmile.model.forms.sections;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class MedicalRecordSectionModelPk implements Serializable {

    @Column(name = "fk_medical_record_catalog")
    private Long idMedicalRecordCatalog;

    @Column(name = "fk_form_section")
    private String idFormSectionModel;
}