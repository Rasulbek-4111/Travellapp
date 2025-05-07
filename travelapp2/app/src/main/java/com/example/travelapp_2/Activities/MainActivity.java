package com.example.travelapp_2.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelapp_2.Adapter.CategoryAdapter;
import com.example.travelapp_2.Adapter.PopularAdapter;
import com.example.travelapp_2.Model.CategoryModel;
import com.example.travelapp_2.Model.ItemModel;
import com.example.travelapp_2.R;
import com.example.travelapp_2.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initCategory();
        initPopular();
        setupSearch();
        setupBottomNavigation();
    }

    private void initPopular() {
        DatabaseReference myRef= database.getReference("Popular");
        binding.progressBarPopular.setVisibility(View.VISIBLE);

        ArrayList<ItemModel> list=new ArrayList<>();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot issue: snapshot.getChildren()){
                        list.add(issue.getValue(ItemModel.class));
                    }
                    if (!list.isEmpty()){
                        binding.recyclerViewPopular.setLayoutManager(new LinearLayoutManager(MainActivity.this,
                                LinearLayoutManager.HORIZONTAL,false));
                        RecyclerView.Adapter adapter=new PopularAdapter(list);
                        binding.recyclerViewPopular.setAdapter(adapter);
                    }
                    binding.progressBarPopular.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initCategory() {
        DatabaseReference myref=database.getReference("Category");
        binding.progressBarCategory.setVisibility(View.VISIBLE);
        ArrayList<CategoryModel> list=new ArrayList<>();
        myref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot issue:snapshot.getChildren()){
                        list.add(issue.getValue(CategoryModel.class));
                    }
                    if (!list.isEmpty()){
                        binding.recyclerViewCategory.setLayoutManager(
                                new GridLayoutManager(MainActivity.this,4)
                        );
                        RecyclerView.Adapter adapter=new CategoryAdapter(list);
                        binding.recyclerViewCategory.setAdapter(adapter);
                    }
                    binding.progressBarCategory.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setupSearch() {
        // Qidiruv tugmasini bosganda
        binding.button.setOnClickListener(v -> {
            String query = binding.searchEditText.getText().toString().trim();
            if (!query.isEmpty()) {
                searchRegions(query);
            }
        });
    }

    private void searchRegions(String query) {
        if (query.isEmpty()) return;

        DatabaseReference myRef = database.getReference("Popular");
        binding.progressBarPopular.setVisibility(View.VISIBLE);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        ItemModel item = issue.getValue(ItemModel.class);
                        if (item != null && item.getTitle().toLowerCase().contains(query.toLowerCase())) {
                            // Found matching region, open DetailActivity
                            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                            intent.putExtra("object", item);
                            startActivity(intent);
                            binding.progressBarPopular.setVisibility(View.GONE);
                            return;
                        }
                    }
                }
                binding.progressBarPopular.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                binding.progressBarPopular.setVisibility(View.GONE);
            }
        });
    }

    private void setupBottomNavigation() {
        binding.bottomNav.setMenuResource(R.menu.bottom_menu);
        binding.bottomNav.showBadge(R.id.home);
        binding.bottomNav.setOnItemSelectedListener(itemId -> {
            if (itemId == R.id.home) {
                // Asosiy sahifa
            } else if (itemId == R.id.explorer) {
                // Xarita sahifasiga o'tish
                startActivity(new Intent(MainActivity.this, MapActivity.class));
            } else if (itemId == R.id.bookmark) {
                // Saqlangan joylar
            } else if (itemId == R.id.profile) {
                // Profil
            }
        });
    }
} 