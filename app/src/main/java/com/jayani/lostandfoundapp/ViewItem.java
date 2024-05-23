package com.jayani.lostandfoundapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ViewItem extends AppCompatActivity {

    TextView TitleType,viewName,ViewPhone,ViewDescription,ViewDate,ViewLocation;
    Button btnDelete,btnViewLocation;
    String itemId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_item);

        Intent intent = getIntent();
        itemId = intent.getStringExtra("itemId");
        String itemType = intent.getStringExtra("itemType");
        String itemName = intent.getStringExtra("itemName");
        String itemPhone = intent.getStringExtra("itemPhone");
        String itemDescription = intent.getStringExtra("itemDescription");
        String itemDate = intent.getStringExtra("itemDate");
        String itemLocation = intent.getStringExtra("itemLocation");
        String itemLocationLat = intent.getStringExtra("itemLocationLat");
        String itemLocationLong = intent.getStringExtra("itemLocationLong");

        dbHelper databaseHelperClass = new dbHelper(this);
        TitleType = findViewById(R.id.TitleType);
        viewName = findViewById(R.id.viewName);
        ViewPhone = findViewById(R.id.ViewPhone);
        ViewDescription = findViewById(R.id.ViewDescription);
        ViewDate = findViewById(R.id.ViewDate);
        ViewLocation = findViewById(R.id.ViewLocation);
        btnDelete = findViewById(R.id.btnDelete);
        btnViewLocation = findViewById(R.id.btnViewLocation);

        if(itemLocationLat == null){
            btnViewLocation.setVisibility(View.INVISIBLE);
        }else{
            btnViewLocation.setVisibility(View.VISIBLE);
        }

        if(Integer.parseInt(itemType)==1){
            TitleType.setText("Found Item");
        }else{
            TitleType.setText("Lost Item");
        }

        viewName.setText(itemName);
        ViewPhone.setText(itemPhone);

        ViewDescription.setText(itemDescription);
        ViewDate.setText(itemDate);

        ViewLocation.setText(String.valueOf(itemLocation));

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id =Integer.parseInt(itemId);
                databaseHelperClass.deleteTask(id);
//                itemAdapter.notifyChange();
                Intent mainAct = new Intent(view.getContext(), MainActivity.class);
                view.getContext().startActivity(mainAct);


            }
        });

        btnViewLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewItem = new Intent(getApplicationContext(), MapsActivity.class);

                viewItem.putExtra("itemType", "single");
                viewItem.putExtra("itemLocationLat", itemLocationLat);
                viewItem.putExtra("itemLocationLong", itemLocationLong);

                startActivity(viewItem);
            }
        });


    }
}