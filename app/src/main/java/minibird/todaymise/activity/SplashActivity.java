package minibird.todaymise.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import minibird.todaymise.R;

public class SplashActivity extends AppCompatActivity {

    private Intent intent;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        intent = new Intent(getApplicationContext(), minibird.todaymise.activity.EmptyActivity.class);
        handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run() {

                startActivity(intent);
                finish();
            }
        }, 1000);
    }
}
