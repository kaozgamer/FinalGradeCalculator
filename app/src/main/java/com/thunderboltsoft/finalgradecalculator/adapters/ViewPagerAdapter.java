package com.thunderboltsoft.finalgradecalculator.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.thunderboltsoft.finalgradecalculator.fragments.AssessmentsFragment;
import com.thunderboltsoft.finalgradecalculator.fragments.MainFragment;

/**
 * Provides the views for every page in every tab.
 * <p/>
 * Based on code by Akash Bangad from "Android For Devs".
 *
 * @author Thushan Perera
 * @see <a href="http://www.android4devs.com/2015/01/how-to-make-material-design-sliding-tabs.html">How To Make Material Design Sliding Tabs</a>
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    /**
     * Stores titles of each tab.
     */
    private CharSequence mTitles[];

    /**
     * Number of tabs.
     */
    private int mNumTabs;

    /**
     * Holds the fragments that have been instantiated and registered to this view pager.
     */
    private SparseArray<Fragment> mRegisteredFragments = new SparseArray<>();

    /**
     * Public constructor and assign passed values.
     *
     * @param fm      the fragment manager
     * @param titles  the titles for each page
     * @param numTabs number of pages
     */
    public ViewPagerAdapter(FragmentManager fm, CharSequence titles[], int numTabs) {
        super(fm);

        mTitles = titles;
        mNumTabs = numTabs;

    }

    /**
     * Returns the fragment for every position in the ViewPager.
     *
     * @param position position of fragment
     * @return the fragment at that position
     */
    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            MainFragment mainFragment = new MainFragment();
            mRegisteredFragments.put(position, mainFragment); // Save the fragment
            return mainFragment;
        } else {
            AssessmentsFragment assessmentsFragment = new AssessmentsFragment();
            mRegisteredFragments.put(position, assessmentsFragment); // Save the fragment
            return assessmentsFragment;
        }
    }

    /**
     * Return the title of the tab in the TabStrip
     *
     * @param position position of tab
     * @return title of tab
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    /**
     * Returns the number of tabs in ViewPager.
     *
     * @return number of tabs
     */
    @Override
    public int getCount() {
        return mNumTabs;
    }

    /**
     * Instantiates a fragment and stores its reference for later use.
     *
     * @param container the container containing the fragment
     * @param position  position of fragment
     * @return the fragment
     */
    @Override
    public Object instantiateItem(View container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        mRegisteredFragments.put(position, fragment);
        return fragment;
    }

    /**
     * Destroys the fragment at a position.
     *
     * @param container container containing the view
     * @param position  position of fragment
     * @param object    the fragment
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        mRegisteredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    /**
     * Retrieves the reference to a fragment at a given position.
     *
     * @param position position of fragment
     * @return the fragment
     */
    public Fragment getRegisteredFragment(int position) {
        return mRegisteredFragments.valueAt(position);
    }
}