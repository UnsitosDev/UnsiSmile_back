package edu.mx.unsis.unsiSmile.model.patients;

import edu.mx.unsis.unsiSmile.model.CatalogOptionModel;
import edu.mx.unsis.unsiSmile.model.PersonModel;
import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "guardians")
public class GuardianModel extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_guardian")
    private Long idGuardian;

    @ManyToOne
    @JoinColumn(name = "fk_parental_status", nullable = false)
    private CatalogOptionModel parentalStatus;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_person", referencedColumnName = "curp", nullable = false, unique = true)
    private PersonModel person;

    @Column(name = "doctor_name", length = 100)
    private String doctorName;

}
