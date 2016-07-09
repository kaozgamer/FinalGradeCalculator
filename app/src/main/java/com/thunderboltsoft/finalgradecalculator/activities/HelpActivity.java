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
 * Created by Thushan on 09-Jul-16.
 */
public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_help);

        Toolbar toolbar = (Toolbar) findViewById(R.id.settings_toolbar);
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

        if (item.getItemId() == android.R.id.home) {
            finish();

            return true;
        } else if (item.getItemId() == R.id.action_help_report_problem) {
            Intent mailto = new Intent(Intent.ACTION_SEND);
            mailto.setType("message/rfc822");
            mailto.putExtra(Intent.EXTRA_EMAIL, new String[]{"kaozgamerdev+grade_calculator@gmail.com"});
            mailto.putExtra(Intent.EXTRA_SUBJECT, "Bug Report: Grade Calculator");
            mailto.putExtra(Intent.EXTRA_TEXT, "What can I help you with?\n");
            startActivity(Intent.createChooser(mailto, "Select email application"));

            return true;
        } else if (item.getItemId() == R.id.action_help_give_feedback) {
            Intent mailto = new Intent(Intent.ACTION_SEND);
            mailto.setType("message/rfc822");
            mailto.putExtra(Intent.EXTRA_EMAIL, new String[]{"kaozgamerdev+grade_calculator@gmail.com"});
            mailto.putExtra(Intent.EXTRA_SUBJECT, "Feedback: Grade Calculator");
            mailto.putExtra(Intent.EXTRA_TEXT, "What can I help you with?\n");
            startActivity(Intent.createChooser(mailto, "Select email application"));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
