package edu.mx.unsis.unsiSmile.model.medicalHistories.teeth;

import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "toothface_conditions")
public class ToothFaceConditionModel extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_toothface_conditions")
    private Long idToothFaceCondition;

    @Column(name = "description", length = 50, nullable = false)
    private String description;

}