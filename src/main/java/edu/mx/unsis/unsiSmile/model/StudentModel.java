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
@EqualsAndHashCode(callSuper=true)
public class StudentModel extends PersonModel {

    @Id
    @Column(name = "enrollment", nullable = false, unique = true)
    private String enrollment;

    @ManyToOne
    @JoinColumn(name = "fk_user", nullable = false)
    private UserModel user;
}
