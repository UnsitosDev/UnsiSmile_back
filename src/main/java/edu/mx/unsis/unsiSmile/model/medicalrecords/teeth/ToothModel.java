package edu.mx.unsis.unsiSmile.model.medicalrecords.teeth;

import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "teeth")
public class ToothModel extends AuditModel {

    @Id
    @Column(name = "id_tooth", length = 3)
    private String idTooth;

    @Column(name = "is_adult")
    private boolean isAdult;

    @Column(name = "description", nullable = false)
    private String description;

}