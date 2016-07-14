package com.thunderboltsoft.finalgradecalculator.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.thunderboltsoft.finalgradecalculator.R;
import com.thunderboltsoft.finalgradecalculator.interfaces.ActivityCallback;

import java.util.Locale;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

/**
 * The main fragment that allows user to enter/view the current weighted grade and the desired grade.
 * It will then display what the user needs to get in the remaining assessment to achieve the desired grade.
 *
 * @author Thushan Perera
 */
public class MainFragment extends Fragment {

    /**
     * Unique ID for the MaterialShowcaseView.
     */
    final private String SHOWCASE_ID = "TEST";

    /**
     * EditText where we display/store the user's desired grade.
     */
    private EditText mEditTextDesiredGrade;

    /**
     * Stores the user's current grade. Supplied by the user or calculated by the list.
     */
    private EditText mTxtCurrentGrade;

    /**
     * Displays the grade the user needs to achieve in order to achieve their desired grade.
     */
    private TextView mDesiredExamGrade;

    /**
     * SwitchCompat that indicates whether or not the user knows their current weighted grade.
     */
    private SwitchCompat mSwitchCompat;

    /**
     * Interface to allow callback to the activity.
     */
    private ActivityCallback mCallbackActivity;

    /**
     * Shows the grade needed.
     */
    private TextView mTextGradeNeeded;

    /**
     * EditText that user use to enter the weighting of the final exam.
     */
    private EditText mTxtFinalGradeWeight;

