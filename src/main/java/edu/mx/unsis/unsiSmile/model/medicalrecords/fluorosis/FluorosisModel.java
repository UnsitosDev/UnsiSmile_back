package edu.mx.unsis.unsiSmile.model.medicalrecords.fluorosis;

import edu.mx.unsis.unsiSmile.model.medicalrecords.treatments.TreatmentDetailModel;
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
@Table(name = "fluorosis")
public class FluorosisModel extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_fluorosis")
    private Long idFluorosis;

    @OneToOne
    @JoinColumn(name = "fk_treatment_detail", referencedColumnName = "id_treatment_detail")
    private TreatmentDetailModel treatmentDetail;

    @OneToMany(mappedBy = "fluorosis", cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<FluorosisToothConditionAssignmentModel> toothConditionAssignments;

    @OneToMany(mappedBy = "fluorosis", cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<FluorosisToothfaceConditionsAssignmentModel> toothFaceConditionsAssignments;
}