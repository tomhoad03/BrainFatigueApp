package com.example.brainfatigueapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Hide action bar
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        // Back button
        final ImageButton settingsBackBtn = findViewById(R.id.activity_settings_back_button);
        settingsBackBtn.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, DashboardActivity.class);
            startActivity(intent);
        });
    }
}