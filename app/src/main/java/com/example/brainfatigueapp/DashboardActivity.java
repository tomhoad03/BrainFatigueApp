package com.example.brainfatigueapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class DashboardActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private FitBitAPIHandler fitBitAPIHandler;

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

        // Fitbit button
        final MaterialButton fitbitBtn = findViewById(R.id.activity_dashboard_fitbit_button);
        fitbitBtn.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, FitBitLoginActivity.class);
            startActivity(intent);
        });

        // Database access
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            FatigueDatabase fatigueDatabase = FatigueDatabase.getDatabase(getApplicationContext());
            SurveyResultDao surveyResultDao = fatigueDatabase.surveyResultDao();

            List<SurveyResult> surveyResults = surveyResultDao.getAll();
            Log.d("survey_results", surveyResults.toString());
        });
        executorService.shutdown();
        //set the fitbitAPI handler to be null
        fitBitAPIHandler = null;

        // Fragments
        FragmentManager fm = getSupportFragmentManager();
        ViewStateAdapter sa = new ViewStateAdapter(fm, getLifecycle());
        final ViewPager2 pa = findViewById(R.id.activity_dashboard_view_pager_1);
        pa.setAdapter(sa);

        tabLayout = findViewById(R.id.activity_dashboard_tab_bar);
        tabLayout.addTab(tabLayout.newTab().setText("Daily Summary"));
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

        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
        handleIntent(getIntent());
    }

    private void handleIntent(Intent intent) {
        String appLinkAction = intent.getAction();
        Uri appLinkData = intent.getData();
        if (Intent.ACTION_VIEW.equals(appLinkAction) && appLinkData != null) {
            //the full URL
            String urlString = String.valueOf(appLinkData);
            //
            int codeStart = urlString.indexOf("?code=");
            // codestart + 5 gives you the starting equals so 6 is correct
            //Toast.makeText(this, "code = "+urlString.substring(codeStart +6), Toast.LENGTH_LONG).show();
            int codeEnd = urlString.indexOf("#_=_");
            String accessCode = urlString.substring(codeStart +6, codeEnd);
            //Toast.makeText(this, "code = "+ accessCode, Toast.LENGTH_LONG).show();

            new Thread(() -> {
                fitBitAPIHandler = new FitBitAPIHandler();
                fitBitAPIHandler.authoriseUser(accessCode);
                Log.d("userProfile", fitBitAPIHandler.getUserActivities());

            }).start();
        }
    }

    public FitBitAPIHandler getFitBitAPIHandler() {
        return fitBitAPIHandler;
    }
}
