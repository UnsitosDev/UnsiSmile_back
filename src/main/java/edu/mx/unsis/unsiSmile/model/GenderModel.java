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
@Table(name = "genders")
public class GenderModel {

    public enum GenderType {
        MALE, FEMALE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_gender")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", unique = true, length = 100)
    private GenderType gender;

}

