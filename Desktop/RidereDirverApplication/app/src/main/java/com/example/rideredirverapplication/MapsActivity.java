package com.example.rideredirverapplication;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.IOException;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    //    private SearchView searchView;
    private SearchView searchView1; // Added the second SearchView
    private SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Initialize our search views.
//        searchView = findViewById(R.id.idSearchView);
        searchView1 = findViewById(R.id.idSearchView1); // Initialize the second SearchView

        // Obtain the SupportMapFragment and get notified
        // when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.Map);

        // Add a query listener for the first search view.
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                // Get the location name from the search view.
//                String location = searchView.getQuery().toString();
//                performSearch(location);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });

        // Add a query listener for the second search view.
        searchView1.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Get the location name from the search view.
                String location = searchView1.getQuery().toString();
                performSearch(location);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        // Call getMapAsync to initialize the map.
        mapFragment.getMapAsync(this);
    }

    private void performSearch(String location) {
        // Create a list to store addresses.
        List<Address> addressList = null;

        // Check if the entered location is not null and not empty.
        if (location != null && !location.isEmpty()) {
            // Create and initialize a geocoder.
            Geocoder geocoder = new Geocoder(MapsActivity.this);
            try {
                // Get location from the location name and add it to the address list.
                addressList = geocoder.getFromLocationName(location, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Check if the address list is not null and not empty.
            if (addressList != null && !addressList.isEmpty()) {
                // Get the first address from the list.
                Address address = addressList.get(0);

                // Create a LatLng object for the location.
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                // Clear existing markers on the map.
                mMap.clear();

                // Add a marker to that position.
                mMap.addMarker(new MarkerOptions().position(latLng).title(location));

                // Animate the camera to that position.
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }
}