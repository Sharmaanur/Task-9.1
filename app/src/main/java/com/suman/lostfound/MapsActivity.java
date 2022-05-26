package com.suman.lostfound;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.suman.lostfound.DB.DBHandler;
import com.suman.lostfound.DB.ItemModel;
import com.suman.lostfound.databinding.ActivityMapsBinding;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    DBHandler dbHandler;
    List<ItemModel> items;

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        dbHandler = new DBHandler(this);
        items = new ArrayList<>();
        items =  dbHandler.getAllItems();

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
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        try {
            for (int i = 0; i < items.size(); i++) {
                String latlng[] = items.get(i).getLocation().split(",");
                mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(latlng[0]), Double.parseDouble(latlng[1]))).title(""));
            }


            //mMap.addMarker(new MarkerOptions().position(new LatLng(-32, 251)).title(""));
            String latlng2[] = items.get(-1).getLocation().split(",");
            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(Double.parseDouble(latlng2[0]), Double.parseDouble(latlng2[1]))));
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}