package edu.mx.unsis.unsiSmile.model;

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
@Table(name = "occupations")
public class OccupationModel {

    @Id
    @Column(name = "id_occupation")
    private Long idOccupation;

    @Column(name = "occupation", length = 100, unique = true)
    private String occupation;

}
