package edu.mx.unsis.unsiSmile.model.medicalHistories;

import edu.mx.unsis.unsiSmile.model.medicalHistories.teeth.ToothModel;
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
    @JoinColumn(name = "sohi_id", nullable = false)
    private SOHIModel sohi;
}
