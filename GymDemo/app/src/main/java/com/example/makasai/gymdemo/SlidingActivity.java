package com.example.makasai.gymdemo;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SlidingDrawer;
import android.widget.Toast;


public class SlidingActivity extends AppCompatActivity {
    SlidingDrawer drawer;
    Button confirm, privateSessionButton, groupSessionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding);
        drawer = (SlidingDrawer) findViewById(R.id.drawer);
        confirm = (Button) findViewById(R.id.confirm);
        privateSessionButton = (Button) findViewById(R.id.privateSessionButton);
        groupSessionButton = (Button) findViewById(R.id.groupSessionButton);
        drawer.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {
            @Override
            public void onDrawerClosed() {
                confirm.setVisibility(View.INVISIBLE);
            }
        });
    }

    // This method should be added to the mapView. We might need to figure out how to untoggle
    // it...perhaps if the gym is highlighted or not?
    public void toggleDrawerState(View view) {
        drawer.toggle();
    }

    public void privateSession(View view) {
        confirm.setVisibility(View.VISIBLE);
        privateSessionButton.setBackgroundColor(getResources().getColor(R.color.red));
        groupSessionButton.setBackgroundColor(000000);
    }

    public void groupSession(View view) {
        confirm.setVisibility(View.VISIBLE);
        groupSessionButton.setBackgroundColor(getResources().getColor(R.color.red));
        privateSessionButton.setBackgroundColor(000000);
    }

//    Use back button to close sliding drawer when pops up
    @Override
    public void onBackPressed() {
        if (drawer.isOpened()) {
            drawer.close();
        } else {
            super.onBackPressed();
        }
    }
//    This should start the next activity. I created a dummy activity "DummyActivity"
    public void startTrainerListActivity(View view) {
        Intent intent = new Intent(this, DummyActivity.class);
        startActivity(intent);
    }


}
