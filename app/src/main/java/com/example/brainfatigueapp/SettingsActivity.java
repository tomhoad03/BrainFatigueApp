package com.example.brainfatigueapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;
import com.google.android.material.slider.RangeSlider;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Multiple thumbs on the 'available' range slider
        RangeSlider availableSlider = findViewById(R.id.activity_settings_available_slider);
        availableSlider.setValues(9f, 21f);

        // Multiple thumbs on the 'unavailable' range slider
        RangeSlider unavailableSlider = findViewById(R.id.activity_settings_unavailable_slider);
        unavailableSlider.setValues(9f, 21f);

        // Hide action bar
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        // Back button
        final ImageButton settingsBackBtn = findViewById(R.id.activity_settings_back_button);
        settingsBackBtn.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, DashboardActivity.class);
            startActivity(intent);
        });

        // The dark mode button toggles the switch
        final Button darkModeBtn = findViewById(R.id.activity_settings_button_dark_mode);
        darkModeBtn.setOnClickListener(v -> {
            // Toggle the switch to the opposite state
            final Switch darkModeSwitch = findViewById(R.id.activity_settings_switch_dark_mode);
            Boolean currentState = darkModeSwitch.isChecked();
            darkModeSwitch.setChecked(!currentState);

            // Call the function to do the dark mode things
            
        });
    }
}