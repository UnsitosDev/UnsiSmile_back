package edu.mx.unsis.unsiSmile.model.addresses;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_locality")
    private Long idLocality;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "postal_code", length = 5)
    private String postalCode;

    @ManyToOne
    @JoinColumn(name = "fk_municipality", referencedColumnName = "id_municipality", nullable = false)
    private MunicipalityModel municipality;
}