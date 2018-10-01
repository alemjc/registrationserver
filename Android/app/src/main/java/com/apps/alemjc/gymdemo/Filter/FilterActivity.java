package com.apps.alemjc.gymdemo.Filter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;


import android.support.v4.view.ViewPager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import android.util.Log;

import com.apps.alemjc.gymdemo.Utils.SectionsPagerAdapter;

import com.apps.alemjc.gymdemo.R;
import com.apps.alemjc.gymdemo.Utils.UniversalImageLoader;
import com.nostra13.universalimageloader.core.ImageLoader;


public class FilterActivity extends AppCompatActivity{
    private static final String TAG = "FilterActivity";
    private static final int ACTIVITY_NUM = 4;
    private static final int FILTER_FRAGMENT = 1;


    //widgets
    private ViewPager mViewPager;
    private FrameLayout mFrameLayout;
    private RelativeLayout mRelativeLayout;

    private Context mContext = FilterActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        mViewPager = (ViewPager) findViewById(R.id.viewpager_container);
        mFrameLayout = (FrameLayout) findViewById(R.id.container);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.relLayout1);

        Log.d(TAG, "onCreate: started.");

        initImageLoader();
        setupViewPager();
    }

    private void initImageLoader(){
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(mContext);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }

    private void setupViewPager(){
            SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
            adapter.addFragment(new TrainerListFragment()); //index 1
            mViewPager.setAdapter(adapter);

            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(mViewPager);
    }
    @Override
    public void onStart() {
        super.onStart();
        mViewPager.setCurrentItem(FILTER_FRAGMENT);
    }
}
