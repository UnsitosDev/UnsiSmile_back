package edu.mx.unsis.unsiSmile.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name = "patients")
public class PatientModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_patient")
    private Long idPatient;

    @Column(name = "admission_date")
    private Date admissionDate;


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

}
