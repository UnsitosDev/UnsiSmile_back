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
@Table(name = "ethnic_groups")
public class EthnicGroupModel extends AuditModel {

    @Id
    @Column(name = "id_ethnic_group")
    private Long idEthnicGroup;

    @Column(name = "ethnic_group", length = 100, unique = true)
    private String ethnicGroup;
}
