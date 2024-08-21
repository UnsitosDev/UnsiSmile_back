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
@Table(name = "tooth_face")
public class ToothFaceModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tooth_face")
    private Long idTooth;

    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    @ManyToOne
    @JoinColumn(name = "fk_tooth")
    private ToothModel tooth;

    @OneToMany(mappedBy = "toothFace", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ToothfaceConditionRel> toothFaceConditions;

}
