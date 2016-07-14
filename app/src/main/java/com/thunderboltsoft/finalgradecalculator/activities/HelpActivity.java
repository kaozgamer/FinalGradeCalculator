package com.thunderboltsoft.finalgradecalculator.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.thunderboltsoft.finalgradecalculator.R;

/**
 * Activity for the HelpFragment class.
 *
 * @author Thushan Perera
 */
public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_help);

        // User needs toolbar to go back to the main activity screen
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_help, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) { // Did user click on the back button?
            finish();

            return true;

        } else if (item.getItemId() == R.id.action_help_report_problem) { // DId user click on the report bug button on the toolbar?
            Intent mailto = new Intent(Intent.ACTION_SEND);
            mailto.setType("message/rfc822");
            mailto.putExtra(Intent.EXTRA_EMAIL, new String[]{"kaozgamerdev+grade_calculator@gmail.com"});
            mailto.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.help_activity_email_bug_report_subject));
            mailto.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.help_activity_email_bug_report_text));
            startActivity(Intent.createChooser(mailto, getResources().getString(R.string.email_intent_chooser)));

            return true;

        } else if (item.getItemId() == R.id.action_help_give_feedback) { // Did user click on the give feedback button on the toolbar?
            Intent mailto = new Intent(Intent.ACTION_SEND);
            mailto.setType("message/rfc822");
            mailto.putExtra(Intent.EXTRA_EMAIL, new String[]{"kaozgamerdev+grade_calculator@gmail.com"});
            mailto.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.help_activity_email_feedback_subject));
            mailto.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.help_activity_email_feedback_text));
            startActivity(Intent.createChooser(mailto, getResources().getString(R.string.email_intent_chooser)));

            return true;

        } else if (item.getItemId() == R.id.action_help_about) { // Did user click on the about button on the toolbar?
            Intent intent = new Intent();
            intent.setClassName(this, "com.thunderboltsoft.finalgradecalculator.activities.AboutActivity");
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
