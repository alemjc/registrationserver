package com.apps.alemjc.gymdemo;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class TrainerProfileActivity extends AppCompatActivity {

    private TextView mAboutMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_profile);

        mAboutMe = (TextView) findViewById(R.id.trainer_about_me);
        mAboutMe.setText("I like working out. I'm a challenger in League of Legends. The only thing bigger " +
                "than my rippling biceps is my heart, which is also a muscle. I know it's a muscle because " +
                "I'm a doctor: a doctor of love.");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Train with Robin?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                Toast.makeText(getApplicationContext(), "Payment Confirmed",Toast.LENGTH_SHORT).show();
            }
        });

        // Create Dialog
        AlertDialog dialog = builder.create();


    }




    }
}
