package com.thunderboltsoft.finalgradecalculator;

import java.util.ArrayList;
import java.util.List;

public class ListAssessments {
    private List<Assessment> assessments;

    public ListAssessments() {
        assessments = new ArrayList<>();
    }

    public void addAssessment(Assessment assessment) {
        assessments.add(assessment);
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
