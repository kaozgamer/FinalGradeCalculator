package com.thunderboltsoft.finalgradecalculator.models;

/**
 * Model for an assessment.
 * Contains the mGrade received and weighting of that assessment.
 *
 * @author Thushan Perera
 */
public class Assessment {

    /**
     * Holds the weighting for this assessment as a percentage.
     */
    private double mWeight;

    /**
     * Holds the grade received for this assessment as a percentage.
     */
    private double mGrade;

    /**
     * Holds boolean indicating if the weight and grade values are valid or not.
     */
    private boolean mValid = true;

    /**
     * Public constructor.
     *
     * @param weight weight of the assessment
     * @param grade  grade received for the assessment
     */
    public Assessment(double weight, double grade) {
        setWeight(weight);
        setGrade(grade);
    }

    /**
     * Sets the weight for the assessment.
     * Also checks if the weight entered is valid or not.
     *
     * @param weight weight of the assessment
     */
    public void setWeight(double weight) {
        if ((weight <= 0) || (weight > 100)) {
            mValid = false;
        }
        mWeight = weight;
    }

    /**
     * Returns the weight of the assessment.
     *
     * @return weight of assessment
     */
    public double getWeight() {
        return mWeight;
    }

    /**
     * Sets the grade received for the assessment.
     * Converts the given mark and total marks available to a percentage.
     *
     * @param mark       mark received for assessment
     * @param totalMarks total marks available for assessment
     */
    public void setGrade(double mark, double totalMarks) {
        mGrade = mark / totalMarks;
        if ((mGrade > 100) || (mGrade < 0)) {
            mValid = false;
        }
    }

    /**
     * Sets the grade received for the assessment.
     *
     * @param grade grade as percentage for the assessment
     */
    public void setGrade(double grade) {
        mGrade = grade;
        if ((mGrade > 100) || (mGrade < 0)) {
            mValid = false;
        }
    }

    /**
     * Returns the current grade for the assessment.
     *
     * @return grade for assessment
     */
    public double getGrade() {
        return mGrade;
    }

    /**
     * Returns boolean indicating if values in this assessment is valid or not.
     *
     * @return true if valid, else false
     */
    public boolean isValid() {
        return mValid;
    }

    /**
     * Checks if the grade entered is in the valid ranges or not.
     *
     * @return true if the grade is valid, else false
     */
    public boolean isGradeValid() {
        return !((mGrade > 100) || (mGrade < 0));
    }

    /**
     * Checks if the weight entered is in the valid ranges or not.
     *
     * @return true if weight is valid, else false
     */
    public boolean isWeightValid() {
        return !((mWeight <= 0) || (mWeight > 100));
    }
}
