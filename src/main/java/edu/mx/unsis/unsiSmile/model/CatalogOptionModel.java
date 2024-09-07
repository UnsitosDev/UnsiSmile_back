package edu.mx.unsis.unsiSmile.model;

import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
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
@Table(name = "catalog_options")
public class CatalogOptionModel extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_catalog_option")
    private Long idCatalogOption;

    @Column(name = "option_name")
    private String optionName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_catalog", nullable = false)
    private CatalogModel catalogModel;
}
