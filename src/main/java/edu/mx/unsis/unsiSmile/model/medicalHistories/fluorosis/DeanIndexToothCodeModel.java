package edu.mx.unsis.unsiSmile.model.medicalHistories.fluorosis;

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
@Table(name = "dean_index_tooth_code")
public class DeanIndexToothCodeModel extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dean_index_tooth_code")
    private Long idDeanIndexToothCode;

    @ManyToOne
    @JoinColumn(name = "fk_tooth", nullable = false)
    private ToothModel tooth;

    @Column(name = "code", nullable = false)
    private Integer code;

    @ManyToOne
    @JoinColumn(name = "fk_dean_index", referencedColumnName = "id_dean_index", nullable = false)
    private DeanIndexModel deanIndex;
}