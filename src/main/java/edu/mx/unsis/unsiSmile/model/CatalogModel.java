package edu.mx.unsis.unsiSmile.model;

import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "catalogs")
public class CatalogModel extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_catalog")
    private Long idCatalog;

    @Column(name = "catalog_name")
    private String catalogName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_question")
    private QuestionModel questionModel;
}
