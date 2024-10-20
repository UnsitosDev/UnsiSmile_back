package edu.mx.unsis.unsiSmile.model.patients;

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
@Table(name = "occupations")
public class OccupationModel extends AuditModel {

    @Id
    @Column(name = "id_occupation")
    private Long idOccupation;

    @Column(name = "occupation", length = 100, unique = true)
    private String occupation;

}
