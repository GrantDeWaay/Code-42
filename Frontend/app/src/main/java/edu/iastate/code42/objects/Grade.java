package edu.iastate.code42.objects;

import java.util.Objects;

public class Grade {
    private int id;
    private Assignment a;
    private User u;
    private double grade;
    private double gradeTotal;

    public Grade(int id, Assignment a, User u, double grade, double gradeTotal) {
        this.id = id;
        this.a = a;
        this.u = u;
        this.grade = grade;
        this.gradeTotal = gradeTotal;
    }

    public Grade(int id, User u, double grade, double gradeTotal) {
        this.id = id;
        this.u = u;
        this.grade = grade;
        this.gradeTotal = gradeTotal;
    }

    public Grade(int id, Assignment a, double grade, double gradeTotal) {
        this.id = id;
        this.a = a;
        this.grade = grade;
        this.gradeTotal = gradeTotal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Assignment getA() {
        return a;
    }

    public void setA(Assignment a) {
        this.a = a;
    }

    public User getUser() {
        return u;
    }

    public void setUser(User u) {
        this.u = u;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public double getGradeTotal() {
        return gradeTotal;
    }

    public void setGradeTotal(double gradeTotal) {
        this.gradeTotal = gradeTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grade grade1 = (Grade) o;
        return id == grade1.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, a, u, grade, gradeTotal);
    }
}
