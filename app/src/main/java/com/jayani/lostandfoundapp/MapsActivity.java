package com.jayani.lostandfoundapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jayani.lostandfoundapp.databinding.ActivityMapsBinding;

import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    String itemType;
    double locationLat;
    double locationLong;
    ArrayList<itemModel> itemsLists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        itemType = intent.getStringExtra("itemType");

        if(itemType == "single") {
            locationLat = Double.parseDouble(intent.getStringExtra("itemLocationLat"));
            locationLong = Double.parseDouble(intent.getStringExtra("itemLocationLong"));
        } else if (itemType == "all") {
            dbHelper databaseHelperClass = new dbHelper(this);

            itemsLists = databaseHelperClass.getItemList();
        }


        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        Log.d("test", String.valueOf(latitude));
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.gmap);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if(itemType.equals("single")) {
            // Add a marker in Sydney and move the camera
            LatLng loc = new LatLng(locationLat, locationLong);
            mMap.addMarker(new MarkerOptions().position(loc).title("Marker"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc,10));

        }else if (itemType.equals("all")) {

            itemsLists.forEach(e -> {
                LatLng loc1 = new LatLng(Double.parseDouble(e.getlocationlat()), Double.parseDouble(e.getlocationlong()));
                mMap.addMarker(new MarkerOptions().position(loc1).title("Marker"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc1,10));
            });

        }
    }
}