package com.thunderboltsoft.finalgradecalculator;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Thushan on 20-May-16.
 */
public class PageAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;

    public PageAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        String title = "";

        switch (position) {
            case 0:
                title = "Calculator";
                break;
            case 1:
                title = "Assessments";
                break;
            case 2:
                title = "Units";
                break;
        }

        return title;
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }
}
