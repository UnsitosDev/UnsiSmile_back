package edu.mx.unsis.unsiSmile.model;

import jakarta.persistence.*;
import edu.mx.unsis.unsiSmile.authenticationProviders.model.UserModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "students")
public class Student extends Person{

    @Id
    @Column(name = "enrollment", nullable = false, unique = true)
    private String enrollment;

    @ManyToOne
    @JoinColumn(name = "fk_user", nullable = false)
    private UserModel user;
}
