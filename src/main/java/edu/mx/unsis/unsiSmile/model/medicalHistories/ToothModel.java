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
@Table(name = "teeth")
public class ToothModel {

    @Id
    @Column(name = "id_tooth", length = 3)
    private String idTooth;

    @Column(name = "is_adult")
    private boolean isAdult;

    @Column(name = "description", nullable = false)
    private String description;

}