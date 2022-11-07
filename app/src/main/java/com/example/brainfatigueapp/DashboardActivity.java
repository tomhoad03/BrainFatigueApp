package com.example.brainfatigueapp;

import android.content.Intent;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Hide action bar
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        // Back button
        final ImageButton startSurveyBtn = findViewById(R.id.activity_dashboard_back_button);
        startSurveyBtn.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, HomeActivity.class);
            startActivity(intent);
        });
    }
}