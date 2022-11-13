package com.example.brainfatigueapp;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;

public class LoginPageActivity extends AppCompatActivity{

    GoogleSignInOptions gsi_options;
    GoogleSignInClient gsi_client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Hide action bar
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        gsi_options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsi_client = GoogleSignIn.getClient(this, gsi_options);

        TextView username = (TextView) findViewById(R.id.username);
        TextView password = (TextView) findViewById(R.id.password);
        ImageView google_button = (ImageView) findViewById(R.id.google_icon) ;

        google_button.setOnClickListener(v -> {
            signIn();
        });

        Button loginButton = (Button) findViewById(R.id.login_continue_button);
        loginButton.setOnClickListener(v -> {
                    Intent intent = new Intent(LoginPageActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
                );

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                navigateToHomeActivity();

            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }

    void signIn(){
        Intent signinIntent = gsi_client.getSignInIntent();
        startActivityForResult(signinIntent, 1000);
    }

    void navigateToHomeActivity(){
        finish();
        Intent intent = new Intent(LoginPageActivity.this, HomeActivity.class);
        startActivity(intent);
    }

}
