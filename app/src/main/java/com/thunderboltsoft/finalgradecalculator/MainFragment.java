package com.thunderboltsoft.finalgradecalculator;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import java.util.Locale;

public class MainFragment extends Fragment {

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final MainActivity main = (MainActivity) getActivity();

        final View view = inflater.inflate(R.layout.fragment_main, container, false);
        EditText txtView = (EditText) view.findViewById(R.id.textView);

        String currentGrade = String.format(Locale.getDefault(), "%.2f", main.getCurrentGrade());
        txtView.setText(currentGrade);

        final EditText editTxtDesiredGrade = (EditText) view.findViewById(R.id.editTextDesiredGrade);
        editTxtDesiredGrade.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!editTxtDesiredGrade.getText().toString().equals("")) {
                    double gradeNeeded = main.getGradeNeeded(Double.parseDouble(editTxtDesiredGrade.getText().toString()));

                    TextView desiredExamGrade = (TextView) view.findViewById(R.id.textViewDesiredResult);
                    String neededGrade = "You need at least " + String.format(Locale.getDefault(), "%.2f", gradeNeeded) + "% to achieve a course grade of " + String.format(Locale.getDefault(), "%.2f", Double.parseDouble(editTxtDesiredGrade.getText().toString())) + "%";
                    desiredExamGrade.setText(neededGrade);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                NewAssessment fragment = new NewAssessment();
                fragmentTransaction.replace(R.id.main_screen, fragment);
                fragmentTransaction.commit();
            }
        });

        final ListAdapter listAdapter = new ListAdapter(view.getContext(), R.layout.item_list_row, main.getAssessments());

        final ListView mainList = (ListView) view.findViewById(R.id.assessmentListView);
        mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Assessment p = (Assessment) mainList.getItemAtPosition(position);
                Toast.makeText(view.getContext(), String.valueOf(p.getGrade()), Toast.LENGTH_SHORT).show();

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                NewAssessment fragment = new NewAssessment();
                fragment.setFromListView(p);
                fragmentTransaction.replace(R.id.main_screen, fragment);
                fragmentTransaction.commit();

                listAdapter.remove(p);
                listAdapter.notifyDataSetChanged();
            }
        });

        mainList.setAdapter(listAdapter);

        return view;
    }
}
