package com.suman.lostfound;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.suman.lostfound.DB.DBHandler;
import com.suman.lostfound.DB.ItemModel;

import java.util.ArrayList;
import java.util.List;

public class FoundActivity extends AppCompatActivity {
    DBHandler dbHandler;
   List<ItemModel> items;
   ListView listView;
   ArrayAdapter<ItemModel> arrayAdapter;
   SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found);
        dbHandler = new DBHandler(this);
        items = new ArrayList<>();
        items =  dbHandler.getAllItems();
        listView = findViewById(R.id.list_view);
        searchView = findViewById(R.id.search_view);
        arrayAdapter = new ArrayAdapter<ItemModel>(this, android.R.layout.simple_dropdown_item_1line, items);
        listView.setAdapter(arrayAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                arrayAdapter.getFilter().filter(newText);
                return false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ItemModel itemModel = (ItemModel) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(FoundActivity.this, RemovePostActivity.class);
                intent.putExtra("id", itemModel.getId());
                intent.putExtra("name", itemModel.getName());
                intent.putExtra("phone", itemModel.getPhone());
                intent.putExtra("desc", itemModel.getDescription());
                intent.putExtra("date", itemModel.getDate());
                intent.putExtra("location", itemModel.getLocation());
                intent.putExtra("type", itemModel.getType());
               startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        items =  dbHandler.getAllItems();
        arrayAdapter = new ArrayAdapter<ItemModel>(this, android.R.layout.simple_dropdown_item_1line, items);
        listView.setAdapter(arrayAdapter);
    }
}