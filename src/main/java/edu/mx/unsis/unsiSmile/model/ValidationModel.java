package edu.mx.unsis.unsiSmile.model;

import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "validations")
public class ValidationModel extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_validation")
    private Long idValidation;

    @Column(name = "validation_name", nullable = false)
    private String validationName;

    @Column(name = "validation_message")
    private String validationMessage;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "fk_validation_type")
    private ValidationTypeModel validationTypeModel;
}
