package edu.mx.unsis.unsiSmile.model.patients.demographics;

import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "marital_statuses")
public class MaritalStatusModel extends AuditModel {

    @Id
    @Column(name = "id_marital_status")
    private Long idMaritalStatus;

    @Column(name = "marital_status", length = 100, unique = true)
    private String maritalStatus;
}