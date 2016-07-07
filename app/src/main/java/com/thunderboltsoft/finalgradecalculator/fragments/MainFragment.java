package com.thunderboltsoft.finalgradecalculator.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;

/**
 * The main fragment that allows user to enter/view the current weighted grade and the desired grade.
 * It will then display what the user needs to get in the remaining assessment to achieve the desired grade.
 *
 * @author Thushan Perera
 */
public class MainFragment extends Fragment {

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
     * Required default constructor
     */
    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_main, container, false);
        mTxtCurrentGrade = (EditText) view.findViewById(R.id.textView);

        String currentGrade = String.format(Locale.getDefault(), "%.2f", mCallbackActivity.getCurrentGrade());
        mTxtCurrentGrade.setText(currentGrade); // Set user's current grade to 2 decimal places

        final TextInputLayout finalGradeWeightLinearLayout = (TextInputLayout) view.findViewById(R.id.input_layout_final_weight);
        final EditText txtFinalGradeWeight = (EditText) view.findViewById(R.id.editTextFinalGradeWeight);

        mSwitchCompat = (SwitchCompat) view.findViewById(R.id.switchCurrentGrade);
        mSwitchCompat.setChecked(true); // Default is assuming user knows their grade
        mSwitchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    finalGradeWeightLinearLayout.setVisibility(View.INVISIBLE);

                    // Switch to the assessments tab
                    mCallbackActivity.switchToAssessmentsTab();

                    txtFinalGradeWeight.setText("");
                    mCallbackActivity.shouldDisableFab(false);
                    mTxtCurrentGrade.setEnabled(false);
                } else {
                    finalGradeWeightLinearLayout.setVisibility(View.VISIBLE);
                    txtFinalGradeWeight.setText("");
                    mCallbackActivity.shouldDisableFab(true);
                    mTxtCurrentGrade.setEnabled(true);
                }
            }
        });

        final TextView txtGradeNeeded = (TextView) view.findViewById(R.id.textViewResult);

        // As user's enters desired grade, calculate final exam grade needed to achieve that
        mEditTextDesiredGrade = (EditText) view.findViewById(R.id.editTextDesiredGrade);
        mEditTextDesiredGrade.addTextChangedListener(new TextWatcher() { // Text change listener so no need for a button
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!mEditTextDesiredGrade.getText().toString().equals("")) {
                    double gradeNeeded;

                    if (txtFinalGradeWeight.getText().toString().isEmpty()) {
                        gradeNeeded = mCallbackActivity.getGradeNeeded(Double.parseDouble(mEditTextDesiredGrade.getText().toString()));
                    } else {
                        gradeNeeded = mCallbackActivity.getGradeNeeded(Double.parseDouble(mTxtCurrentGrade.getText().toString()), Double.parseDouble(mEditTextDesiredGrade.getText().toString()), Double.parseDouble(txtFinalGradeWeight.getText().toString()));
                    }

                    String neededGrade = String.format(Locale.getDefault(), "%.2f", gradeNeeded) + "%";
                    txtGradeNeeded.setText(neededGrade);

                    mDesiredExamGrade = (TextView) view.findViewById(R.id.textViewDesiredResult);
                    neededGrade = "You need at least " + String.format(Locale.getDefault(), "%.2f", gradeNeeded) + "% to achieve a course grade of " + String.format(Locale.getDefault(), "%.2f", Double.parseDouble(mEditTextDesiredGrade.getText().toString())) + "%";
                    mDesiredExamGrade.setText(neededGrade);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        new MaterialShowcaseView.Builder(getActivity())
                .setTarget(mSwitchCompat)
                .setDismissText("GOT IT")
                .setContentText("This is some amazing feature you should know about")
                .setDelay(100) // optional but starting animations immediately in onCreate can make them choppy
                .singleUse("test") // provide a unique ID used to ensure it is only shown once
                .show();

        return view;
    }

    /**
     * Updates the TextView containing the user's current weighted grade.
     *
     * @param currentGrade current weighted grade
     */
    public void updateCurrentGrade(double currentGrade) {
        mTxtCurrentGrade.setText(String.format(Locale.getDefault(), "%.2f", currentGrade)); // 2 decimal places
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallbackActivity = (ActivityCallback) activity;
    }
}
