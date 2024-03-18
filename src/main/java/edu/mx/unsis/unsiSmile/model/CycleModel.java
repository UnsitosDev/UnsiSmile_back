package edu.mx.unsis.unsiSmile.model;

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
@Table(name = "cycles")
public class CycleModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cycle")
    private Long idCycle;
    @Column(name = "cycle_name", nullable = false, unique = true)
    private String cycleName;

    @Column(name = "status", nullable = false)
    private Boolean status;
}
