package edu.mx.unsis.unsiSmile.model;

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
@Table(name = "students_semesters")
public class StudentSemester {
    @Id
    @ManyToOne
    @JoinColumn(name = "fk_student")
    @Column(nullable = false)
    private Student student;
    @Id
    @ManyToOne
    @JoinColumn(name = "fk_semester")
    @Column(nullable = false)
    private Semester semester;
}
