package edu.mx.unsis.unsiSmile.model.addresses;

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
@Table(name = "nationalities")
public class NationalityModel {

    @Id
    @Column(name = "id_nationality")
    private Long idNationality;

    @Column(name = "nationality", length = 100, unique = true)
    private String nationality;

}
