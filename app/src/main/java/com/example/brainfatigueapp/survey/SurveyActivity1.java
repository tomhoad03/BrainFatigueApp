package com.example.brainfatigueapp.survey;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.brainfatigueapp.R;

public class SurveyActivity1 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey1);

        // Hide action bar
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
    }
}