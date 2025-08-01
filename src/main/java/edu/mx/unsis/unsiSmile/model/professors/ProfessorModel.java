package edu.mx.unsis.unsiSmile.model.professors;

import edu.mx.unsis.unsiSmile.authenticationProviders.model.UserModel;
import edu.mx.unsis.unsiSmile.model.people.PersonModel;
import edu.mx.unsis.unsiSmile.model.students.CareerModel;
import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "professors")
public class ProfessorModel extends AuditModel {
    @Id
    @Column(name = "id_professor", length = 15, unique = true, nullable = false)
    private String idProfessor;

    @OneToOne
    @JoinColumn(name = "fk_person",nullable = false)
    private PersonModel person;

    @OneToOne
    @JoinColumn(name = "fk_user",  nullable = false)
    private UserModel user;

    @OneToOne
    @JoinColumn(name = "fk_career")
    private CareerModel career;
}