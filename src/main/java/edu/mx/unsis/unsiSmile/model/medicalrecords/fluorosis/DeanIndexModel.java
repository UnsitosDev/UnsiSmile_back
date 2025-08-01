package edu.mx.unsis.unsiSmile.model.medicalrecords.fluorosis;

import edu.mx.unsis.unsiSmile.model.treatments.TreatmentDetailModel;
import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "dean_index")
public class DeanIndexModel extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dean_index")
    private Long idDeanIndex;

    @OneToOne
    @JoinColumn(name = "fk_treatment_detail", referencedColumnName = "id_treatment_detail", nullable = false)
    private TreatmentDetailModel treatmentDetail;

    @OneToMany(mappedBy = "deanIndex", cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<DeanIndexToothCodeModel> toothCodes;
}