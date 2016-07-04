package com.thunderboltsoft.finalgradecalculator.interfaces;

import com.thunderboltsoft.finalgradecalculator.models.Assessment;

import java.util.List;

/**
 * Interface containing definitions of the callback methods available for fragments to call on the main activity.
 *
 * @author Thushan Perera
 */
public interface ActivityCallback {
    void disableViewPager();

    void enableViewPager();

    void switchToAssessmentsTab();

    double getGradeNeeded(double desiredGrade);

    double getGradeNeeded(double desiredGrade, double finalGradeWeight);

    double getCurrentGrade();

    List<Assessment> getAssessments();
}
