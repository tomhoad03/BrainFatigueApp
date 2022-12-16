package com.example.brainfatigueapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;
import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.RangeSlider;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Set the content of the drop-down menu (spinner)
        Spinner frequencySpinner = findViewById(R.id.activity_settings_frequency_slider);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.notification_intervals, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // default layouts
        frequencySpinner.setAdapter(adapter);

        // Get the slider objects
        RangeSlider availableSlider = findViewById(R.id.activity_settings_available_slider);
        RangeSlider unavailableSlider = findViewById(R.id.activity_settings_unavailable_slider);
        RangeSlider summarySlider = findViewById(R.id.activity_settings_summary_slider);

        // Database access
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Settings> futureSetting = executorService.submit(() -> {
            FatigueDatabase fatigueDatabase = FatigueDatabase.getDatabase(getApplicationContext());
            SettingsDao settingsDao = fatigueDatabase.settingsDao();

            List<Settings> settings = settingsDao.getAll();
            Settings setting;
            try {
                setting = settings.get(settings.size() - 1);
            } catch (Exception e) {
                setting = new Settings(System.currentTimeMillis(), 10800000L, 32400000L, 79200000L, 39600000L, 54000000L, 54000000L, false);
            }
            Log.d("survey_results", setting.toString());
            return setting;
        });
        executorService.shutdown();

        long timeout = System.currentTimeMillis() + 10000;
        Settings resultSetting;

        while (System.currentTimeMillis() < timeout) {
            try {
                resultSetting = futureSetting.get();
            } catch (ExecutionException | InterruptedException e) {
                continue;
            }

            // Multiple thumbs on the 'available' and 'unavailable' sliders
            availableSlider.setValues((float) (resultSetting.getDayStart() / 3600000L), (float) (resultSetting.getDayEnd() / 3600000L));
            availableSlider.setMinSeparationValue(1.0f);
            unavailableSlider.setValues((float) (resultSetting.getWorkStart() / 3600000L), (float) (resultSetting.getWorkEnd() / 3600000L));
            unavailableSlider.setMinSeparationValue(1.0f);

            // Format the labels that appear on the slider thumbs to display times and not just numbers
            LabelFormatter timeFormatter = value -> {
                int hour = (int) value;
                if (hour == 24) {
                    return "00:00am";
                } else if (hour >= 12) {
                    return hour + ":00pm";
                } else {
                    return hour + ":00am";
                }
            };
            availableSlider.setLabelFormatter(timeFormatter);
            unavailableSlider.setLabelFormatter(timeFormatter);
            summarySlider.setLabelFormatter(timeFormatter);

            // Hide action bar
            if (getSupportActionBar() != null)
                getSupportActionBar().hide();

            // The dark mode button toggles the switch
            final Button darkModeBtn = findViewById(R.id.activity_settings_button_dark_mode);
            darkModeBtn.setOnClickListener(v -> {
                // Toggle the switch to the opposite state
                final Switch darkModeSwitch = findViewById(R.id.activity_settings_switch_dark_mode);
                Boolean currentState = darkModeSwitch.isChecked();
                darkModeSwitch.setChecked(!currentState);

                // Call the function to do the dark mode things

            });
            break;
        }
    }
}