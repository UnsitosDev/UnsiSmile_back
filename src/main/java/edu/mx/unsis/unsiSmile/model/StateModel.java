package edu.mx.unsis.unsiSmile.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "states")
public class StateModel {

    @Id
    @Column(name = "id_state", length = 2)
    private String idState;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

}
