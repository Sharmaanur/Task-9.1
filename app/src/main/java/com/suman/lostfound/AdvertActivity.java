package com.suman.lostfound;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.suman.lostfound.DB.DBHandler;
import com.suman.lostfound.DB.ItemModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdvertActivity extends AppCompatActivity {
    EditText name, phone, desc, date, location;
    Button btnsave;
    DBHandler dbHandler;
    RadioGroup postype;
    int type = 0;
    String latlng = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advert);
        name = findViewById(R.id.ed_name);
        phone = findViewById(R.id.ed_phone);
        desc = findViewById(R.id.ed_description);
        date = findViewById(R.id.ed_date);
        location = findViewById(R.id.ed_location);
        btnsave = findViewById(R.id.btn_save);
        dbHandler = new DBHandler(this);
        postype = findViewById(R.id.radio_group);
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!name.getText().toString().trim().isEmpty()){
                    ItemModel itemModel = new ItemModel();
                    if (type == 1){
                        itemModel.setName("Found "+name.getText().toString().trim());
                    }else{
                        itemModel.setName("Lost "+name.getText().toString().trim());
                    }

                    itemModel.setPhone(phone.getText().toString().trim());
                    itemModel.setDescription(desc.getText().toString().trim());
                    itemModel.setDate(date.getText().toString().trim());
                    itemModel.setLocation(latlng);
                    itemModel.setType(type);
                    dbHandler.addItem(itemModel);
                    name.setText("");
                    phone.setText("");
                    desc.setText("");
                    date.setText("");
                    location.setText("");
                }
            }
        });
        postype.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.type_lost:
                        type = 0;
                        break;
                    case R.id.type_found:
                        type = 1;
                        break;
                    default:
                        break;
                }
            }
        });

        location.setFocusable(false);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(AdvertActivity.this, PlacePick.class));
                if (!Places.isInitialized()) {
                    Places.initialize(getApplicationContext(), "AIzaSyD6WIvaTH38RsZRkfS8cQSRw0_LzSr8Pjw");
                }
        List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME);
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,fieldList).build(AdvertActivity.this);
        resultLauncher.launch(intent);
            }
        });


    }
    ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null){
                        Intent intent = result.getData();
                        Place place = Autocomplete.getPlaceFromIntent(intent);
                        location.setText(place.getAddress());
                        latlng = place.getLatLng().toString().replace("lat/lng: (","").replace(")","");
                        Toast.makeText(AdvertActivity.this, latlng, Toast.LENGTH_SHORT).show();

                    }

                }
            });
}