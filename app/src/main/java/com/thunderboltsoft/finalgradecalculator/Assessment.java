package com.thunderboltsoft.finalgradecalculator;


public class Assessment {
    private double weight;
    private double grade;
    private boolean valid = true;

    public Assessment(double weight, double grade) {
        setWeight(weight);
        setGrade(grade);
    }

    public void setWeight(double weight) {
        if ((weight <= 0) || (weight > 100)) {
            valid = false;
        }
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public void setGrade(double mark, double totalMarks) {
        this.grade = mark / totalMarks;
        if ((this.grade > 100) || (this.grade < 0)) {
            valid = false;
        }
    }

    public void setGrade(double grade) {
        this.grade = grade;
        if ((this.grade > 100) || (this.grade < 0)) {
            valid = false;
        }
    }

    public double getGrade() {
        return grade;
    }

    public boolean isValid() {
        return valid;
    }
}
