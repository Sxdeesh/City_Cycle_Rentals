package com.example.bikerentsadeesh;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Startup_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_startup_page);

        // Check if user is already logged in
        checkIfUserLoggedIn();

        Button btngogreen = findViewById(R.id.btngogreen);

        btngogreen.setOnClickListener(v -> {
            Intent intent = new Intent(Startup_page.this, Login.class);
            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void checkIfUserLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        String savedUsername = sharedPreferences.getString("username", null);
        String savedEmail = sharedPreferences.getString("email", null);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        // If user credentials exist and user is marked as logged in,
        // skip startup page and go directly to HomeActivity
        if (savedUsername != null && savedEmail != null && isLoggedIn) {
            Intent intent = new Intent(Startup_page.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }
}