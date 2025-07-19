package com.example.bikerentsadeesh;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Edit_username extends AppCompatActivity {

    private ImageView ivBack;
    private EditText etNewUsername;
    private Button btnUpdateUsername;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_username);

        etNewUsername = findViewById(R.id.etUsername);
        btnUpdateUsername = findViewById(R.id.btnUpdateUsername);
        dbHelper = new DBHelper(this);
        ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(view -> onBackPressed());
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "Email");
        String username = dbHelper.getUsernameByEmail(email);
        etNewUsername.setText(username);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        update();
    }
    public void update() {
        btnUpdateUsername.setOnClickListener(view -> {
            String newUsername = etNewUsername.getText().toString().trim();

            if (newUsername.isEmpty()) {
                Toast.makeText(Edit_username.this, "Please enter a username", Toast.LENGTH_SHORT).show();
                return;
            }

            SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
            String userEmail = sharedPreferences.getString("email", null);

            if (userEmail == null) {
                Toast.makeText(Edit_username.this, "User email not found", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean updated = dbHelper.updateUsername(userEmail, newUsername);

            if (updated) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", newUsername);
                editor.apply();

                Toast.makeText(Edit_username.this, "Username updated successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(Edit_username.this, "Username update failed", Toast.LENGTH_SHORT).show();
            }
        });
    }


}