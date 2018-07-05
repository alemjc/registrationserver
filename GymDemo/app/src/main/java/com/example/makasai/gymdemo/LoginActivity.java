package com.example.makasai.gymdemo;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

//    This method needs to be substituted for the workout activity
    public void startWorkoutActivity(View view) {
        Intent intent = new Intent(this, WorkoutRoutine.class);
        startActivity(intent);
    }
//    I created this just to jump to the map activity
    public void startSlidingActivity(View view) {
        Intent intent = new Intent(this, SlidingActivity.class);
        startActivity(intent);
    }
}

