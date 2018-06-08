package minibird.todaymise.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;

import java.text.SimpleDateFormat;
import java.util.Date;

import minibird.todaymise.R;

public class Main1Fragment extends Fragment{

    private long mNow;
    private Date mDate;
    private SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
    private ConstraintLayout locationLo;
    private Button detailBtn;
    private Button shareBtn;
    private MainActivity mainActivity;
    private ViewPager vp;
    private TextView location, curTime, comment;
    private ImageView conditionImg;
    private String userLocation;

    public static Main1Fragment newInstance(String loc){
        Main1Fragment frag = new Main1Fragment();
        Bundle bundle = new Bundle();
        bundle.putString("location", loc);
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userLocation = getArguments().getString("location");

     }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final ConstraintLayout constraintLayout = (ConstraintLayout)inflater.inflate(R.layout.main1, container, false);

        // findView
        detailBtn = (Button)constraintLayout.findViewById(R.id.main_detail_btn);
        shareBtn = (Button)constraintLayout.findViewById(R.id.main_share_btn);
        mainActivity = (MainActivity)getActivity();
        location = (TextView)constraintLayout.findViewById(R.id.main_place_tv);
        curTime = (TextView)constraintLayout.findViewById(R.id.main_time_tv);
        conditionImg = (ImageView)constraintLayout.findViewById(R.id.item_condition_iv);
        comment = (TextView)constraintLayout.findViewById(R.id.main_comment_tv);
        locationLo = (ConstraintLayout)constraintLayout.findViewById(R.id.main_location_lo);
        vp = (ViewPager)mainActivity.findViewById(R.id.main_vp);

        curTime.setText(getTime());
        location.setText(userLocation);

        detailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp.setCurrentItem(1);
            }
        });

        locationLo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "click", Toast.LENGTH_SHORT).show();
            }
        });

        return constraintLayout;
    }

    private String getTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);

        return mFormat.format(mDate);
    }

}
