package com.thunderboltsoft.finalgradecalculator;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListAssessments assessments;
    private MainFragment mainFragment;

    public int getNumAssessments() {
        return assessments.getAssessments().size();
    }

    public String[] getAssessmentsList() {
        return assessments.getAssessmentsDetailList();
    }

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

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
//
//        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,myToolBar,R.string.app_name,R.string.app_name);
//        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        assessments = new ListAssessments();

        mainFragment = new MainFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.main_screen, new ViewPagerContainerFragment()).commit();

        //getFragmentManager().beginTransaction().replace(R.id.main_screen, mainFragment, "Tag").addToBackStack(null).commit();
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
//        if (id == R.id.action_settings) {
//            return true;
//        }

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
