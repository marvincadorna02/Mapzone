package com.example.mapzone;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Map fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Disable default zoom controls
        mMap.getUiSettings().setZoomControlsEnabled(false);

        // Center sa Davao, zoom 15
        LatLng davao = new LatLng(7.0731, 125.6130);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(davao, 15));

        // Add 10 restaurants as markers
        LatLng[] restaurants = new LatLng[]{
                new LatLng(7.0760, 125.6080), // Jack’s Ridge
                new LatLng(7.0640, 125.6070), // Tiny Kitchen
                new LatLng(7.0675, 125.6115), // Yellow Fin
                new LatLng(7.0700, 125.6150), // Lachi’s Pastries
                new LatLng(7.0650, 125.6100), // Rotisserie de Bacolod
                new LatLng(7.0685, 125.6170), // Bigby’s
                new LatLng(7.0710, 125.6095), // Davao Dencia’s
                new LatLng(7.0695, 125.6125), // Gourdo’s
                new LatLng(7.0740, 125.6145), // The Greenhouse
                new LatLng(7.0720, 125.6180)  // RBG Bar & Grill
        };

        String[] names = new String[]{
                "Jack’s Ridge",
                "Tiny Kitchen",
                "Yellow Fin",
                "Lachi’s Pastries",
                "Rotisserie de Bacolod",
                "Bigby’s",
                "Davao Dencia’s",
                "Gourdo’s",
                "The Greenhouse",
                "RBG Bar & Grill"
        };

        for (int i = 0; i < restaurants.length; i++) {
            mMap.addMarker(new MarkerOptions()
                    .position(restaurants[i])
                    .title(names[i])
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        }

        // Custom zoom buttons
        Button zoomIn = findViewById(R.id.zoom_in);
        Button zoomOut = findViewById(R.id.zoom_out);

        zoomIn.setOnClickListener(v -> mMap.animateCamera(CameraUpdateFactory.zoomIn()));
        zoomOut.setOnClickListener(v -> mMap.animateCamera(CameraUpdateFactory.zoomOut()));
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}
