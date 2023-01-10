package com.example.brainfatigueapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

public class FitBitLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fit_bit_login);

        // Hide action bar
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        // Load fitbit login page
        Button startSurveyBtn = findViewById(R.id.fitbit_login_button);
        startSurveyBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.fitbit.com/oauth2/authorize?response_type=code&client_id=239457&scope=activity+cardio_fitness+electrocardiogram+heartrate+location+nutrition+oxygen_saturation+profile+respiratory_rate+settings+sleep+social+temperature+weight"));
            startActivity(intent);
        });
    }
}