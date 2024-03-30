package edu.mx.unsis.unsiSmile.model;

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
@Table(name = "municipalities")
public class MunicipalityModel {

    @Id
    @Column(name = "id_municipality", length = 4)
    private String idMunicipality;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "fk_state", referencedColumnName = "id_state", nullable = false)
    private StateModel state;

}
