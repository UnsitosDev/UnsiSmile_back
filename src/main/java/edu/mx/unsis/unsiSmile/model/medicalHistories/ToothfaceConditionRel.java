package edu.mx.unsis.unsiSmile.model.medicalHistories;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "toothFace_condition_rel")
public class ToothfaceConditionRel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_toothFace_condition_rel")
    private Long idtoothFaceConditionRel;

    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    @ManyToOne
    @JoinColumn(name = "fk_tooth_face")
    private ToothFaceModel toothFace;

    @ManyToOne
    @JoinColumn(name = "fk_tooth_condition")
    private ToothConditionModel toothCondition;
}
