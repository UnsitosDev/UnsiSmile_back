package edu.mx.unsis.unsiSmile.model;

import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "clinical_history_catalogs")
public class ClinicalHistoryCatalogModel extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_clinical_history_catalog")
    private Integer idClinicalHistoryCatalog;

    @Column(name = "clinical_history_name", nullable = false, length = 100)
    private String clinicalHistoryName;
}
