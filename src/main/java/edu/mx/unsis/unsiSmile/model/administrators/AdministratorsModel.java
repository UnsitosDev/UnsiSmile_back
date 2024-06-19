package edu.mx.unsis.unsiSmile.model.administrators;

import edu.mx.unsis.unsiSmile.authenticationProviders.model.UserModel;
import edu.mx.unsis.unsiSmile.model.PersonModel;
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
@Table(name = "administrators")
public class AdministratorsModel {
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
