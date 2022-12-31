package com.example.brainfatigueapp;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.util.Arrays;

public class LoginPageActivity extends AppCompatActivity{

    GoogleSignInOptions gsi_options;
    GoogleSignInClient gsi_client;

    private CallbackManager callbackManager;
    private LoginManager loginManager;
    private String loginType = "Google";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Hide action bar
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();


        //printHashKey();

        System.out.flush();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure, You wanted to make decision");
        gsi_options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsi_client = GoogleSignIn.getClient(this, gsi_options);
        /*
        ImageView facebook_button = (ImageView) findViewById(R.id.facebook_icon);
        FacebookSdk.sdkInitialize(LoginPageActivity.this);
        callbackManager = CallbackManager.Factory.create();
        facebookLogin();
        TextView username = (TextView) findViewById(R.id.username);
        TextView password = (TextView) findViewById(R.id.password);

         */
        Button google_button = findViewById(R.id.login_with_google);

        google_button.setOnClickListener(v -> {
            loginType = "Google";
            GoogleSignInAccount lastAccount = GoogleSignIn.getLastSignedInAccount(this);
            if(lastAccount == null) {
                signIn();
            }
            else{
                navigateToHomeActivity();
            }
        });
        /*
        facebook_button.setOnClickListener(v -> {
            loginType = "Facebook";
            loginManager.logInWithReadPermissions(LoginPageActivity.this, Arrays.asList("email", "public_profile", "user_birthday"));
        });

         */

        Button loginButton = (Button) findViewById(R.id.login_continue_button);
        loginButton.setOnClickListener(v -> {
                    Intent intent = new Intent(LoginPageActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
                );

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (loginType.equals("Google")){
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 100){
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                handleSignInResult(task);
            }
        }
        else if (loginType.equals("Facebook")){
            // add this line
            callbackManager.onActivityResult(requestCode, resultCode, data);
            super.onActivityResult(requestCode, resultCode, data);
        }

    }


    private void printHashKey(){ // for the facebook login it needs a hashkey and this seems to be the most common way to find out what it is
            try {
                PackageInfo package_information = getPackageManager().getPackageInfo("com.example.brainfatigueapp", PackageManager.GET_SIGNATURES);
                for (Signature signature : package_information.signatures) {
                    MessageDigest sha_message_digest = MessageDigest.getInstance("SHA");
                    sha_message_digest.update(signature.toByteArray());
                    Log.d("The KeyHash is:", Base64.encodeToString(sha_message_digest.digest(), Base64.DEFAULT));
                    System.out.println("The KeyHash is:" + Base64.encodeToString(sha_message_digest.digest(), Base64.DEFAULT));
                }
            }

            catch (Exception e){
        }


    }
    private void signIn() {
        Intent signInIntent = gsi_client.getSignInIntent();
        startActivityForResult(signInIntent, 100);
    }

    void handleSignInResult(Task<GoogleSignInAccount> finishedTask){
        try{
            GoogleSignInAccount account = finishedTask.getResult(ApiException.class);
            navigateToHomeActivity();
        } catch (ApiException e){
            e.printStackTrace();
        }
    }

    void navigateToHomeActivity(){
        //finish();
        Intent intent = new Intent(LoginPageActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    //guide for how this all works found at https://www.geeksforgeeks.org/how-to-create-a-facebook-login-using-an-android-app/
    public void facebookLogin() {
        loginManager = LoginManager.getInstance();
        callbackManager = CallbackManager.Factory.create();
        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject json_object, GraphResponse response) {
                        if (json_object != null) {
                            try {
                                String name = json_object.getString("name");
                                String email = json_object.getString("email");
                                String fbUserID = json_object.getString("id");
                                disconnectFromFacebook();
                                navigateToHomeActivity();
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, name, email, gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Log.v("LoginScreen", "Facebook Login Cancelled");
            }

            @Override
            public void onError(FacebookException error) {
                Log.v("LoginScreen", "Facebook Login Error: " + error.getMessage());
            }
        });
    }

    // to disconnect from facebook as we just needed the account info we don't need to remain connected whilst using the app
    public void disconnectFromFacebook(){
        if (AccessToken.getCurrentAccessToken() == null) {
            return; // already disconnected from facebook, we don't need to do anything
        }

        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {
                LoginManager.getInstance().logOut();
            }
        }).executeAsync();
    }



}
