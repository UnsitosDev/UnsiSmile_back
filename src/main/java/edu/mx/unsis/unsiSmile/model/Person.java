package edu.mx.unsis.unsiSmile.model;

import jakarta.persistence.*;
import edu.mx.unsis.unsiSmile.authenticationProviders.model.UserModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "people")
public abstract class Person {

    @Id
    @Column(name = "curp", length = 20)
    private String curp;

    @Column(name = "first_name",nullable = false, length = 50)
    private String firstName;

    @Column(name = "second_name", length = 50)
    private String secondName;

    @Column(name = "first_lastname",nullable = false, length = 50)
    private String firstLastName;

    @Column(name = "second_lastname", length = 50)
    private String secondLastName;

    @Column(name = "phone", length = 10)
    private String phone;

    @Column(name = "birth_date",nullable = false)
    private Date birthDate;

    @Column(name = "email",unique = true, length = 200)
    private String email;

    @ManyToOne
    @JoinColumn(name = "fk_gender")
    private Gender gender;

}

