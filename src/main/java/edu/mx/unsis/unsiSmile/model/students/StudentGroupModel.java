package edu.mx.unsis.unsiSmile.model.students;

import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "student_groups")
public class StudentGroupModel extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_student_groups")
    private Long idStudentGroups;

    @ManyToOne
    @JoinColumn(name = "fk_student")
    private StudentModel student;

    @ManyToOne
    @JoinColumn(name = "fk_group")
    private GroupModel group;
}