package com.thunderboltsoft.finalgradecalculator.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
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
        Preference contactDeveloper = findPreference("pref_contact_dev");

        contactDeveloper.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent mailto = new Intent(Intent.ACTION_SEND);
                mailto.setType("message/rfc822");
                mailto.putExtra(Intent.EXTRA_EMAIL, new String[]{"kaozgamerdev+grade_calculator@gmail.com"});
                mailto.putExtra(Intent.EXTRA_SUBJECT, "Feedback about Grade Calculator");
                mailto.putExtra(Intent.EXTRA_TEXT, "What can I help you with?");
                startActivity(Intent.createChooser(mailto, "Select email application"));

                return true;
            }
        });
    }
}
