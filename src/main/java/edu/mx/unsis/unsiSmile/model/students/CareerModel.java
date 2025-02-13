package edu.mx.unsis.unsiSmile.model.students;

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
@Table(name = "careers")
public class CareerModel  extends AuditModel {

    @Id
    @Column(name = "id_career", nullable = false, unique = true)
    private String idCareer;
    @Column(name = "career", nullable = false, unique = true)
    private String career;

}

