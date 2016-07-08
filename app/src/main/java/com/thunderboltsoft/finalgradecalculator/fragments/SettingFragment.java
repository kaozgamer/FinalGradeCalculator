package com.thunderboltsoft.finalgradecalculator.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.thunderboltsoft.finalgradecalculator.R;

/**
 * Created by Thushan on 08-Jul-16.
 */
public class SettingFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.fragment_setting);
    }
}
