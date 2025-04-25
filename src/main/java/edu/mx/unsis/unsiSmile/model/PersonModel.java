package edu.mx.unsis.unsiSmile.model;

import edu.mx.unsis.unsiSmile.model.patients.GuardianModel;
import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "people")
public class PersonModel extends AuditModel {

    @Id
    @Column(name = "curp", length = 20, unique = true, nullable = false)
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

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    @Builder.Default
    private Set<GuardianModel> guardians = new HashSet<>();

    public String getFullName() {
        StringBuilder fullName = new StringBuilder()
                .append(firstName).append(" ");

        if (StringUtils.hasText(secondName)) {
            fullName.append(secondName).append(" ");
        }

        return fullName
                .append(firstLastName)
                .append(" ")
                .append(secondLastName)
                .toString();
    }
}