package com.example.brainfatigueapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class HomeActivity extends AppCompatActivity {

    GoogleSignInOptions gsi_options;
    GoogleSignInClient gsi_client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Hide action bar
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        // Start survey button
        final Button startSurveyBtn = findViewById(R.id.activity_home_survey_button);
        startSurveyBtn.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, SurveyStartActivity.class);
            startActivity(intent);
        });

        // Open the dashboard button
        final Button dashboardBtn = findViewById(R.id.activity_home_dashboard_button);
        dashboardBtn.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, DashboardActivity.class);
            startActivity(intent);
        });

        TextView welcomeMessage = findViewById(R.id.activity_home_welcome);
        gsi_options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsi_client = GoogleSignIn.getClient(this, gsi_options);
        GoogleSignInAccount googleAccount = GoogleSignIn.getLastSignedInAccount(this);
        if(googleAccount != null){
            String firstName = googleAccount.getGivenName();
            welcomeMessage.setText("Hi " + firstName + ", What would you like to do today?");
        }
    }
}