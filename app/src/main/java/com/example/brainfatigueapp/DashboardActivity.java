package com.example.brainfatigueapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ImageButton;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.constraintlayout.motion.utils.ViewState;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;

import java.util.List;
import java.util.concurrent.*;

public class DashboardActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Hide action bar
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        // Settings button
        final MaterialButton settingsBtn = findViewById(R.id.activity_dashboard_settings_button);
        settingsBtn.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        // Database access
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<List<SurveyResult>> surveyResultsFuture = executorService.submit(() -> {
            SurveyDatabase surveyDatabase = SurveyDatabase.getDatabase(getApplicationContext());
            SurveyResultDao surveyResultDao = surveyDatabase.surveyResultDao();

            return surveyResultDao.getAll();
        });
        executorService.shutdown();

        List<SurveyResult> surveyResults = null;
        try {
            surveyResults = surveyResultsFuture.get(200, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
        Log.d("survey_results", surveyResults.toString());

        // Fragments
        FragmentManager fm = getSupportFragmentManager();
        ViewStateAdapter sa = new ViewStateAdapter(fm, getLifecycle());
        final ViewPager2 pa = findViewById(R.id.activity_dashboard_view_pager_1);
        pa.setAdapter(sa);

        tabLayout = findViewById(R.id.activity_dashboard_tab_bar);
        tabLayout.addTab(tabLayout.newTab().setText("Today"));
        tabLayout.addTab(tabLayout.newTab().setText("Lifetime"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pa.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        pa.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
    }

    // Get the string for the corresponding survey result
    public String getSurveyString(SurveyResult surveyResult, Integer questionId, Integer questionResult, @Nullable Boolean extended) {
        if (questionId == 2 || (questionId == 3 && extended != null) || (questionId == 4 && extended != null) || questionId == 8 || questionId == 9) {
            return surveyResult.getSurveyString(getApplicationContext(), questionId, questionResult, extended);
        } else {
            return null;
        }
    }
}
