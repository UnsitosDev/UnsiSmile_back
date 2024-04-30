package edu.mx.unsis.unsiSmile.model.addresses;

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
@Table(name = "localities")
public class LocalityModel {

    @Id
    @Column(name = "id_locality", length = 5)
    private String idLocality;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "postal_code", length = 5)
    private String postalCode;

    @ManyToOne
    @JoinColumn(name = "fk_municipality", referencedColumnName = "id_municipality", nullable = false)
    private MunicipalityModel municipality;

}
