package edu.mx.unsis.unsiSmile.model;

import jakarta.persistence.*;

@Entity
public class StudentSemester {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idStudentSemester;

    @ManyToOne
    @JoinColumn(name = "fk_student")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "fk_semester")
    private Semester semester;

    // Constructores, getters y setters

    public StudentSemester() {
    }

    public StudentSemester(Student student, Semester semester) {
        this.student = student;
        this.semester = semester;
    }

    public int getIdStudentSemester() {
        return idStudentSemester;
    }

    public void setIdStudentSemester(int idStudentSemester) {
        this.idStudentSemester = idStudentSemester;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }
}
