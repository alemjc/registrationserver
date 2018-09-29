package com.apps.alemjc.gymdemo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;

import android.util.Log;


import com.apps.alemjc.gymdemo.R;
import com.apps.alemjc.gymdemo.Utils.BottomNavigationViewHelper;

import com.apps.alemjc.gymdemo.Utils.BottomNavigationViewEx;


public class FilterActivity extends AppCompatActivity{
    private static final String TAG = "FilterActivity";
    private static final int ACTIVITY_NUM = 4;

    private Context mContext = FilterActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        Log.d(TAG, "onCreate: started.");
    }

    /**
     * BottomNavigationView setup
     */
    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
//        BottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

}
