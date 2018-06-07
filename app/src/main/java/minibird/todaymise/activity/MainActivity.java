package minibird.todaymise.activity;

import android.content.Intent;
import android.content.IntentSender;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.places.Places;

import minibird.todaymise.R;

public class MainActivity extends AppCompatActivity implements OnConnectionFailedListener{

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

    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        FindView();
        homeBtn.setImageResource(R.drawable.menu_home_on);

        viewPager = (ViewPager)findViewById(R.id.main_vp);
        viewPager.setAdapter(new adapter(getSupportFragmentManager()));

        // 페이지 이동
        maskBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "준비 중인 서비스입니다", Toast.LENGTH_LONG).show();
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

    static public class adapter extends FragmentPagerAdapter {
        public adapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position<0 || MAX_PAGE <= position) return null;

            switch (position){
                case 0:
                    cur_fragment = new Main1Fragment();
                    break;
                case 1: cur_fragment = new Main2Fragment();
                    break;
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
                Toast.makeText(getApplicationContext(), "한번 더 누르면 앱이 종료됩니다", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    // findview
    private void FindView(){
        homeBtn = (ImageButton)findViewById(R.id.menu_home_btn);
        maskBtn = (ImageButton)findViewById(R.id.menu_mask_btn);
        infoBtn = (ImageButton)findViewById(R.id.menu_info_btn);
        settingBtn = (ImageButton)findViewById(R.id.menu_setting_btn);
    }
}
