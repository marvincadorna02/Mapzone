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

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(),
                    "AIzaSyAuf_zfsV1iAxDlMQYLfbnh5Bk7VD7g9p8"); // imong API key
        }

        // Load Map Fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // 🔎 Autocomplete Search Bar (Places API)
        AutocompleteSupportFragment autocompleteFragment =
                (AutocompleteSupportFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.autocomplete_fragment);

        if (autocompleteFragment != null) {
            // Set fields to return
            autocompleteFragment.setPlaceFields(Arrays.asList(
                    Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));

            // Set hint text
            autocompleteFragment.setHint("Try search a place");

            // Apply custom background (para dili transparent)
            if (autocompleteFragment.getView() != null) {
                autocompleteFragment.getView().setBackgroundResource(R.drawable.autocomplete_bg);
            }

            // Listener kung mag-pili ug lugar
            autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(Place place) {
                    if (mMap != null && place.getLatLng() != null) {
                        LatLng location = place.getLatLng();
                        mMap.addMarker(new MarkerOptions()
                                .position(location)
                                .title(place.getName())
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 17));
                    }
                }

                @Override
                public void onError(com.google.android.gms.common.api.Status status) {
                    // Log error if needed
                }
            });
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Disable default zoom controls
        mMap.getUiSettings().setZoomControlsEnabled(false);

        // Default center sa Davao
        LatLng davao = new LatLng(7.0731, 125.6130);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(davao, 13));

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
