package com.thunderboltsoft.finalgradecalculator;

import java.util.ArrayList;
import java.util.List;

public class ListAssessments {
    private List<Assessment> assessments;
    private double currentGrade;
    private double examWeight;

    public ListAssessments() {
        assessments = new ArrayList<>();
    }

    public void addAssessment(Assessment assessment) {
        assessments.add(assessment);

        calcCurrentGrade();
    }

    public String[] getAssessmentsDetailList() {
        String[] assessmentsDetailList = new String[assessments.size()];
        Assessment[] assessmentsList = assessments.toArray(new Assessment[assessments.size()]);

        for (int i = 0; i < assessmentsList.length; i++) {
            String detail = "Grade: " + String.format("%.2f", assessmentsList[i].getGrade()) + "% with Weight: " + String.format("%.2f", assessmentsList[i].getWeight()) + "%";
            assessmentsDetailList[i] = detail;
        }

        return assessmentsDetailList;
    }

    private void calcCurrentGrade() {
        double totalWeight = 0;
        double sumProductWeightedGrades = 0;

        for (Assessment a : assessments) {
            double tempGrade = a.getGrade();
            double tempWeight = a.getWeight();

            sumProductWeightedGrades += (tempGrade * tempWeight);
            totalWeight += tempWeight;
        }

        this.examWeight = 100 - totalWeight;
        this.currentGrade = sumProductWeightedGrades / totalWeight;
    }

    public double calcGradeNeeded(double desiredGrade) {
        return ((100 * desiredGrade) - (currentGrade * (100 - examWeight))) / examWeight;
    }

    public double getCurrentGrade() {
        return this.currentGrade;
    }

    public List<Assessment> getAssessments() {
        return assessments;
    }

    public Assessment[] getAssessmentsArray() {
        Assessment[] array = new Assessment[assessments.size()];
        return assessments.toArray(array);
    }

    public Assessment getAssessment(int index) {
        return assessments.get(index);
    }
}
