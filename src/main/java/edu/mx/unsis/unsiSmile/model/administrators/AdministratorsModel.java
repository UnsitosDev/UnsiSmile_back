package edu.mx.unsis.unsiSmile.model.administrators;

import edu.mx.unsis.unsiSmile.authenticationProviders.model.UserModel;
import edu.mx.unsis.unsiSmile.model.PersonModel;
import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "administrators")
public class AdministratorsModel extends AuditModel {
    @Id
    @Column(name = "employee_number", length = 15, nullable = false, unique = true)
    private String employeeNumber;

    @ManyToOne
    @JoinColumn(name = "fk_person",nullable = false)
    private PersonModel person;

    @ManyToOne
    @JoinColumn(name = "fk_user",  nullable = false)
    private UserModel user;
}
