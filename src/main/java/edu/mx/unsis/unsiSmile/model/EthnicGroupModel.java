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
@Table(name = "ethnic_groups")
public class EthnicGroupModel {

    @Id
    @Column(name = "id_ethnic_group")
    private Long idEthnicGroup;

    @Column(name = "ethnic_group", length = 100, unique = true)
    private String ethnicGroup;
}