    /**
     * Required default constructor
     */
    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_main, container, false);

        // Find and assign the views to global variables
        final TextInputLayout finalGradeWeightLinearLayout = (TextInputLayout) view.findViewById(R.id.input_layout_final_weight);
        mTxtFinalGradeWeight = (EditText) view.findViewById(R.id.editTextFinalGradeWeight);
        mTextGradeNeeded = (TextView) view.findViewById(R.id.textViewResult);
        mDesiredExamGrade = (TextView) view.findViewById(R.id.textViewDesiredResult);
        mSwitchCompat = (SwitchCompat) view.findViewById(R.id.switchCurrentGrade);
        mTxtCurrentGrade = (EditText) view.findViewById(R.id.textView);
        mEditTextDesiredGrade = (EditText) view.findViewById(R.id.editTextDesiredGrade);

        mTxtFinalGradeWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if ((!mEditTextDesiredGrade.getText().toString().isEmpty()) && (!mTxtCurrentGrade.getText().toString().isEmpty()) && (!mTxtFinalGradeWeight.getText().toString().isEmpty())) {
                    if (mSwitchCompat.isChecked()) {
                        calculateAndShowGradeNeeded(); // Make the app more responsive
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        mTxtCurrentGrade.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if ((!mEditTextDesiredGrade.getText().toString().isEmpty()) && (!mTxtFinalGradeWeight.getText().toString().isEmpty()) && (!mTxtCurrentGrade.getText().toString().isEmpty())) {
                    if (mSwitchCompat.isChecked()) {
                        calculateAndShowGradeNeeded();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        String currentGrade = String.format(Locale.getDefault(), "%.2f", mCallbackActivity.getCurrentGrade());
        mTxtCurrentGrade.setText(currentGrade); // Set user's current grade to 2 decimal places

        mSwitchCompat.setChecked(true); // Default is assuming user knows their grade
        mSwitchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    finalGradeWeightLinearLayout.setVisibility(View.INVISIBLE);

                    // Switch to the assessments tab
                    mCallbackActivity.switchToAssessmentsTab();

                    cleanMainFragment();

                    mCallbackActivity.shouldDisableFab(false);
                    mTxtCurrentGrade.setEnabled(false);

                    mCallbackActivity.enableViewPager();
                } else {
                    finalGradeWeightLinearLayout.setVisibility(View.VISIBLE);

                    cleanMainFragment();

                    mCallbackActivity.shouldDisableFab(true);
                    mTxtCurrentGrade.setEnabled(true);

                    mCallbackActivity.disableViewPager();
                }
            }
        });

        // As user's enters desired grade, calculate final exam grade needed to achieve that
        mEditTextDesiredGrade.addTextChangedListener(new TextWatcher() { // Text change listener so no need for a button
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!mEditTextDesiredGrade.getText().toString().equals("")) {
                    double gradeNeeded;

                    // Decide which to calculate
                    if (mTxtFinalGradeWeight.getText().toString().isEmpty()) {
                        gradeNeeded = mCallbackActivity.getGradeNeeded(Double.parseDouble(mEditTextDesiredGrade.getText().toString()));
                    } else {
                        gradeNeeded = mCallbackActivity.getGradeNeeded(Double.parseDouble(mTxtCurrentGrade.getText().toString()), Double.parseDouble(mEditTextDesiredGrade.getText().toString()), Double.parseDouble(mTxtFinalGradeWeight.getText().toString()));
                    }

                    String neededGrade = String.format(Locale.getDefault(), "%.2f", gradeNeeded) + "%";
                    mTextGradeNeeded.setText(neededGrade);

                    neededGrade = "You need at least " + String.format(Locale.getDefault(), "%.2f", gradeNeeded) + "% to achieve a course grade of " + String.format(Locale.getDefault(), "%.2f", Double.parseDouble(mEditTextDesiredGrade.getText().toString())) + "%";
                    mDesiredExamGrade.setText(neededGrade);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return view;
    }

    /**
     * Takes the values of the EditText views, calculates and shows the grade needed.
     */
    private void calculateAndShowGradeNeeded() {
        double gradeNeeded = mCallbackActivity.getGradeNeeded(Double.parseDouble(mTxtCurrentGrade.getText().toString()),
                Double.parseDouble(mEditTextDesiredGrade.getText().toString()),
                Double.parseDouble(mTxtFinalGradeWeight.getText().toString()));

        String neededGrade = String.format(Locale.getDefault(), "%.2f", gradeNeeded) + "%";
        mTextGradeNeeded.setText(neededGrade);

        neededGrade = "You need at least " + String.format(Locale.getDefault(), "%.2f", gradeNeeded) + "% to achieve a course grade of " + String.format(Locale.getDefault(), "%.2f", Double.parseDouble(mEditTextDesiredGrade.getText().toString())) + "%";
        mDesiredExamGrade.setText(neededGrade);
    }

    /**
     * Cleans all the values on the main fragment view.
     */
    private void cleanMainFragment() {
        mTxtFinalGradeWeight.setText("");
        mTxtCurrentGrade.setText("");
        mEditTextDesiredGrade.setText("");

        mTextGradeNeeded.setText("");
        mDesiredExamGrade.setText(getResources().getString(R.string.desired_result_description));

        mCallbackActivity.cleanAssessments();
    }

    /**
     * Updates the TextView containing the user's current weighted grade.
     *
     * @param currentGrade current weighted grade
     */
    public void updateCurrentGrade(double currentGrade) {
        mTxtCurrentGrade.setText(String.format(Locale.getDefault(), "%.2f", currentGrade)); // 2 decimal places

        if (!mEditTextDesiredGrade.getText().toString().isEmpty()) {
            double gradeNeeded = mCallbackActivity.getGradeNeeded(Double.parseDouble(mEditTextDesiredGrade.getText().toString()));

            String neededGrade = String.format(Locale.getDefault(), "%.2f", gradeNeeded) + "%";
            mTextGradeNeeded.setText(neededGrade);

            neededGrade = "You need at least " + String.format(Locale.getDefault(), "%.2f", gradeNeeded) + "% to achieve a course grade of " + String.format(Locale.getDefault(), "%.2f", Double.parseDouble(mEditTextDesiredGrade.getText().toString())) + "%";
            mDesiredExamGrade.setText(neededGrade);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()) {
            onResume(); // Run onResume() once the view is visible to the user
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!getUserVisibleHint()) {
            return;
        }

        // Run the mini tutorial the first time the user views this fragment
        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(500); // half second between each showcase view

        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(getActivity(), SHOWCASE_ID);
        sequence.setConfig(config);
        sequence.addSequenceItem(mSwitchCompat, "Switch this off if you do not know your current grade\n\nSwiping to the Assessment tab will then be available", "GOT IT");
        sequence.addSequenceItem(mTxtCurrentGrade, "Enter your current weighted unit grade", "GOT IT");
        sequence.addSequenceItem(mEditTextDesiredGrade, "Enter the grade that you are aiming for", "GOT IT");
        sequence.addSequenceItem(mTxtFinalGradeWeight, "Enter the weight of the final assessment in your unit", "GOT IT");
        sequence.start();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallbackActivity = (ActivityCallback) activity;
    }
}
