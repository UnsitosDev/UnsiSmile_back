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
@Table(name = "neighborhoods")
public class NeighborhoodModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_neighborhood")
    private Long idNeighborhood;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "fk_locality", referencedColumnName = "id_locality", nullable = false)
    private LocalityModel locality;
}