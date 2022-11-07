package com.example.brainfatigueapp;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import com.example.brainfatigueapp.R;

public class SurveyStartActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_start);

        // Hide action bar
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        // Back button
        final ImageButton startSurveyBtn = findViewById(R.id.activity_survey_start_back_button);
        startSurveyBtn.setOnClickListener(v -> {
            Intent intent = new Intent(SurveyStartActivity.this, HomeActivity.class);
            startActivity(intent);
        });
    }
}