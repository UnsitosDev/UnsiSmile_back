package edu.mx.unsis.unsiSmile.model.professors;

import edu.mx.unsis.unsiSmile.model.students.GroupModel;
import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "professor_groups")
public class ProfessorGroupModel extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_professor_groups")
    private Long idStudentGroups;

    @ManyToOne
    @JoinColumn(name = "fk_professor")
    private ProfessorModel professor;

    @ManyToOne
    @JoinColumn(name = "fk_group")
    private GroupModel group;
}
