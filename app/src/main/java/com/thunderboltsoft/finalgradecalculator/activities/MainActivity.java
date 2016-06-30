package com.thunderboltsoft.finalgradecalculator.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.thunderboltsoft.finalgradecalculator.R;
import com.thunderboltsoft.finalgradecalculator.adapters.ViewPagerAdapter;
import com.thunderboltsoft.finalgradecalculator.fragments.MainFragment;
import com.thunderboltsoft.finalgradecalculator.interfaces.ActivityCallbackInterface;
import com.thunderboltsoft.finalgradecalculator.libs.SlidingTabLayout;
import com.thunderboltsoft.finalgradecalculator.models.Assessment;
import com.thunderboltsoft.finalgradecalculator.models.ListAssessments;

import java.util.List;

/**
 * Main activity class for the app.
 *
 * @author Thushan Perera
 */
public class MainActivity extends AppCompatActivity implements ActivityCallbackInterface {

    /**
     * Holds the list of assessments (each containing the grade and weight for that assessment), entered by the user.
     */
    private ListAssessments mAssessments;

    /**
     * View that contains multiple pages(tabs).
     */
    private ViewPager mPager;

    /**
     * Our adapter we use to convert our data and fill into the ViewPager.
     */
    private ViewPagerAdapter mAdapter;

    /**
     * The view for our tabs.
     */
    private SlidingTabLayout mTabs;

    /**
     * The title for our tabs.
     */
    private CharSequence mTitles[] = {"Calculator", "Assessments"};

    /**
     * The number of tabs.
     */
    private int mNumTabs = 2;

    /**
     * Gets and returns the list of assessments entered by the user.
     *
     * @return list of assessments
     */
    public List<Assessment> getAssessments() {
        return mAssessments.getAssessments();
    }

    /**
     * Get and return the final grade needed to achieve the desired grade.
     * @param desiredGrade the desired grade as percentage
     * @return the grade needed as a percentage
     */
    public double getGradeNeeded(double desiredGrade) {
        return mAssessments.calcGradeNeeded(desiredGrade);
    }

    /**
     * Get and return the current grades for all the assessments entered by user.
     * @return the current weighted grade
     */
    public double getCurrentGrade() {
        return mAssessments.getCurrentGrade();
    }

    /**
     * Sends an assessment to the main activity.
     * <p/>
     * TODO: Refactor and send this as a callback
     *
     * @param assessment the assessment
     */
    public void sendAssessment(Assessment assessment) {
        mAssessments.addAssessment(assessment);
        MainFragment mainFragment = (MainFragment) mAdapter.getRegisteredFragment(0);
        mainFragment.updateCurrentGrade(mAssessments.getCurrentGrade());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolBar = (Toolbar) findViewById(R.id.toolbar);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        setSupportActionBar(myToolBar);

        ActionBar sActionBar = getSupportActionBar();
        if (sActionBar != null) {
            sActionBar.setTitle("Grade Calculator");
        }

        mAssessments = new ListAssessments();

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mTitles, mNumTabs);

        // Assigning ViewPager View and setting the adapter
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        // Assiging the Sliding Tab Layout View
        mTabs = (SlidingTabLayout) findViewById(R.id.tabs);
        mTabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        mTabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.colorAccent);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        mTabs.setViewPager(mPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}