package edu.mx.unsis.unsiSmile.model.medicalHistories;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "odontogram")
public class OdontogramModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_odontogram")
    private Long idOdontogram;

    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    @OneToMany(mappedBy = "odontogram", cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<ToothConditionAssignmentModel> toothConditionAssignments;

    @OneToMany(mappedBy = "odontogram", cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<ToothFaceConditionModel> toothFaceConditions;
}
