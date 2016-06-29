package com.thunderboltsoft.finalgradecalculator;

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

/**
 * Created by Thushan on 29-Jun-16.
 */
public class NewAssessmentDialogFragment extends DialogFragment {
    private EditText weight;
    private EditText grade;
    public Assessment p;

    public void setFromListView(Assessment p) {
        this.p = p;
    }

    public static NewAssessmentDialogFragment newInstance(int myIndex) {
        NewAssessmentDialogFragment newAssessmentDialogFragment = new NewAssessmentDialogFragment();

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

        weight = (EditText) view.findViewById(R.id.txtEnterWeight);
        grade = (EditText) view.findViewById(R.id.txtEnterGrade);

        if (p != null) {
            weight.setText(String.valueOf(p.getWeight()));
            grade.setText(String.valueOf(p.getGrade()));
        }

        AlertDialog.Builder b = new AlertDialog.Builder(getActivity())
                .setTitle("Add Assessment Grade")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Do something
                                double weightDouble = Double.parseDouble(weight.getText().toString());
                                double gradeDouble = Double.parseDouble(grade.getText().toString());

                                Assessment newAssessment = new Assessment(weightDouble, gradeDouble);

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
                                dialog.dismiss();
                            }
                        }
                );

        b.setView(view);

        return b.create();
    }
}
