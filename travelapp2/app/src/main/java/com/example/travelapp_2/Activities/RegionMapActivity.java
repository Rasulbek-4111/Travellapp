package com.example.travelapp_2.Activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.travelapp_2.Model.RegionModel;
import com.example.travelapp_2.Model.HotelModel;
import com.example.travelapp_2.R;
import com.example.travelapp_2.databinding.ActivityRegionMapBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class RegionMapActivity extends BaseActivity implements OnMapReadyCallback {
    private ActivityRegionMapBinding binding;
    private GoogleMap mMap;
    private RegionModel region;

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegionMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        region = (RegionModel) getIntent().getSerializableExtra("region");
        if (region != null) {
            binding.regionTitle.setText(region.getName());
        }

        // Xarita fragmentini o'rnatish
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        } else {
            // Xarita fragmenti topilmaganda xatolikni ko'rsatish
            showToast("Xarita yuklanmadi. Iltimos, qaytadan urinib ko'ring.");
            finish();
        }

        binding.backButton.setOnClickListener(v -> finish());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            mMap = googleMap;
            
            // Viloyat koordinatalarini olish
            String[] coords = region.getCoordinates().split(",");
            double lat = Double.parseDouble(coords[0].replace("° N", "").trim());
            double lng = Double.parseDouble(coords[1].replace("° E", "").trim());
            
            LatLng regionLocation = new LatLng(lat, lng);
            
            // Xaritada viloyat markazini ko'rsatish
            mMap.addMarker(new MarkerOptions()
                    .position(regionLocation)
                    .title(region.getName()));
            
            // Xaritani viloyat markaziga markazlashtirish
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(regionLocation, 10));
            
            // Mehmonxonalarni qo'shish
            addHotels();
        } catch (Exception e) {
            showToast("Xarita yuklanmadi. Iltimos, qaytadan urinib ko'ring.");
            finish();
        }
    }

    private void addHotels() {
        // Bu yerda Firebase dan mehmonxonalar ma'lumotlarini olish va xaritada ko'rsatish kerak
        DatabaseReference hotelsRef = database.getReference("Hotels");
        hotelsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot hotelSnapshot : snapshot.getChildren()) {
                        HotelModel hotel = hotelSnapshot.getValue(HotelModel.class);
                        if (hotel != null && hotel.getRegion().equals(region.getName())) {
                            LatLng hotelLocation = new LatLng(hotel.getLatitude(), hotel.getLongitude());
                            mMap.addMarker(new MarkerOptions()
                                    .position(hotelLocation)
                                    .title(hotel.getName())
                                    .snippet(hotel.getAddress()));
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xatolik yuz berganda
            }
        });
    }
} 