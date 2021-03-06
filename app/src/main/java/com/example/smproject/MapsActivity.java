package com.example.smproject;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.smproject.databinding.ActivityMapsBinding;

import java.util.List;
import java.util.Locale;

import lombok.SneakyThrows;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    GoogleMapOptions options = new GoogleMapOptions();

    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     binding = ActivityMapsBinding.inflate(getLayoutInflater());
     setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        bundle = getIntent().getExtras();
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
    @SneakyThrows
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if(bundle != null){
            String[] ll = bundle.getString("latLng").split(",");
            double latitude = Double.parseDouble(ll[0]);
            double longitude = Double.parseDouble(ll[1]);
            LatLng latLng = new LatLng(latitude, longitude);

            mMap.addMarker(new MarkerOptions().position(latLng));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

            Geocoder gdc = new Geocoder(MapsActivity.this, Locale.getDefault());
            List<Address> address = gdc.getFromLocation(latLng.latitude, latLng.longitude,1);
            if(address.size()>0){
                String countryName = address.get(0).getCountryName();
                System.out.println(countryName);
            }
        }
        mMap.setTrafficEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        options.mapType(GoogleMap.MAP_TYPE_SATELLITE)
                .compassEnabled(false)
                .rotateGesturesEnabled(false)
                .tiltGesturesEnabled(false);

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @lombok.SneakyThrows
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(latLng));

                Geocoder gdc = new Geocoder(MapsActivity.this, Locale.getDefault());
                List<Address> address = gdc.getFromLocation(latLng.latitude, latLng.longitude,1);
                if(address.size()>0){
                    String countryName = address.get(0).getCountryName();
                    System.out.println(countryName);
                }
            }
        });
    }
}