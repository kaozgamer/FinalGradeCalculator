package com.thunderboltsoft.finalgradecalculator.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds a list of assessments.
 *
 * @author Thushan Perera
 */
public class ListAssessments {

    /**
     * List of assessments.
     */
    private List<Assessment> mAssessments;

    /**
     * Current weighted grade calculated from all the assessments in the list.
     */
    private double mCurrentGrade;

    /**
     * The final exam weight.
     */
    private double mExamWeight;

    /**
     * Public constructor.
     */
    public ListAssessments() {
        mAssessments = new ArrayList<>();
    }

    /**
     * Adds a new assessment to the list.
     *
     * @param assessment assessment to add
     */
    public void addAssessment(Assessment assessment) {
        mAssessments.add(assessment);
        calcCurrentGrade();
    }

    /**
     * Calculates the current weighted grade of all the assessments in the list.
     */
    private void calcCurrentGrade() {
        double totalWeight = 0;
        double sumProductWeightedGrades = 0;

        for (Assessment a : mAssessments) { // Go through each assessment
            double tempGrade = a.getGrade();
            double tempWeight = a.getWeight();

            sumProductWeightedGrades += (tempGrade * tempWeight); // Normalize the mark
            totalWeight += tempWeight;
        }

        this.mExamWeight = 100 - totalWeight;
        this.mCurrentGrade = sumProductWeightedGrades / totalWeight;
    }

    /**
     * Calculates the final exam grade needed to achieve the desired grade.
     *
     * @param desiredGrade the desired grade
     * @return the grade needed
     */
    public double calcGradeNeeded(double desiredGrade) {
        return ((100 * desiredGrade) - (mCurrentGrade * (100 - mExamWeight))) / mExamWeight;
    }

    /**
     * Gets the weighted grade of the assessments currently in the list.
     *
     * @return weighted grade
     */
    public double getCurrentGrade() {
        return this.mCurrentGrade;
    }

    /**
     * Returns the list of all the assessments.
     *
     * @return list of assessments
     */
    public List<Assessment> getAssessments() {
        return mAssessments;
    }
}
