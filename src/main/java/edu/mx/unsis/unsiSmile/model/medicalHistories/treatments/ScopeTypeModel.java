package edu.mx.unsis.unsiSmile.model.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "scope_types")
public class ScopeTypeModel extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_scope_type")
    private Long idScopeType;

    @Column(name = "name", nullable = false, length = 50, unique = true)
    private String name;
}
