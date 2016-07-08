package com.thunderboltsoft.finalgradecalculator.activities;

import android.content.Intent;
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
import com.thunderboltsoft.finalgradecalculator.fragments.AssessmentsFragment;
import com.thunderboltsoft.finalgradecalculator.fragments.MainFragment;
import com.thunderboltsoft.finalgradecalculator.interfaces.ActivityCallback;
import com.thunderboltsoft.finalgradecalculator.libs.SlidingTabLayout;
import com.thunderboltsoft.finalgradecalculator.models.Assessment;
import com.thunderboltsoft.finalgradecalculator.models.ListAssessments;

import java.util.List;

/**
 * Main activity class for the app.
 *
 * @author Thushan Perera
 */
public class MainActivity extends AppCompatActivity implements ActivityCallback {

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
    private CharSequence mTitles[] = {"Calculator", "Assessments", "Settings"};

    /**
     * The number of tabs.
     */
    private int mNumTabs = 2;

    /**
     * Gets and returns the list of assessments entered by the user.
     *
     * @return list of assessments
     */
    @Override
    public List<Assessment> getAssessments() {
        return mAssessments.getAssessments();
    }

    /**
     * Get and return the final grade needed to achieve the desired grade.
     * @param desiredGrade the desired grade as percentage
     * @return the grade needed as a percentage
     */
    @Override
    public double getGradeNeeded(double desiredGrade) {
        return mAssessments.calcGradeNeeded(desiredGrade);
    }

    @Override
    public double getGradeNeeded(double currentGrade, double desiredGrade, double finalGradeWeight) {
        return mAssessments.calcGradeNeeded(currentGrade, desiredGrade, finalGradeWeight);
    }

    /**
     * Get and return the current grades for all the assessments entered by user.
     * @return the current weighted grade
     */
    @Override
    public double getCurrentGrade() {
        return mAssessments.getCurrentGrade();
    }

    @Override
    public void shouldDisableFab(boolean shouldDisable) {
        AssessmentsFragment assessmentsFragment = (AssessmentsFragment) mAdapter.getRegisteredFragment(1);
        assessmentsFragment.shouldDisable(shouldDisable);
    }

    public void removeFromDataSet() {
        mAssessments.recalculateCurrentGrade();

        MainFragment mainFragment = (MainFragment) mAdapter.getRegisteredFragment(0);
        mainFragment.updateCurrentGrade(mAssessments.getCurrentGrade());
    }

    /**
     * Sends an assessment to the main activity.
     * <p/>
     * TODO: Refactor and send this as a callback
     *
     * @param assessment the assessment
     */
    public void sendAssessment(final Assessment assessment) {
//        List<Assessment> newAssessments = new ArrayList<>();
//        newAssessments.addAll(mAssessments.getAssessments());
//        newAssessments.add(assessment);
//
//        mAssessments.getAssessments().clear();
//        mAssessments.getAssessments().addAll(newAssessments);
//
//        mAdapter.notifyDataSetChanged();

//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                mAssessments.addAssessment(assessment);
//                mAdapter.notifyDataSetChanged();
//            }
//        });

        mAssessments.addAssessment(assessment);

        AssessmentsFragment assessmentsFragment = (AssessmentsFragment) mAdapter.getRegisteredFragment(1);
        assessmentsFragment.updateListAdapter();

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

        // Assigning the Sliding Tab Layout View
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

        // Default is the switch compat is on
//        disableViewPager();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent();
            intent.setClassName(this, "com.thunderboltsoft.finalgradecalculator.activities.SettingActivity");
            startActivity(intent);

            return true;
        }

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

    @Override
    public void disableViewPager() {
        mPager.beginFakeDrag();
        mTabs.setEnableTabsClick(false);
    }

    @Override
    public void enableViewPager() {
        mPager.beginFakeDrag();
        mPager.endFakeDrag();
        mTabs.setEnableTabsClick(true);
    }

    @Override
    public void switchToAssessmentsTab() {
        mPager.post(new Runnable() {
            @Override
            public void run() {
                mPager.setCurrentItem(1);
                mAdapter.notifyDataSetChanged();
            }
        });
    }
}
