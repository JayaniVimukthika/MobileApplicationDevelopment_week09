package com.jayani.lostandfoundapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button btnNew,btnShowAll,btnShowAllMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btnNew = findViewById(R.id.btnNew);
        btnShowAll = findViewById(R.id.btnShowAll);
        btnShowAllMap = findViewById(R.id.btnShowAllMap);

        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addItem = new Intent(view.getContext(), AddNewItem.class);
                view.getContext().startActivity(addItem);
            }
        });

        btnShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent showAll = new Intent(view.getContext(), ShowAllItems.class);
                view.getContext().startActivity(showAll);
            }
        });

        btnShowAllMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent showAll = new Intent(view.getContext(),MapsActivity.class);
//                view.getContext().startActivity(showAll);

                Intent viewItem = new Intent(getApplicationContext(), MapsActivity.class);

                viewItem.putExtra("itemType", "all");

                startActivity(viewItem);
            }
        });


    }
}