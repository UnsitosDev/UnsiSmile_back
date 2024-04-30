package edu.mx.unsis.unsiSmile.model;

import java.time.LocalDate;

import edu.mx.unsis.unsiSmile.model.patients.GenderModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "people")
public class PersonModel {

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
    private LocalDate birthDate;

    @Column(name = "email",unique = true, length = 200)
    private String email;

    @ManyToOne
    @JoinColumn(name = "fk_gender")
    private GenderModel gender;

}

