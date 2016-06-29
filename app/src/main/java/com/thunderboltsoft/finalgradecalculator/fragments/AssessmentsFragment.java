package com.thunderboltsoft.finalgradecalculator.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.melnykov.fab.FloatingActionButton;
import com.thunderboltsoft.finalgradecalculator.R;
import com.thunderboltsoft.finalgradecalculator.activities.MainActivity;
import com.thunderboltsoft.finalgradecalculator.adapters.ListAdapter;
import com.thunderboltsoft.finalgradecalculator.models.Assessment;

public class AssessmentsFragment extends Fragment {

    public AssessmentsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final MainActivity main = (MainActivity) getActivity();

        final View view = inflater.inflate(R.layout.fragment_assessments, container, false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewAssessmentDialogFragment newAssessmentDialogFragment = NewAssessmentDialogFragment.newInstance(0);
                newAssessmentDialogFragment.show(getChildFragmentManager().beginTransaction(), "DialogFragment");
            }
        });

        final ListAdapter listAdapter = new ListAdapter(view.getContext(), R.layout.item_list_row, main.getAssessments());

        final ListView mainList = (ListView) view.findViewById(R.id.assessmentListView);
        mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Assessment p = (Assessment) mainList.getItemAtPosition(position);

                NewAssessmentDialogFragment newAssessmentDialogFragment = NewAssessmentDialogFragment.newInstance(0);
                newAssessmentDialogFragment.setFromListView(p);

                newAssessmentDialogFragment.show(getChildFragmentManager().beginTransaction(), "DialogFragment");

                listAdapter.remove(p);
                listAdapter.notifyDataSetChanged();
            }
        });

        mainList.setAdapter(listAdapter);

        return view;
    }
}
