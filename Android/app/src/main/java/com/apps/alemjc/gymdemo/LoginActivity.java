package com.apps.alemjc.gymdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.util.Log;

import com.apps.alemjc.gymdemo.Filter.FilterActivity;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity{

    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d(TAG, "onCreate: starting.");
    }

//    This method needs to be substituted for the workout activity
    public void startFilterActivity(View view) {
        Intent intent = new Intent(this, FilterActivity.class);
        startActivity(intent);
    }
}

