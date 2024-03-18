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
@Table(name = "careers")
public class Career {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_career")
    private String idCareer;
    @Column(name = "career", nullable = false, unique = true)
    private String career;

}

