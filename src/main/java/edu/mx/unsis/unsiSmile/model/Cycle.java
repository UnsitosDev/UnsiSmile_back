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
public class Cycle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cycle")
    private Long idCycle;
    @Column(name = "cycle_name", nullable = false, unique = true)
    private String cycleName;

    @Enumerated(EnumType.STRING)
    private CycleStatus status = CycleStatus.ACTIVE;

    public enum CycleStatus {
        ACTIVE, INACTIVE
    }
}
