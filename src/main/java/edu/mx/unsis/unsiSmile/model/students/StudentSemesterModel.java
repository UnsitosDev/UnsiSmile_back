package edu.mx.unsis.unsiSmile.model.students;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
