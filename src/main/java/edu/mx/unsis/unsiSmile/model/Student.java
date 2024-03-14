package edu.mx.unsis.unsiSmile.model;

import jakarta.persistence.*;
import edu.mx.unsis.unsiSmile.authenticationProviders.model.UserModel;

@Entity
public class Student {

    @Id
    private String enrollment;

    @ManyToOne
    @JoinColumn(name = "fk_user")
    private UserModel user;

    @ManyToOne
    @JoinColumn(name = "fk_person")
    private Person person;

    // Constructors, getters y setters

    public Student() {
    }

    public Student(String enrollment, UserModel user, Person person) {
        this.enrollment = enrollment;
        this.user = user;
        this.person = person;
    }

    public String getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(String enrollment) {
        this.enrollment = enrollment;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}

