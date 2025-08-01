package edu.mx.unsis.unsiSmile.model.forms.questions;

import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "validation_types")
public class ValidationTypeModel extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_validation_type")
    private Long idValidationType;

    @Column(name = "validation_code")
    private String validationCode;
}