package com.jayani.lostandfoundapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddNewItem extends AppCompatActivity {
    RadioButton lost, found;
    EditText name, phone, description, date, location;

    int mYear, mMonth, mDay;
    DatePickerDialog datePickerDialog;
    Button createItem, btnCurrentLocation;
    private Integer postType = 0;

    private final int FINE_PERMISSION_CODE = 1;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;

    private Geocoder geocoder;
    LatLng latLng =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_new_item);

        lost = findViewById(R.id.rlost);
        found = findViewById(R.id.rFound);
        name = findViewById(R.id.itemName);
        phone = findViewById(R.id.phoneNumber);
        description = findViewById(R.id.itemDes);
        date = findViewById(R.id.itemDate);
        location = findViewById(R.id.location);
        createItem = findViewById(R.id.btnSave);
        lost.setChecked(true);
        found.setChecked(false);
        btnCurrentLocation = findViewById(R.id.btnCurrentLocation);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        geocoder = new Geocoder(this, Locale.getDefault());
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//        getLastLocation();

        date.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(this,
                        (view1, year, monthOfYear, dayOfMonth) -> {
                            date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            datePickerDialog.dismiss();
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
            return true;
        });

        btnCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getLastLocation();
//                location.setText((int) currentLocation.getLatitude());
            }
        });

        createItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (validateFields()) {
                if (found.isChecked()) {
                    postType = 1;
                } else {
                    postType = 0;
                }
                dbHelper db = new dbHelper(AddNewItem.this);
            itemModel itemModel = new itemModel(postType, name.getText().toString(), phone.getText().toString(), description.getText().toString(), date.getText().toString(), location.getText().toString(),String.valueOf(latLng.latitude),String.valueOf(latLng.longitude));
                db.addItem(itemModel);
                itemAdapter.notifyChange();
                Toast.makeText(AddNewItem.this, "Add Task Successfully", Toast.LENGTH_SHORT).show();
                finish();

//                    startActivity(getIntent());
//                }

            }
        });
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},FINE_PERMISSION_CODE);
            return;
        }

        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    currentLocation = location;
                    latLng=new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
                    convertLatLngToAddress(latLng);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == FINE_PERMISSION_CODE){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLastLocation();
            }else{
                Toast.makeText(this,"LOcation Permission Denied",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String convertLatLngToAddress(LatLng latLng) {
        try {
            List<Address> addresses = geocoder.getFromLocation(
                    latLng.latitude,
                    latLng.longitude,
                    1);

            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                String fullAddress = address.getAddressLine(0);
                // You can use other address properties like address.getLocality(), address.getCountryName(), etc.
                Toast.makeText(this, fullAddress, Toast.LENGTH_SHORT).show();

                location.setText(fullAddress);
            } else {
                Toast.makeText(this, "No address found", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Geocoding failed", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

}