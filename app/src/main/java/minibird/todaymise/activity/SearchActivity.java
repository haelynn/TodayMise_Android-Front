package minibird.todaymise.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import minibird.todaymise.R;

public class SearchActivity extends AppCompatActivity implements OnConnectionFailedListener{

    private GoogleApiClient mGoogleApiClient;
    private Geocoder geocoder = new Geocoder(this, Locale.getDefault());
    private List<Address> addresses = null;
    private Intent intent;
    private MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mainActivity = (MainActivity)mainActivity.mainActivity;
        intent = new Intent(getApplicationContext(), minibird.todaymise.activity.MainActivity.class);

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        AutocompleteFilter filter = new AutocompleteFilter.Builder()
                .setTypeFilter(Place.TYPE_COUNTRY)
                .setCountry("KR")
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_REGIONS)
                .build();
        autocompleteFragment.setFilter(filter);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                double latitude = place.getLatLng().latitude;
                double longitude = place.getLatLng().longitude;

                try{
                    addresses = geocoder.getFromLocation(
                            latitude, longitude, 1
                    );
                }catch(IOException e){

                }catch (IllegalArgumentException a){

                }

                if(addresses == null || addresses.size() == 0){
                    // 실패
                    finish();
                }else{
                    Address address = addresses.get(0);
                    String str = address.getLocality().toString();
                    if(address.getThoroughfare() != null)
                        str += " " + address.getThoroughfare().toString();
                    mainActivity.finish();
                    intent.putExtra("userLocation", str);

                    // Toast.makeText(getApplicationContext(), latitude + longitude + str, Toast.LENGTH_LONG).show();

                    SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("loc", str);
                    editor.commit();

                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                System.out.println( "An error occurred: " + status);
            }
        });

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getApplicationContext(), "google connect error", Toast.LENGTH_LONG).show();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }
}
