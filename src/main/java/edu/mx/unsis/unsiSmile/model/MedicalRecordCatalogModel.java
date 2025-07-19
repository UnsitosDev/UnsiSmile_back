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
@Table(name = "medical_record_catalogs")
public class MedicalRecordCatalogModel extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medical_record_catalog")
    private Long idMedicalRecordCatalog;

    @Column(name = "medical_record_name", nullable = false, length = 100)
    private String medicalRecordName;
}