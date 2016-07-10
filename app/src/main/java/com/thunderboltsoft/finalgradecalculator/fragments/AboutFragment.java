package com.thunderboltsoft.finalgradecalculator.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.thunderboltsoft.finalgradecalculator.R;

/**
 * Created by Thushan on 10-Jul-16.
 */
public class AboutFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_help);
    }
}
