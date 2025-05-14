package com.example.travelapp_2.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.travelapp_2.Model.UserModel;
import com.example.travelapp_2.databinding.ActivityRegisterBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends BaseActivity {
    ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupRegisterButton();
    }

    private void setupRegisterButton() {
        binding.registerButton.setOnClickListener(v -> {
            if (validateInputs()) {
                registerUser();
            }
        });
    }

    private boolean validateInputs() {
        String name = binding.nameEditText.getText().toString().trim();
        String email = binding.emailEditText.getText().toString().trim();
        String phone = binding.phoneEditText.getText().toString().trim();
        String password = binding.passwordEditText.getText().toString().trim();
        String confirmPassword = binding.confirmPasswordEditText.getText().toString().trim();

        if (name.isEmpty()) {
            binding.nameInputLayout.setError("Ismni kiriting");
            return false;
        }

        if (email.isEmpty()) {
            binding.emailInputLayout.setError("Emailni kiriting");
            return false;
        }

        if (phone.isEmpty()) {
            binding.phoneInputLayout.setError("Telefon raqamni kiriting");
            return false;
        }

        if (password.isEmpty()) {
            binding.passwordInputLayout.setError("Parolni kiriting");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            binding.confirmPasswordInputLayout.setError("Parollar mos kelmadi");
            return false;
        }

        return true;
    }

    private void registerUser() {
        String name = binding.nameEditText.getText().toString().trim();
        String email = binding.emailEditText.getText().toString().trim();
        String phone = binding.phoneEditText.getText().toString().trim();
        String password = binding.passwordEditText.getText().toString().trim();

        binding.progressBar.setVisibility(View.VISIBLE);
        binding.registerButton.setEnabled(false);

        // Firebase'da email mavjudligini tekshirish
        DatabaseReference usersRef = database.getReference("Users");
        usersRef.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    binding.progressBar.setVisibility(View.GONE);
                    binding.registerButton.setEnabled(true);
                    Toast.makeText(RegisterActivity.this, "Bu email allaqachon ro'yxatdan o'tgan", Toast.LENGTH_SHORT).show();
                } else {
                    // Yangi foydalanuvchi yaratish
                    String userId = usersRef.push().getKey();
                    UserModel user = new UserModel(userId, name, email, phone, password);

                    usersRef.child(userId).setValue(user)
                            .addOnSuccessListener(aVoid -> {
                                binding.progressBar.setVisibility(View.GONE);
                                binding.registerButton.setEnabled(true);
                                Toast.makeText(RegisterActivity.this, "Ro'yxatdan o'tish muvaffaqiyatli yakunlandi", Toast.LENGTH_SHORT).show();
                                finish();
                            })
                            .addOnFailureListener(e -> {
                                binding.progressBar.setVisibility(View.GONE);
                                binding.registerButton.setEnabled(true);
                                Toast.makeText(RegisterActivity.this, "Xatolik yuz berdi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                binding.progressBar.setVisibility(View.GONE);
                binding.registerButton.setEnabled(true);
                Toast.makeText(RegisterActivity.this, "Xatolik yuz berdi: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
} 