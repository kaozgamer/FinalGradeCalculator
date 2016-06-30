package com.thunderboltsoft.finalgradecalculator.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.thunderboltsoft.finalgradecalculator.R;
import com.thunderboltsoft.finalgradecalculator.activities.MainActivity;
import com.thunderboltsoft.finalgradecalculator.models.Assessment;

/**
 * Represents the dialog fragment that will hold the "Add new assessment" fragment.
 * User can use this to enter the weight and grade of an assessment.
 *
 * @author Thushan Perera
 */
public class NewAssessmentDialogFragment extends DialogFragment {

    /**
     * The EditText box for user to enter the weighting of assessment.
     */
    private EditText mWeight;

    /**
     * The EditText box for user to enter the grade of the assessment.
     */
    private EditText mGrade;

    /**
     * The assessment to be added.
     */
    private Assessment mAssessment;

    /**
     * Sets the assessments from an existing assessment.
     *
     * @param assessment existing assessment
     */
    public void setFromListView(Assessment assessment) {
        mAssessment = assessment;
    }

    /**
     * Create a new NewAssessmentDialogFragment fragment.
     *
     * @param myIndex params
     * @return new fragment
     */
    public static NewAssessmentDialogFragment newInstance(int myIndex) {
        NewAssessmentDialogFragment newAssessmentDialogFragment = new NewAssessmentDialogFragment();

        // Just to test Bundles
        Bundle args = new Bundle();
        args.putInt("anIntToSend", myIndex);
        newAssessmentDialogFragment.setArguments(args);

        return newAssessmentDialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View view = inflater.inflate(R.layout.fragment_new_assessment, null);

        mWeight = (EditText) view.findViewById(R.id.txtEnterWeight);
        mGrade = (EditText) view.findViewById(R.id.txtEnterGrade);

        // If assessment is given, populate EditText values from that
        if (mAssessment != null) {
            mWeight.setText(String.valueOf(mAssessment.getWeight()));
            mGrade.setText(String.valueOf(mAssessment.getGrade()));
        }

        // Add OK and Cancel buttons to our dialog
        AlertDialog.Builder b = new AlertDialog.Builder(getActivity())
                .setTitle("Add Assessment Grade")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                double weightDouble = Double.parseDouble(mWeight.getText().toString());
                                double gradeDouble = Double.parseDouble(mGrade.getText().toString());

                                Assessment newAssessment = new Assessment(weightDouble, gradeDouble);

                                // Send the assessment to the activity to be added to the list of assessments
                                MainActivity activity = (MainActivity) getActivity();
                                if (newAssessment.isValid()) {
                                    activity.sendAssessment(newAssessment);
                                } else {
                                    Toast.makeText(getActivity(), "Invalid values detected", Toast.LENGTH_SHORT).show();
                                }

                                // Hide the soft keyboard - annoying
                                InputMethodManager imm = (InputMethodManager) activity.getBaseContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                            }
                        }
                )
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss(); // Do not do anything with the user entered values
                            }
                        }
                );

        b.setView(view);

        return b.create();
    }
}