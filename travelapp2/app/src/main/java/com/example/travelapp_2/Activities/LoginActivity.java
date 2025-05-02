package com.example.travelapp_2.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.travelapp_2.databinding.ActivityLoginBinding;

public class LoginActivity extends BaseActivity {
    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.emailEdit.getText().toString().trim();
                String password = binding.passwordEdit.getText().toString().trim();

                if (email.isEmpty()) {
                    binding.emailLayout.setError("Email kiritilmagan");
                    return;
                }
                if (password.isEmpty()) {
                    binding.passwordLayout.setError("Parol kiritilmagan");
                    return;
                }

                Toast.makeText(LoginActivity.this, "Muvaffaqiyatli kirildi", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        });
    }
} 