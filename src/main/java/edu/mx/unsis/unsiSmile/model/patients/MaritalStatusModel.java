package edu.mx.unsis.unsiSmile.model.patients;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Table(name = "marital_statuses")
public class MaritalStatusModel {

    @Id
    @Column(name = "id_marital_status")
    private Long idMaritalStatus;

    @Column(name = "marital_status", length = 100, unique = true)
    private String maritalStatus;
}
