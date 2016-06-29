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

public class MainActivity extends AppCompatActivity implements ActivityCallbackInterface {

    private ListAssessments assessments;

    public List<Assessment> getAssessments() {
        return assessments.getAssessments();
    }

    public double getGradeNeeded(double desiredGrade) {
        return assessments.calcGradeNeeded(desiredGrade);
    }

    public double getCurrentGrade() {
        return assessments.getCurrentGrade();
    }

    public void sendAssessment(Assessment as) {
        assessments.addAssessment(as);
        MainFragment mainFragment = (MainFragment) adapter.getRegisteredFragment(0);
        mainFragment.updateCurrentGrade(assessments.getCurrentGrade());
    }

    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[] = {"Calculator", "Assessments"};
    int Numboftabs = 2;

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

        assessments = new ListAssessments();

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), Titles, Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.colorAccent);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);
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
