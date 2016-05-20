package com.thunderboltsoft.finalgradecalculator;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewAssessment extends Fragment {

    private Button btnAdd;
    public EditText weight;
    public EditText grade;

    public Assessment p;

    public NewAssessment() {
        // Required empty public constructor
    }

    public void setFromListView(Assessment p) {
        this.p = p;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_new_assessment, container, false);

        btnAdd = (Button) view.findViewById(R.id.btnAdd);
        weight = (EditText) view.findViewById(R.id.txtEnterWeight);
        grade = (EditText) view.findViewById(R.id.txtEnterGrade);

        if (p != null) {
            weight.setText(String.valueOf(p.getWeight()));
            grade.setText(String.valueOf(p.getGrade()));
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Switch back to the main fragment
                ViewPagerContainerFragment viewPagerFragment = new ViewPagerContainerFragment();
                viewPagerFragment.setAssessmentView();

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_screen, viewPagerFragment).commit();

                // Get the values from the EditText fields
                double weightDouble = Double.parseDouble(weight.getText().toString());
                double gradeDouble = Double.parseDouble(grade.getText().toString());

                Assessment newAssessment = new Assessment(weightDouble, gradeDouble);

                // Check input validity, then decide whether to send it to activity or not
                MainActivity activityRef = (MainActivity) getActivity();
                if (newAssessment.isValid()) {
                    activityRef.sendAssessment(newAssessment);
                } else {
                    Toast.makeText(getActivity(), "Invalid values detected", Toast.LENGTH_SHORT).show();
                }

                // Hide the soft keyboard - annoying
                InputMethodManager imm = (InputMethodManager) activityRef.getBaseContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });

        return view;
    }
}
