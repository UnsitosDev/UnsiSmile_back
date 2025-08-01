package edu.mx.unsis.unsiSmile.model.patients;

import java.time.LocalDate;
import java.util.UUID;

import edu.mx.unsis.unsiSmile.model.patients.demographics.EthnicGroupModel;
import edu.mx.unsis.unsiSmile.model.patients.demographics.MaritalStatusModel;
import edu.mx.unsis.unsiSmile.model.patients.demographics.OccupationModel;
import edu.mx.unsis.unsiSmile.model.patients.demographics.ReligionModel;
import edu.mx.unsis.unsiSmile.model.people.PersonModel;
import edu.mx.unsis.unsiSmile.model.addresses.AddressModel;
import edu.mx.unsis.unsiSmile.model.patients.demographics.NationalityModel;
import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "patients")
public class PatientModel extends AuditModel {

    @Id
    @Column(name = "id_patient", unique = true, nullable = false)
    private String idPatient;

    @Column(name = "admission_date")
    private LocalDate admissionDate;

    @Column(name = "has_disability")
    private Boolean hasDisability;

    @ManyToOne
    @JoinColumn(name = "fk_nationality", referencedColumnName = "id_nationality")
    private NationalityModel nationality;

    @OneToOne(fetch = FetchType.LAZY)
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

    @Column(name = "medical_record_number", unique = true)
    private Long medicalRecordNumber;

    @PrePersist
    public void generateId() {
        if (idPatient == null) {
            idPatient = UUID.randomUUID().toString();
        }
    }
}
