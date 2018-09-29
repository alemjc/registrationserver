package com.apps.alemjc.gymdemo;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

public class TrainerProfileActivity extends AppCompatActivity {

    private TextView mAboutMe;
    private Button mTrainerButton;
    private String trainerAboutMeText;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_profile);
        setTitle("Robin");
        ActionBar mActionBar = getSupportActionBar();

        mTrainerButton = (Button) findViewById(R.id.trainerButton);
        mAboutMe = (TextView) findViewById(R.id.trainer_about_me);

        trainerAboutMeText = "I like working out. I'm a challenger in League of Legends. The only thing bigger " +
                "than my rippling biceps is my heart, which is also a muscle. I know it's a muscle because " +
                "I'm a doctor: a doctor of love.";
        mAboutMe.setText(trainerAboutMeText);

        mTrainerButton.setOnClickListener(new View.OnClickListener() {
            // Open AlertDialog to confirm payment
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PaymentActivity.class);
                startActivity(intent);
//                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
//                alertDialogBuilder.setTitle("Train with Robin?");
//
//                //Using dialog layout. I think it will be easier to customize the dialogue this way
//                LayoutInflater inflater = getLayoutInflater();
//
//                alertDialogBuilder.setView(inflater.inflate(R.layout.dialog_confirm_trainer, null))
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                //test Toast
//                                Toast.makeText(context, "You did it =)", Toast.LENGTH_SHORT).show();
//                            }
//                        })
//                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                dialogInterface.cancel();
//                            }
//                        });
//                AlertDialog alertDialog = alertDialogBuilder.create();
//
//                alertDialog.show();
            }
        });




    }




    }
