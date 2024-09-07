package edu.mx.unsis.unsiSmile.model;

import edu.mx.unsis.unsiSmile.model.utils.ClinicalHistorySectionModelPk;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "clinical_history_sections")
public class ClinicalHistorySectionModel {

    @EmbeddedId
    private ClinicalHistorySectionModelPk idClinicalHistorySectionModel;
}
