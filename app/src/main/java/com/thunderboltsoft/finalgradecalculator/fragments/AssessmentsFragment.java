package com.thunderboltsoft.finalgradecalculator.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TableRow;

import com.melnykov.fab.FloatingActionButton;
import com.thunderboltsoft.finalgradecalculator.R;
import com.thunderboltsoft.finalgradecalculator.adapters.ListAdapter;
import com.thunderboltsoft.finalgradecalculator.interfaces.ActivityCallback;
import com.thunderboltsoft.finalgradecalculator.models.Assessment;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

/**
 * Fragment that contains the list of assessments that the user have entered.
 * User is able to edit an assessment by clicking on that item in the list.
 *
 * @author Thushan Perera
 */
public class AssessmentsFragment extends Fragment {

    final private String SHOWCASE_ID = "Test2";

    private ActivityCallback mCallbackActivity;

    private ListAdapter listAdapter;

    private ListView mainList;

    private ViewGroup mContainer;

    private FloatingActionButton fab;

    private TableRow mHeading;

    /**
     * Required public constructor.
     */
    public AssessmentsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_assessments, container, false);

        mHeading = (TableRow) view.findViewById(R.id.tableRow);

        mContainer = container;

        // Set floating action button which will allow user to add a new assessment
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show the new assessment fragment in a dialog box
                NewAssessmentDialogFragment newAssessmentDialogFragment = NewAssessmentDialogFragment.newInstance(false);
                newAssessmentDialogFragment.show(getChildFragmentManager().beginTransaction(), "DialogFragment");
            }
        });

        listAdapter = new ListAdapter(view.getContext(), R.layout.item_list_row, mCallbackActivity.getAssessments());

        // Add onClick listener to the list, so that user can edit individual assessments
        mainList = (ListView) view.findViewById(R.id.assessmentListView);
        mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Assessment p = (Assessment) mainList.getItemAtPosition(position);

                NewAssessmentDialogFragment newAssessmentDialogFragment = NewAssessmentDialogFragment.newInstance(true);
                newAssessmentDialogFragment.setFromListView(p);

                newAssessmentDialogFragment.show(getChildFragmentManager().beginTransaction(), "DialogFragment");

                listAdapter.remove(p); // Remove it, easier to delete if user does not want it
                listAdapter.notifyDataSetChanged();
            }
        });

        mainList.setAdapter(listAdapter);

        shouldDisable(true);


        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallbackActivity = (ActivityCallback) activity;
    }

    public void resetListView() {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_assessments, mContainer, false);

        listAdapter = new ListAdapter(view.getContext(), R.layout.item_list_row, mCallbackActivity.getAssessments());
        mainList.setAdapter(listAdapter);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()) {
            onResume();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!getUserVisibleHint()) {
            return;
        }

        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(500); // half second between each showcase view

        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(getActivity(), SHOWCASE_ID);
        sequence.setConfig(config);
        sequence.addSequenceItem(fab, "Tap here to enter the grade and weight for an assessment", "GOT IT");
        sequence.addSequenceItem(mHeading, "You can edit or delete any of these by tapping on them", "GOT IT");
        sequence.start();
    }

    public void shouldDisable(boolean shouldDisable) {
        if (shouldDisable) {
            fab.setVisibility(View.INVISIBLE);
        } else {
            fab.setVisibility(View.VISIBLE);
        }
    }

    public void updateListAdapter() {
        listAdapter.notifyDataSetChanged();
    }
}
