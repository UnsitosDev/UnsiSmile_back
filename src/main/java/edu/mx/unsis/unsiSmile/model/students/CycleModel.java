package edu.mx.unsis.unsiSmile.model.students;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
