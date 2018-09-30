package com.apps.alemjc.gymdemo.Filter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.apps.alemjc.gymdemo.Utils.FilterFeedListAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.apps.alemjc.gymdemo.R;
import com.apps.alemjc.gymdemo.models.Photo;
import com.apps.alemjc.gymdemo.models.UserAccountSettings;

/**
 * Created by User on 5/28/2017.
 */

public class FilterFragment extends Fragment {
    private static final String TAG = "FilterFragment";

    //vars
    private ArrayList<Photo> mPhotos;
    private ArrayList<String> mFollowing;
    private ListView mListView;
    private FilterFeedListAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter, container, false);
        mListView = (ListView) view.findViewById(R.id.listView);
        mFollowing = new ArrayList<>();
        mPhotos = new ArrayList<>();

        getPhotos();

        return view;
    }

    private void getPhotos(){
        Log.d(TAG, "getPhotos: getting photos");

        Photo photo1 = new Photo();
        photo1.setCaption("Hello");
        photo1.setTags("not sure");
        photo1.setImage_path("drawable://"+R.drawable.robin_baby);
        photo1.setPhoto_id("1");

        mPhotos.add(photo1);

        Photo photo2 = new Photo();
        photo2.setCaption("Robin");
        photo2.setTags("What is this");
        photo2.setImage_path("drawable://"+R.drawable.robin_duke);
        photo2.setPhoto_id("2");

        mPhotos.add(photo2);


        Photo photo3 = new Photo();
        photo3.setCaption("This is weird");
        photo3.setTags("Adding tags");
        photo3.setImage_path("drawable://"+R.drawable.robin_shirtless);
        photo3.setPhoto_id("3");
        displayPhotos();

    }

    private void displayPhotos(){
        if(mPhotos != null){
            Log.d(TAG, "getPhotos: displaying photos");
//            Collections.sort(mPhotos, new Comparator<Photo>() {
//                @Override
//                public int compare(Photo o1, Photo o2) {
//                    return o2.getDate_created().compareTo(o1.getDate_created());
//                }
//            });

            mAdapter = new FilterFeedListAdapter(getActivity(), R.layout.layout_filter_listitem, mPhotos);
            mListView.setAdapter(mAdapter);
        }
    }
}


