package com.example.android_lab_assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity  {

    private final int REQUEST_CODE = 1;

    GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    LocationCallback locationCallback;
    LocationRequest locationRequest;

    double latitude, longitude;
    double dest_lat, dest_lng;

    final int RADIUS = 1500;

    String url;

    static boolean directionRequested;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        dest_lat = latLng.latitude;
//        dest_lng = latLng.longitude;
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
       // this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                //WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        //requestPermission();
        String apiKey = getString(R.string.api_key);

        /**
         * Initialize Places. For simplicity, the API key is hard-coded. In a production
         * environment we recommend using a secure mechanism to manage API keys.
         */
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }

// Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(this);
        addFragment(new MapFragment(),false,"one");
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Object[] dataTransfer;
                switch (item.getItemId())
                {
                    case R.id.action_map:
                        Toast.makeText(MainActivity.this,"Map",Toast.LENGTH_SHORT).show();
                        addFragment(new MapFragment(),false,"one");
                        break;
                    case R.id.action_list:
                        Toast.makeText(MainActivity.this,"list",Toast.LENGTH_SHORT).show();
                        //addFragment(new ListFragment(),false,"one");
                        openFragment(new ListFragment());

                        break;

                    case R.id.action_direction:
                        dataTransfer = new Object[3];
                        url = getDirectionUrl();
                        dataTransfer[0] = mMap;
                        dataTransfer[1] = url;
                        dataTransfer[2] = new LatLng(dest_lat, dest_lng);

                        GetDirectionsData getDirectionsData = new GetDirectionsData();
                        // execute asynchronously
                        getDirectionsData.execute(dataTransfer);
//                        if (view.getId() == R.id.go_btn)
//                            directionRequested = false;
//                        else
//                            directionRequested = true;


                        Toast.makeText(MainActivity.this,"direction",Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });



    }

    private String getDirectionUrl() {
        StringBuilder googleDirectionUrl = new StringBuilder("https://maps.googleapis.com/maps/api/directions/json?");
        googleDirectionUrl.append("origin="+latitude+","+longitude);
        googleDirectionUrl.append("&destination="+dest_lat+","+dest_lng);
        googleDirectionUrl.append("&key="+getString(R.string.api_key_places));
        Log.d("", "getDirectionUrl: "+googleDirectionUrl);
        return googleDirectionUrl.toString();
    }


    public void addFragment(Fragment fragment, boolean addToBackStack, String tag) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();

        if (addToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.replace(R.id.back_view, fragment, tag);
        ft.commitAllowingStateLoss();
    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.back_view, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }




}
