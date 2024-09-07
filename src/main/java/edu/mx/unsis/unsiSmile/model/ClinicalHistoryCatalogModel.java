package edu.mx.unsis.unsiSmile.model;

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
@Table(name = "clinical_history_catalogs")
public class ClinicalHistoryCatalogModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_clinical_history_catalog")
    private Integer idClinicalHistoryCatalog;

    @Column(name = "clinical_history_name", nullable = false, length = 100)
    private String clinicalHistoryName;
}
