package com.thunderboltsoft.finalgradecalculator.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.thunderboltsoft.finalgradecalculator.R;
import com.thunderboltsoft.finalgradecalculator.models.Assessment;

import java.util.List;

/**
 * This code is based on Rakhita's answer on StackOverflow.
 *
 * @see <a href="http://stackoverflow.com/a/8166802/2440953">Custom Adapter for List View</a>
 *
 * @author Thushan Perera
 */
public class ListAdapter extends ArrayAdapter<Assessment> {

    /**
     * Public constructor.
     *
     * @param context            the context
     * @param textViewResourceId the resource id
     */
    public ListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    /**
     * Public constructor.
     * @param context the context
     * @param resource the resource id
     * @param items the list of assessments to populate to view with
     */
    public ListAdapter(Context context, int resource, List<Assessment> items) {
        super(context, resource, items);
    }

    /**
     * Gets the view at a given position.
     * @param position position of view
     * @param convertView the reference view
     * @param parent the parent group of the view
     * @return the view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.item_list_row, parent, false);
        }

        Assessment p = getItem(position);

        if (p != null) {
            TextView gradeTextView = (TextView) v.findViewById(R.id.itemRowGrade);
            TextView weightTextView = (TextView) v.findViewById(R.id.itemRowWeight);

            if (gradeTextView != null) {
                gradeTextView.setText(String.valueOf(p.getGrade()));
            }

            if (weightTextView != null) {
                weightTextView.setText(String.valueOf(p.getWeight()));
            }
        }

        return v;
    }
}
