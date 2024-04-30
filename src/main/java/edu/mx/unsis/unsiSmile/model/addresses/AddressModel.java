package edu.mx.unsis.unsiSmile.model.addresses;

import edu.mx.unsis.unsiSmile.model.HousingModel;
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
@Table(name = "addresses")
public class AddressModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_address")
    private Long idAddress;

    @Column(name = "street_number", length = 2)
    private String streetNumber;

    @Column(name = "interior_number", length = 2)
    private String interiorNumber;

    @ManyToOne
    @JoinColumn(name = "fk_housing", referencedColumnName = "id_housing", nullable = false)
    private HousingModel housing;

    @ManyToOne
    @JoinColumn(name = "fk_street", referencedColumnName = "id_street", nullable = false)
    private StreetModel street;

}
