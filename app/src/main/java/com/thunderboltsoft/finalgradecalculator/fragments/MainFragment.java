package com.thunderboltsoft.finalgradecalculator.fragments;

import android.os.Bundle;
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
import com.thunderboltsoft.finalgradecalculator.activities.MainActivity;

import java.util.Locale;

/**
 * The main fragment that allows user to enter/view the current weighted grade and the desired grade.
 * It will then display what the user needs to get in the remaining assessment to achieve the desired grade.
 */
public class MainFragment extends Fragment {

    /**
     * EditText where we display/store the user's desired grade.
     */
    private EditText mEditTextDesiredGrade;

    /**
     * Stores the user's current grade. Supplied by the user or calculated by the list.
     */
    private EditText mTextView;

    /**
     * Displays the grade the user needs to achieve in order to achieve their desired grade.
     */
    private TextView mDesiredExamGrade;

    /**
     * Required default constructor
     */
    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final MainActivity main = (MainActivity) getActivity();

        final View view = inflater.inflate(R.layout.fragment_main, container, false);
        mTextView = (EditText) view.findViewById(R.id.textView);

        String currentGrade = String.format(Locale.getDefault(), "%.2f", main.getCurrentGrade());
        mTextView.setText(currentGrade); // Set user's current grade to 2 decimal places

        SwitchCompat switchCompat = (SwitchCompat) view.findViewById(R.id.switchCurrentGrade);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // TODO: Enable EditText to allow user to enter the current weighted grade
                }
            }
        });

        // As user's enters desired grade, calculate final exam grade needed to achieve that
        mEditTextDesiredGrade = (EditText) view.findViewById(R.id.editTextDesiredGrade);
        mEditTextDesiredGrade.addTextChangedListener(new TextWatcher() { // Text change listener so no need for a button
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!mEditTextDesiredGrade.getText().toString().equals("")) {
                    double gradeNeeded = main.getGradeNeeded(Double.parseDouble(mEditTextDesiredGrade.getText().toString()));

                    mDesiredExamGrade = (TextView) view.findViewById(R.id.textViewDesiredResult);
                    String neededGrade = "You need at least " + String.format(Locale.getDefault(), "%.2f", gradeNeeded) + "% to achieve a course grade of " + String.format(Locale.getDefault(), "%.2f", Double.parseDouble(mEditTextDesiredGrade.getText().toString())) + "%";
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
     * Updates the TextView containing the user's current weighted grade.
     *
     * @param currentGrade current weighted grade
     */
    public void updateCurrentGrade(double currentGrade) {
        mTextView.setText(String.format(Locale.getDefault(), "%.2f", currentGrade)); // 2 decimal places
    }
}
