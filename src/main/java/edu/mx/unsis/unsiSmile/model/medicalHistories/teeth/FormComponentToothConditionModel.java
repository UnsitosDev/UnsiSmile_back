package edu.mx.unsis.unsiSmile.model.medicalHistories.teeth;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "form_component_tooth_conditions")
public class FormComponentToothConditionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_form_component_tooth_condition")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_form_component", nullable = false)
    private FormComponentModel formComponent;

    @ManyToOne
    @JoinColumn(name = "fk_tooth_condition", nullable = false)
    private ToothConditionModel toothCondition;
}