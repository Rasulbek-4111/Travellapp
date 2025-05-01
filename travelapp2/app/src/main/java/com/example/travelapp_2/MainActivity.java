package com.example.travelapp_2;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.travelapp_2.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
} 