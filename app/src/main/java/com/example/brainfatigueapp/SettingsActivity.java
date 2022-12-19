package com.example.brainfatigueapp;

import android.location.GnssAntennaInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
        ExecutorService executorService1 = Executors.newSingleThreadExecutor();
        Future<Settings> futureSetting = executorService1.submit(() -> {
            FatigueDatabase fatigueDatabase = FatigueDatabase.getDatabase(getApplicationContext());
            SettingsDao settingsDao = fatigueDatabase.settingsDao();

            List<Settings> settings = settingsDao.getAll();
            Settings setting;
            try {
                setting = settings.get(settings.size() - 1);
            } catch (Exception e) {
                setting = new Settings();
            }
            Log.d("setting", String.valueOf(setting));
            return setting;
        });
        executorService1.shutdown();

        long timeout = System.currentTimeMillis() + 10000;
        Settings resultSetting;

        while (System.currentTimeMillis() < timeout) {
            try {
                resultSetting = futureSetting.get();
            } catch (ExecutionException | InterruptedException e) {
                continue;
            }
            final float milliHour = 3600000L;
            Settings finalResultSetting = resultSetting;

            // Awake time slider
            availableSlider.setValues(resultSetting.getDayStart() / milliHour, resultSetting.getDayEnd() / milliHour);
            availableSlider.setMinSeparationValue(1.0f);

            availableSlider.addOnChangeListener((slider, value, fromUser) -> {
                ExecutorService executorService2 = Executors.newSingleThreadExecutor();
                executorService2.submit(() -> {
                    FatigueDatabase fatigueDatabase = FatigueDatabase.getDatabase(getApplicationContext());
                    SettingsDao settingsDao = fatigueDatabase.settingsDao();

                    finalResultSetting.setSettingsId(System.currentTimeMillis());
                    finalResultSetting.setDayStart((long) (slider.getValues().get(0) * milliHour));
                    finalResultSetting.setDayEnd((long) (slider.getValues().get(1) * milliHour));

                    settingsDao.deleteAll();
                    settingsDao.insert(finalResultSetting);
                });
                executorService2.shutdown();
            });

            // Work time slider
            unavailableSlider.setValues(resultSetting.getWorkStart() / milliHour, resultSetting.getWorkEnd() / milliHour);
            unavailableSlider.setMinSeparationValue(1.0f);

            unavailableSlider.addOnChangeListener((slider, value, fromUser) -> {
                ExecutorService executorService2 = Executors.newSingleThreadExecutor();
                executorService2.submit(() -> {
                    FatigueDatabase fatigueDatabase = FatigueDatabase.getDatabase(getApplicationContext());
                    SettingsDao settingsDao = fatigueDatabase.settingsDao();

                    finalResultSetting.setSettingsId(System.currentTimeMillis());
                    finalResultSetting.setWorkStart((long) (slider.getValues().get(0) * milliHour));
                    finalResultSetting.setWorkEnd((long) (slider.getValues().get(1) * milliHour));

                    settingsDao.deleteAll();
                    settingsDao.insert(finalResultSetting);
                });
                executorService2.shutdown();
            });

            // Summary time slider
            summarySlider.setValues(resultSetting.getSummary() / milliHour);
            summarySlider.setMinSeparationValue(1.0f);

            summarySlider.addOnChangeListener((slider, value, fromUser) -> {
                ExecutorService executorService2 = Executors.newSingleThreadExecutor();
                executorService2.submit(() -> {
                    FatigueDatabase fatigueDatabase = FatigueDatabase.getDatabase(getApplicationContext());
                    SettingsDao settingsDao = fatigueDatabase.settingsDao();

                    finalResultSetting.setSettingsId(System.currentTimeMillis());
                    finalResultSetting.setSummary((long) (slider.getValues().get(0) * milliHour));

                    settingsDao.deleteAll();
                    settingsDao.insert(finalResultSetting);
                });
                executorService2.shutdown();
            });

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
                boolean currentState = darkModeSwitch.isChecked();
                darkModeSwitch.setChecked(!currentState);

                // Call the function to do the dark mode things
            });
            break;
        }
    }
}