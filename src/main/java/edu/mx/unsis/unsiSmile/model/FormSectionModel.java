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
@Table(name = "form_sections")
public class FormSectionModel extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_form_section")
    private Long idFormSection;

    @Column(name = "form_name", nullable = false, length = 100)
    private String formName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_parent_section", referencedColumnName = "id_form_section")
    private FormSectionModel parentSection;
}
