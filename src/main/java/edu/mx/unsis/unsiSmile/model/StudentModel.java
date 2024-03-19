package edu.mx.unsis.unsiSmile.model;

import jakarta.persistence.*;
import edu.mx.unsis.unsiSmile.authenticationProviders.model.UserModel;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "students")
public class StudentModel {

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
