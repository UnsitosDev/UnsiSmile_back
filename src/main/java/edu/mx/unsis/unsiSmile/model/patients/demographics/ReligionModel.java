package edu.mx.unsis.unsiSmile.model.patients.demographics;

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
@Table(name = "religions")
public class ReligionModel extends AuditModel {

    @Id
    @Column(name = "id_religion")
    private Long idReligion;

    @Column(name = "religion", length = 100, unique = true)
    private String religion;

}
