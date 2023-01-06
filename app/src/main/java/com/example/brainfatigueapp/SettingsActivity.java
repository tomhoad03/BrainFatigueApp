package com.example.brainfatigueapp;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.RangeSlider;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
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
        Future<Setting> futureSetting = executorService.submit(() -> {
            FatigueDatabase fatigueDatabase = FatigueDatabase.getDatabase(getApplicationContext());
            SettingsDao settingsDao = fatigueDatabase.settingsDao();

            List<Setting> settings = settingsDao.getAll();
            Setting setting;
            try {
                setting = settings.get(settings.size() - 1);
            } catch (Exception e) {
                setting = new Setting();
            }
            return setting;
        });
        executorService.shutdown();

        long timeout = System.currentTimeMillis() + 10000;
        Setting resultSetting;

        while (System.currentTimeMillis() < timeout) {
            try {
                resultSetting = futureSetting.get();
            } catch (ExecutionException | InterruptedException e) {
                continue;
            }
            final long milliHour = 3600000L;
            Setting finalResultSetting = resultSetting;
            ArrayList<Long> intervals = new ArrayList<>(Arrays.asList(milliHour, milliHour + (milliHour / 2L), 2L * milliHour, (2L * milliHour) + (milliHour / 2L), 3L * milliHour));

            // Interval selector
            frequencySpinner.setSelection(intervals.indexOf(resultSetting.getInterval()));
            frequencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ExecutorService frequencyExecutorService = Executors.newSingleThreadExecutor();
                    frequencyExecutorService.submit(() -> {
                        FatigueDatabase fatigueDatabase = FatigueDatabase.getDatabase(getApplicationContext());
                        SettingsDao settingsDao = fatigueDatabase.settingsDao();

                        finalResultSetting.setSettingsId(System.currentTimeMillis());
                        for (Long interval : intervals) {
                            finalResultSetting.setInterval(interval);
                        }

                        settingsDao.deleteAll();
                        settingsDao.insert(finalResultSetting);
                    });
                    frequencyExecutorService.shutdown();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {}
            });

            // Awake time slider
            availableSlider.setValues((float) (resultSetting.getDayStart() / milliHour), (float) (resultSetting.getDayEnd() / milliHour));
            availableSlider.setMinSeparationValue(1.0f);

            availableSlider.addOnChangeListener((slider, value, fromUser) -> {
                ExecutorService availableExecutorService = Executors.newSingleThreadExecutor();
                availableExecutorService.submit(() -> {
                    FatigueDatabase fatigueDatabase = FatigueDatabase.getDatabase(getApplicationContext());
                    SettingsDao settingsDao = fatigueDatabase.settingsDao();

                    finalResultSetting.setSettingsId(System.currentTimeMillis());
                    finalResultSetting.setDayStart((long) (slider.getValues().get(0) * milliHour));
                    finalResultSetting.setDayEnd((long) (slider.getValues().get(1) * milliHour));

                    settingsDao.deleteAll();
                    settingsDao.insert(finalResultSetting);
                });
                availableExecutorService.shutdown();
            });

            // Work time slider
            unavailableSlider.setValues((float) (resultSetting.getWorkStart() / milliHour), (float) (resultSetting.getWorkEnd() / milliHour));
            unavailableSlider.setMinSeparationValue(1.0f);

            unavailableSlider.addOnChangeListener((slider, value, fromUser) -> {
                ExecutorService unavailableExecutorService = Executors.newSingleThreadExecutor();
                unavailableExecutorService.submit(() -> {
                    FatigueDatabase fatigueDatabase = FatigueDatabase.getDatabase(getApplicationContext());
                    SettingsDao settingsDao = fatigueDatabase.settingsDao();

                    finalResultSetting.setSettingsId(System.currentTimeMillis());
                    finalResultSetting.setWorkStart((long) (slider.getValues().get(0) * milliHour));
                    finalResultSetting.setWorkEnd((long) (slider.getValues().get(1) * milliHour));

                    settingsDao.deleteAll();
                    settingsDao.insert(finalResultSetting);
                });
                unavailableExecutorService.shutdown();
            });

            // Summary time slider
            summarySlider.setValues((float) (resultSetting.getSummary() / milliHour));
            summarySlider.setMinSeparationValue(1.0f);

            summarySlider.addOnChangeListener((slider, value, fromUser) -> {
                ExecutorService summaryExecutorService = Executors.newSingleThreadExecutor();
                summaryExecutorService.submit(() -> {
                    FatigueDatabase fatigueDatabase = FatigueDatabase.getDatabase(getApplicationContext());
                    SettingsDao settingsDao = fatigueDatabase.settingsDao();

                    finalResultSetting.setSettingsId(System.currentTimeMillis());
                    finalResultSetting.setSummary((long) (slider.getValues().get(0) * milliHour));

                    settingsDao.deleteAll();
                    settingsDao.insert(finalResultSetting);
                });
                summaryExecutorService.shutdown();

                long scheduledNotification = System.currentTimeMillis() - (((long) LocalTime.now().getHour() * 3600 * 1000) + ((long) LocalTime.now().getMinute() * 60 * 1000) + (LocalTime.now().getSecond() * 1000L)) + ((long) (slider.getValues().get(0) * milliHour));

                // Setup the daily summary notification
                Intent notifyIntent = new Intent(getApplicationContext(), SummaryReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                getSystemService(NotificationManager.class).cancel(1);
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + scheduledNotification, pendingIntent);
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

            // The dark mode button toggles the switch
            final Button darkModeBtn = findViewById(R.id.activity_settings_button_dark_mode);
            final Switch darkModeSwitch = findViewById(R.id.activity_settings_switch_dark_mode);
            darkModeSwitch.setChecked(finalResultSetting.isDarkMode());
            darkModeSwitch.setClickable(false);

            darkModeBtn.setOnClickListener(v -> {
                // Toggle the switch to the opposite state
                darkModeSwitch.setChecked(!darkModeSwitch.isChecked());

                // Toggle the switch to the opposite state
                darkModeSwitch.setChecked(!darkModeSwitch.isChecked());

                if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }

                // Call the function to do the dark mode things
                ExecutorService darkExecutorService = Executors.newSingleThreadExecutor();
                darkExecutorService.submit(() -> {
                    FatigueDatabase fatigueDatabase = FatigueDatabase.getDatabase(getApplicationContext());
                    SettingsDao settingsDao = fatigueDatabase.settingsDao();

                    finalResultSetting.setSettingsId(System.currentTimeMillis());
                    finalResultSetting.setDarkMode(!finalResultSetting.isDarkMode());

                    settingsDao.deleteAll();
                    settingsDao.insert(finalResultSetting);
                });
                darkExecutorService.shutdown();
            });

            // Make the FAQ button spawn a popup
            Button faqButton = findViewById(R.id.activity_settings_button_faq);
            faqButton.setOnClickListener(v -> {
                FragmentManager manager = getSupportFragmentManager();
                DashboardPopupFrag popup = new DashboardPopupFrag("faq");
                popup.show(manager, "popup");
            });

            // Make the 'About info' button spawn a popup
            Button aboutButton = findViewById(R.id.activity_settings_button_about);
            aboutButton.setOnClickListener(v -> {
                FragmentManager manager = getSupportFragmentManager();
                DashboardPopupFrag popup = new DashboardPopupFrag("about");
                popup.show(manager, "popup");
            });

            // Make the 'Log out' button do log out stuff
            //dw I did :)
            Button logOutButton = findViewById(R.id.activity_settings_button_logout);
            logOutButton.setOnClickListener(v -> {
                        GoogleSignInOptions gsi_options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
                        GoogleSignInClient gsi_client = GoogleSignIn.getClient(this, gsi_options);
                        gsi_client.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Intent intent = new Intent(SettingsActivity.this, LoginPageActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                    });

            // Hide action bar
            if (getSupportActionBar() != null)
                getSupportActionBar().hide();

            // TODO - left/right handed button, notification schedule visualiser, work start/end blockers for day start/end, disable work blocker button
            break;
        }
    }
}