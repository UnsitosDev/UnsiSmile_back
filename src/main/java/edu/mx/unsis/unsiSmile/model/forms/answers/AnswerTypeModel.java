package edu.mx.unsis.unsiSmile.model.forms.answers;

import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "answer_types")
public class AnswerTypeModel extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_answer_type")
    private Long idAnswerType;

    @Column(name = "answer_type", nullable = false, unique = true)
    private String description;
}