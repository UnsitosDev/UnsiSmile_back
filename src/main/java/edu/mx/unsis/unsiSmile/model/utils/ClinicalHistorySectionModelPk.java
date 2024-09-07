package edu.mx.unsis.unsiSmile.model.utils;

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
public class ClinicalHistorySectionModelPk implements Serializable {

    @Column(name = "fk_clinical_history_catalog")
    private Integer idClinicalHistoryCatalog;

    @Column(name = "fk_form_section")
    private Integer idFormSectionModel;
}
