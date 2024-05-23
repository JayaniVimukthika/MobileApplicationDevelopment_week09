package com.jayani.lostandfoundapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ShowAllItems extends AppCompatActivity {

    private RecyclerView itemRecyclerView;
    private itemAdapter itemAdapters;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_all_items);

        dbHelper databaseHelperClass = new dbHelper(this);

        ArrayList<itemModel> itemsLists = databaseHelperClass.getItemList();

        itemRecyclerView = findViewById(R.id.itemRecycler);
        itemRecyclerView.setHasFixedSize(true);
        itemRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemAdapters =new itemAdapter(itemsLists);
        itemRecyclerView.setAdapter(itemAdapters);


        itemAdapters.setOnClickListner(new itemAdapter.OnItemClickListner() {
            @Override
            public void onItemClick(int position) {
                int itemId =itemsLists.get(position).getId();
                int itemType =itemsLists.get(position).getPostType();
                String itemName =itemsLists.get(position).getName();
                String itemPhone =itemsLists.get(position).getPhone();
                String itemDescription =itemsLists.get(position).getDescription();
                String itemDate =itemsLists.get(position).getDate();
                String itemLocation =itemsLists.get(position).getLocation();
                String itemLocationLat =itemsLists.get(position).getlocationlat();
                String itemLocationLong =itemsLists.get(position).getlocationlong();

                Intent viewItem = new Intent(getApplicationContext(), ViewItem.class);

                viewItem.putExtra("itemId", String.valueOf(itemId));
                viewItem.putExtra("itemType", String.valueOf(itemType));
                viewItem.putExtra("itemName", itemName);
                viewItem.putExtra("itemPhone", itemPhone);
                viewItem.putExtra("itemDescription", itemDescription);
                viewItem.putExtra("itemDate", itemDate);
                viewItem.putExtra("itemLocation", itemLocation);
                viewItem.putExtra("itemLocationLat", itemLocationLat);
                viewItem.putExtra("itemLocationLong", itemLocationLong);
                startActivity(viewItem);
            }
        });

    }
}