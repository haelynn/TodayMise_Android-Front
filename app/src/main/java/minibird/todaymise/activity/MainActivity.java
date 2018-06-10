package minibird.todaymise.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import minibird.todaymise.R;


import java.util.List;

public class MainActivity extends AppCompatActivity{

    public static Activity mainActivity;
    static int MAX_PAGE = 2;
    static Fragment cur_fragment = new Fragment();
    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;
    private ImageButton homeBtn;
    private ImageButton maskBtn;
    private ImageButton infoBtn;
    private ImageButton settingBtn;
    private Intent intent;
    private ViewPager viewPager;
    private static String userLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivity = MainActivity.this;

        FindView();
        homeBtn.setImageResource(R.drawable.menu_home_on);
        viewPager = (ViewPager)findViewById(R.id.main_vp);
        intent = getIntent();
        userLocation = intent.getExtras().getString("userLocation");

        viewPager.setAdapter(new adapter(getSupportFragmentManager()));

        // 페이지 이동
        homeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(viewPager.getCurrentItem() == 1)
                    viewPager.setCurrentItem(0);
            }
        });
        maskBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), minibird.todaymise.activity.MaskActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
        infoBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), minibird.todaymise.activity.InformationActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
        settingBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), minibird.todaymise.activity.SettingActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
    }

    static public class adapter extends FragmentStatePagerAdapter {
        public adapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position<0 || MAX_PAGE <= position) return null;

            switch(position){
                case 0: return Main1Fragment.newInstance(userLocation);
                case 1: return Main2Fragment.newInstance(userLocation);
            }
            return cur_fragment;
        }

        @Override
        public int getCount() {
            return MAX_PAGE;
        }
    }

    @Override
    public void onBackPressed() {

        if(viewPager.getCurrentItem() == 1){
            viewPager.setCurrentItem(0);
        }
        else{
            long tempTime = System.currentTimeMillis();
            long intervalTime = tempTime - backPressedTime;

            if(0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime)
                super.onBackPressed();
            else{
                backPressedTime = tempTime;
                Toast.makeText(getApplicationContext(), "한 번 더 누르면 앱이 종료됩니다", Toast.LENGTH_SHORT).show();
            }
        }
    }


    // findview
    private void FindView(){
        homeBtn = (ImageButton)findViewById(R.id.menu_home_btn);
        maskBtn = (ImageButton)findViewById(R.id.menu_mask_btn);
        infoBtn = (ImageButton)findViewById(R.id.menu_info_btn);
        settingBtn = (ImageButton)findViewById(R.id.menu_setting_btn);
    }
}
