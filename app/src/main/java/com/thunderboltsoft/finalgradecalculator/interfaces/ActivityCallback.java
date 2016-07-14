package com.thunderboltsoft.finalgradecalculator.interfaces;

import com.thunderboltsoft.finalgradecalculator.models.Assessment;

import java.util.List;

/**
 * Interface containing definitions of the callback methods available for fragments to call on the main activity.
 *
 * @author Thushan Perera
 */
public interface ActivityCallback {

    /**
     * Disable the ViewPager and SlidingTabStrip.
     */
    void disableViewPager();

    /**
     * Enable the ViewPager and SlidingTabStrip.
     */
    void enableViewPager();

    /**
     * Switch ViewPager to the Assessments tab.
     */
    void switchToAssessmentsTab();

    /**
     * Get and return the final grade needed to achieve the desired grade.
     *
     * @param desiredGrade the desired grade as percentage
     * @return the grade needed as a percentage
     */
    double getGradeNeeded(double desiredGrade);

    /**
     * Override of getGradeNeeded method.
     *
     * @param currentGrade     current grade they are getting as a percentage
     * @param desiredGrade     the desired grade as a percentage
     * @param finalGradeWeight the weighting of the final assessment
     * @return the grade needed as a percentage
     */
    double getGradeNeeded(double currentGrade, double desiredGrade, double finalGradeWeight);

    /**
     * Get and return the current grades for all the assessments entered by user.
     *
     * @return the current weighted grade
     */
    double getCurrentGrade();

    /**
     * Gets and returns the list of assessments entered by the user.
     *
     * @return list of assessments
     */
    List<Assessment> getAssessments();

    /**
     * Should the FloatingActionButton in the Assessments List fragment be disabled or not?
     *
     * @param shouldDisable true = if should disable, else false
     */
    void shouldDisableFab(boolean shouldDisable);

    /**
     * Clean the list of assessments, therefore clears the ListAdapter then the list view after notifyDataSetChanged().
     */
    void cleanAssessments();
}
