package edu.mx.unsis.unsiSmile.model.medicalHistories;

import java.time.LocalDate;
import java.util.List;

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
@Table(name = "tooth")
public class ToothModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tooth")
    private Long idTooth;

    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    @ManyToOne
    @JoinColumn(name = "fk_odontogram")
    private OdontogramModel odontogram;

    @OneToMany(mappedBy = "tooth", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ToothConditionRel> toothConditions;

    @OneToMany(mappedBy = "tooth", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ToothFaceModel> toothFaces;
}
