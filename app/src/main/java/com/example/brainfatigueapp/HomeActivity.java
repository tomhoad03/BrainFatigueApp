package com.example.brainfatigueapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Hide action bar
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        // Start survey button
        final Button startSurveyBtn = findViewById(R.id.activity_home_survey_button);
        startSurveyBtn.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, SurveyStartActivity.class);
            startActivity(intent);
        });

        // Open the dashboard button
        final Button dashboardBtn = findViewById(R.id.activity_home_dashboard_button);
        dashboardBtn.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, DashboardActivity.class);
            startActivity(intent);
        });
    }
}