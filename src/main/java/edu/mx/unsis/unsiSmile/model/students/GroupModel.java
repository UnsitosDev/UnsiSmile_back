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
@Table(name = "groups")
public class GroupModel extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_group")
    private Long idGroup;

    @Column(name = "semester_number", nullable = false)
    private String semesterNumber;

    @Column(name = "group_name", nullable = false)
    private String groupName;

    @ManyToOne
    @JoinColumn(name = "fk_career")
    private CareerModel career;

    @ManyToOne
    @JoinColumn(name = "fk_semester")
    private SemesterModel semester;

    @Transient
    public String getFullGroupName() {
        String baseName = semesterNumber + career.getIdCareer();
        return (groupName != null && !groupName.isEmpty())
                ? baseName + "-" + groupName
                : baseName;
    }
}
