package edu.mx.unsis.unsiSmile.model.students;

import edu.mx.unsis.unsiSmile.authenticationProviders.model.UserModel;
import edu.mx.unsis.unsiSmile.model.PersonModel;
import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "students")
public class StudentModel extends AuditModel {

    @Id
    @Column(name = "enrollment", nullable = false, unique = true)
    private String enrollment;

    @OneToOne
    @JoinColumn(name = "fk_user", nullable = false)
    private UserModel user;

    @OneToOne
    @JoinColumn(name = "fk_person")
    private PersonModel person;
}
