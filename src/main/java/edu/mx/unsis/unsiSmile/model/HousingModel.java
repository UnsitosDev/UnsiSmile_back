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
@Table(name = "housings")
public class HousingModel {

    @Id
    @Column(name = "id_housing", length = 2)
    private String idHousing;

    @Column(name = "category", columnDefinition = "TEXT")
    private String category;

}
