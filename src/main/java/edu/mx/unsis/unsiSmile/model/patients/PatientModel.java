package edu.mx.unsis.unsiSmile.model.patients;

import edu.mx.unsis.unsiSmile.model.PersonModel;
import edu.mx.unsis.unsiSmile.model.addresses.AddressModel;
import edu.mx.unsis.unsiSmile.model.addresses.NationalityModel;
import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "patients")
public class PatientModel extends AuditModel {

    @Id
    @GenericGenerator(name = "uuid2")
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "id_patient")
    private UUID idPatient;

    @Column(name = "admission_date")
    private LocalDate admissionDate;


    @Column(name = "is_minor")
    private Boolean isMinor;

    @Column(name = "has_disability")
    private Boolean hasDisability;

    @ManyToOne
    @JoinColumn(name = "fk_nationality", referencedColumnName = "id_nationality")
    private NationalityModel nationality;

    @ManyToOne
    @JoinColumn(name = "fk_person", referencedColumnName = "curp", nullable = false)
    private PersonModel person;

    @ManyToOne
    @JoinColumn(name = "fk_address", referencedColumnName = "id_address", nullable = false)
    private AddressModel address;

    @ManyToOne
    @JoinColumn(name = "fk_marital_status", referencedColumnName = "id_marital_status")
    private MaritalStatusModel maritalStatus;

    @ManyToOne
    @JoinColumn(name = "fk_occupation", referencedColumnName = "id_occupation")
    private OccupationModel occupation;

    @ManyToOne
    @JoinColumn(name = "fk_ethnic_group", referencedColumnName = "id_ethnic_group")
    private EthnicGroupModel ethnicGroup;

    @ManyToOne
    @JoinColumn(name = "fk_religion", referencedColumnName = "id_religion")
    private ReligionModel religion;

    @ManyToOne
    @JoinColumn(name = "fk_guardian", referencedColumnName = "id_guardian")
    private GuardianModel guardian;

    @Column(name = "file_number", unique = true)
    private Long fileNumber;
}
