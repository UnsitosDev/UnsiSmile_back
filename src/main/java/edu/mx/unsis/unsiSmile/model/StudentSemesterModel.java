package edu.mx.unsis.unsiSmile.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "students_semesters")
public class StudentSemesterModel {

    @Id
    @ManyToOne
    @JoinColumn(name = "fk_student")
    private StudentModel student;
    @Id
    @ManyToOne
    @JoinColumn(name = "fk_semester")
    private SemesterModel semester;
}
