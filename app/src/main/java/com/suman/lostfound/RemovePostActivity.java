package com.suman.lostfound;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.suman.lostfound.DB.DBHandler;
import com.suman.lostfound.DB.ItemModel;

public class RemovePostActivity extends AppCompatActivity {
    DBHandler dbHandler;
    Intent intent;
    TextView name, date, location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_post);
        dbHandler = new DBHandler(this);

        intent = getIntent();
        name = findViewById(R.id.item_name);
        date = findViewById(R.id.item_date);
        location = findViewById(R.id.item_desc);
        name.setText(intent.getStringExtra("name"));
        date.setText(intent.getStringExtra("date"));
        location.setText("At "+intent.getStringExtra("location"));

        findViewById(R.id.btn_remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemModel itemModel = new ItemModel();
                itemModel.setId(intent.getStringExtra("id"));
                dbHandler.deleteClient(itemModel);
            }
        });

    }
}