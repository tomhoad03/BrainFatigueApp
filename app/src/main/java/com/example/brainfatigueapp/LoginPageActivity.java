package com.example.brainfatigueapp;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

public class LoginPageActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Hide action bar
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        TextView username = (TextView) findViewById(R.id.username);
        TextView password = (TextView) findViewById(R.id.password);

        Button loginButton = (Button) findViewById(R.id.login_continue_button);
        loginButton.setOnClickListener(v -> {
                    Intent intent = new Intent(LoginPageActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
                );

    }
}
