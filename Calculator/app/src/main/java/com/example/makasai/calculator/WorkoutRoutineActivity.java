package com.example.makasai.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class WorkoutRoutineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        Intent intent = getIntent();
        String message = "Workout Routine";
        TextView messageTextView = (TextView) findViewById(R.id.messageTextView);
        messageTextView.setText(message);

    }
}
