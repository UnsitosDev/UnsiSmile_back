package edu.mx.unsis.unsiSmile.model.medicalrecords.dentalprophylaxis;

import edu.mx.unsis.unsiSmile.model.medicalrecords.teeth.ToothModel;
import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "sohi_tooth_code")
public class SOHIToothCodeModel extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tooth_code")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_tooth", nullable = false)
    private ToothModel tooth;

    @Column(name = "code", nullable = false)
    private Integer code;

    @ManyToOne
    @JoinColumn(name = "fk_sohi", nullable = false)
    private SOHIModel sohi;
}
