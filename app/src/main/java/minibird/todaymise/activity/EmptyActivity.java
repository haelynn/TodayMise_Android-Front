package minibird.todaymise.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import minibird.todaymise.R;

public class EmptyActivity extends AppCompatActivity {

    private Intent intent;
    private Geocoder geocoder = new Geocoder(this, Locale.getDefault());
    private List<Address> addresses = null;
    private LocationManager locationManager = null;
    private int flag;
    private String location;
    private SharedPreferences prefs;
    private double longtitude = 0, latitude = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        final int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        intent = new Intent(getApplicationContext(), minibird.todaymise.activity.MainActivity.class);

        prefs = getSharedPreferences("pref", MODE_PRIVATE);
        flag = prefs.getInt("intro", 0);
        location = prefs.getString("loc", "서울시 역삼동");


        if(Build.VERSION.SDK_INT >= 23){
            if(permissionCheck != PackageManager.PERMISSION_GRANTED){ // 허가 받지 않음
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            }else{ // 허가 받음
                if(flag > 0){
                    try{
                        Toast.makeText(getApplicationContext(), "수신 중", Toast.LENGTH_SHORT).show();
                        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1, mLocationListener);
                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 1, mLocationListener);
                    }catch (SecurityException ex){
                        Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch(requestCode){
            case 0 :
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    // 권한 허가
                    Toast.makeText(getApplicationContext(), "위치 접근 권한이 승인되었습니다", Toast.LENGTH_LONG).show();
                    if(flag == 0){
                        try{
                            locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
                            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1, mLocationListener);
                            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 1, mLocationListener);
                        }catch (SecurityException ex){
                            Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                        }
                        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putInt("intro", 1);
                        editor.commit();
                    }
                }
                else{
                    // 권한 거부
                    intent.putExtra("userLocation", location);
                    Toast.makeText(getApplicationContext(), "위치 접근 권한이 없습니다 x_x \n기본 지역으로 정보를 보내드립니다", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                    finish();
                }
                return;
        }
    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

            longtitude = location.getLongitude(); // 경도
            latitude = location.getLatitude(); // 위도
            // float accuracy = location.getAccuracy(); // 정확도
            // String provider = location.getProvider();

            try{
                addresses = geocoder.getFromLocation(
                        latitude, longtitude, 1
                );
            }catch(IOException e){

            }catch (IllegalArgumentException a){

            }
            if(addresses == null || addresses.size() == 0){
                intent.putExtra("userLocation", location);
            }else{
                Address address = addresses.get(0);
                String str = address.getLocality().toString() + " " + address.getThoroughfare().toString();
                intent.putExtra("userLocation", str);
                intent.putExtra("longtitude", longtitude);
                intent.putExtra("latitude", latitude);
                intent.putExtra("locality", address.getLocality().toString());
            }
            startActivity(intent);
            locationManager.removeUpdates(mLocationListener);
            finish();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };


}
