package com.apps.alemjc.gymdemo.Utils;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.lang.reflect.Array;
import java.security.PolicySpi;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import com.apps.alemjc.gymdemo.R;
import com.apps.alemjc.gymdemo.Profile.TrainerProfileActivity;
import de.hdodenhof.circleimageview.CircleImageView;
import com.apps.alemjc.gymdemo.models.User;
import com.apps.alemjc.gymdemo.models.Trainer;
import com.apps.alemjc.gymdemo.models.Photo;
import com.apps.alemjc.gymdemo.models.UserAccountSettings;
import com.apps.alemjc.gymdemo.models.UserSettings;
/**
 * Created by User on 9/22/2017.
 */

public class FilterFeedListAdapter extends ArrayAdapter<Photo> {

    public interface OnLoadMoreItemsListener{
        void onLoadMoreItems();
    }
    OnLoadMoreItemsListener mOnLoadMoreItemsListener;

    private static final String TAG = "FilterFeedListAdapter";

    private LayoutInflater mInflater;
    private int mLayoutResource;
    private Context mContext;
    private String currentUsername = "";

    public FilterFeedListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Photo> objects) {
        super(context, resource, objects);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLayoutResource = resource;
        this.mContext = context;
    }

    static class ViewHolder{
        CircleImageView mprofileImage;
        String likesString;
        TextView name, timeDetla, caption, likes, comments, reviews, cost;
        SquareImageView image;
        ImageView heartRed, heartWhite, comment;

        UserAccountSettings settings = new UserAccountSettings();
        User user  = new User();
        StringBuilder users;
        String mLikesString;
        boolean likeByCurrentUser;
        GestureDetector detector;
        Photo photo;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final ViewHolder holder;

        if(convertView == null){
            convertView = mInflater.inflate(mLayoutResource, parent, false);
            holder = new ViewHolder();

            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.image = (SquareImageView) convertView.findViewById(R.id.post_image);
            holder.caption = (TextView) convertView.findViewById(R.id.image_caption);
//            holder.mprofileImage = (CircleImageView) convertView.findViewById(R.id.profile_photo);
            holder.photo = getItem(position);
            holder.users = new StringBuilder();
            holder.reviews = (TextView) convertView.findViewById(R.id.trainer_reviews);
            holder.cost = (TextView) convertView.findViewById(R.id.cost);

            convertView.setTag(holder);

        }else{
            holder = (ViewHolder) convertView.getTag();
        }

//        //get the current users username (need for checking likes string)
//        getCurrentUsername();

        //set the caption
        holder.caption.setText(getItem(position).getCaption());

        //set reviews

        //set cost
        holder.cost.setText(getItem(position).getCost());

        //set reviews
        holder.reviews.setText(getItem(position).getReviews());

        //set the profile image
        final ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(getItem(position).getImage_path(), holder.image);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating to profile of: " +
                        holder.user.getUsername());

                Intent intent = new Intent(mContext, TrainerProfileActivity.class);
                intent.putExtra(mContext.getString(R.string.calling_activity),
                        mContext.getString(R.string.home_activity));
                intent.putExtra(mContext.getString(R.string.intent_user), holder.user);
                mContext.startActivity(intent);
            }
        });
//        if(reachedEndOfList(position)){
//            loadMoreData();
//        }

        return convertView;
    }

    private boolean reachedEndOfList(int position){
        return position == getCount() - 1;
    }

    private void loadMoreData(){

        try{
            mOnLoadMoreItemsListener = (OnLoadMoreItemsListener) getContext();
        }catch (ClassCastException e){
            Log.e(TAG, "loadMoreData: ClassCastException: " +e.getMessage() );
        }

        try{
            mOnLoadMoreItemsListener.onLoadMoreItems();
        }catch (NullPointerException e){
            Log.e(TAG, "loadMoreData: ClassCastException: " +e.getMessage() );
        }
    }

    public class GestureListener extends GestureDetector.SimpleOnGestureListener{

        ViewHolder mHolder;
        public GestureListener(ViewHolder holder) {
            mHolder = holder;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
    }

    private void getCurrentUsername(){
        Log.d(TAG, "getCurrentUsername: retrieving user account settings");
    }

}
