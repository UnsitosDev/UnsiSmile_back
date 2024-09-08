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
@Table(name = "tooth_faces")
public class ToothFaceModel {

    @Id
    @Column(name = "id_tooth_face", length = 3)
    private String idToothFace;

    @Column(name = "description", nullable = false)
    private String description;
}