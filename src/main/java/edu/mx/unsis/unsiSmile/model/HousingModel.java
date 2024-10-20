package edu.mx.unsis.unsiSmile.model;

import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "housings")
public class HousingModel extends AuditModel {

    @Id
    @Column(name = "id_housing", length = 2)
    private String idHousing;

    @Column(name = "category", columnDefinition = "TEXT")
    private String category;

}
