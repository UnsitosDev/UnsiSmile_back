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
@Table(name = "religions")
public class ReligionModel {

    @Id
    @Column(name = "id_religion")
    private Long idReligion;

    @Column(name = "religion", length = 100, unique = true)
    private String religion;

}
