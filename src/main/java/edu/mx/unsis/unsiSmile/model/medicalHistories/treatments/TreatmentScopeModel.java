package edu.mx.unsis.unsiSmile.model.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "treatment_scopes")
public class TreatmentScopeModel extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_scope")
    private Long idScope;

    @ManyToOne
    @JoinColumn(name = "fk_scope_type", referencedColumnName = "id_scope_type", nullable = false)
    private ScopeTypeModel scopeType;

    @Column(name = "scope_name", nullable = false, length = 100)
    private String scopeName;
}