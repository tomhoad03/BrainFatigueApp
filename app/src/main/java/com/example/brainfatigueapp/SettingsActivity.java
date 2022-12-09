package com.example.brainfatigueapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;
import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.RangeSlider;
import org.jetbrains.annotations.NotNull;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Set the content of the drop-down menu (spinner)
        Spinner frequencySpinner = findViewById(R.id.activity_settings_frequency_slider);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.notification_intervals,
                                                                            android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // default layouts
        frequencySpinner.setAdapter(adapter);

        // Get the slider objects
        RangeSlider availableSlider = findViewById(R.id.activity_settings_available_slider);
        RangeSlider unavailableSlider = findViewById(R.id.activity_settings_unavailable_slider);
        RangeSlider summarySlider = findViewById(R.id.activity_settings_summary_slider);

        // Multiple thumbs on the 'available' and 'unavailable' sliders
        availableSlider.setValues(9f, 21f);
        unavailableSlider.setValues(9f, 21f);

        // Format the labels that appear on the slider thumbs to display times and not just numbers
        LabelFormatter timeFormatter = new LabelFormatter() {
            @NonNull
            @NotNull
            @Override
            public String getFormattedValue(float value) {
                int hour = (int) value;
                if (hour == 24) {
                    return "00:00am";
                } else if (hour >= 12) {
                    return String.valueOf(hour) + ":00pm";
                } else {
                    return String.valueOf(hour) + ":00am";
                }
            }
        };
        availableSlider.setLabelFormatter(timeFormatter);
        unavailableSlider.setLabelFormatter(timeFormatter);
        summarySlider.setLabelFormatter(timeFormatter);

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