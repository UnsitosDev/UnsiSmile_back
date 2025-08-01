package edu.mx.unsis.unsiSmile.model.professors;

import edu.mx.unsis.unsiSmile.authenticationProviders.model.UserModel;
import edu.mx.unsis.unsiSmile.model.people.PersonModel;
import edu.mx.unsis.unsiSmile.model.students.CareerModel;
import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
