package com.example.travelapp_2.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelapp_2.Adapter.RegionAdapter;
import com.example.travelapp_2.Model.RegionModel;
import com.example.travelapp_2.R;
import com.example.travelapp_2.databinding.ActivityMapBinding;

import java.util.ArrayList;

public class MapActivity extends BaseActivity {
    ActivityMapBinding binding;
    private ArrayList<RegionModel> regions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupRegions();
        setupBackButton();
        setupBottomNavigation();
    }

    private void setupBackButton() {
        binding.backButton.setOnClickListener(v -> finish());
    }

    private void setupBottomNavigation() {
        binding.bottomNav.setMenuResource(R.menu.bottom_menu);
        binding.bottomNav.showBadge(R.id.explorer);
        binding.bottomNav.setOnItemSelectedListener(itemId -> {
            if (itemId == R.id.home) {
                // Asosiy sahifaga o'tish
                startActivity(new Intent(MapActivity.this, MainActivity.class));
                finish();
            } else if (itemId == R.id.explorer) {
                // Xarita sahifasida qolish
            } else if (itemId == R.id.bookmark) {
                // Saqlangan joylar
            } else if (itemId == R.id.profile) {
                // Profil
            }
        });
    }

    private void setupRegions() {
        regions = new ArrayList<>();
        // O'zbekiston viloyatlari
        regions.add(new RegionModel("Toshkent", "41.3111° N, 69.2797° E", "Toshkent viloyati"));
        regions.add(new RegionModel("Andijon", "40.7833° N, 72.3333° E", "Andijon viloyati"));
        regions.add(new RegionModel("Buxoro", "39.7667° N, 64.4333° E", "Buxoro viloyati"));
        regions.add(new RegionModel("Farg'ona", "40.3864° N, 71.7864° E", "Farg'ona viloyati"));
        regions.add(new RegionModel("Jizzax", "40.1167° N, 67.8500° E", "Jizzax viloyati"));
        regions.add(new RegionModel("Namangan", "41.0000° N, 71.6667° E", "Namangan viloyati"));
        regions.add(new RegionModel("Navoiy", "40.0833° N, 65.3833° E", "Navoiy viloyati"));
        regions.add(new RegionModel("Qashqadaryo", "38.8667° N, 65.8000° E", "Qashqadaryo viloyati"));
        regions.add(new RegionModel("Samarqand", "39.6542° N, 66.9597° E", "Samarqand viloyati"));
        regions.add(new RegionModel("Sirdaryo", "40.8500° N, 68.6667° E", "Sirdaryo viloyati"));
        regions.add(new RegionModel("Surxondaryo", "37.2333° N, 67.2667° E", "Surxondaryo viloyati"));
        regions.add(new RegionModel("Xorazm", "41.5333° N, 60.6333° E", "Xorazm viloyati"));

        binding.recyclerViewRegions.setLayoutManager(new GridLayoutManager(this, 2));
        binding.recyclerViewRegions.setAdapter(new RegionAdapter(regions, region -> {
            // Viloyat tanlanganda Google Maps da ochish
            String[] coords = region.getCoordinates().split(",");
            double lat = Double.parseDouble(coords[0].replace("° N", "").trim());
            double lng = Double.parseDouble(coords[1].replace("° E", "").trim());
            
            String uri = String.format("geo:%f,%f?q=%f,%f(%s)", lat, lng, lat, lng, region.getName());
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, android.net.Uri.parse(uri));
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        }));
    }
} 