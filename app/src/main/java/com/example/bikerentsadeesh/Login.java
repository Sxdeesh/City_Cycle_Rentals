package com.example.bikerentsadeesh;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Login extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvRegister;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Check if user is already logged in
        checkIfUserLoggedIn();

        etEmail = findViewById(R.id.etusername);
        etPassword = findViewById(R.id.etloginpw);
        btnLogin = findViewById(R.id.btnlogin);
        tvRegister = findViewById(R.id.tvregister);
        dbHelper = new DBHelper(this);

        btnLogin.setOnClickListener(view -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                return;
            }
            if (dbHelper.checkUser(email, password)){
                String username = dbHelper.getUsernameByEmail(email);

                // Save user credentials for auto-login
                saveUserCredentials(username, email);

                // Check if this is the first login
                SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
                boolean isFirstLogin = sharedPreferences.getBoolean("isFirstLogin", true);

                if (isFirstLogin) {
                    // Mark that first login is complete
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("isFirstLogin", false);
                    editor.apply();

                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();

                    // For first login, show welcome page
                    Intent intent = new Intent(Login.this, WelcomeActivity.class);
                    startActivity(intent);
                } else {
                    // For subsequent logins, go directly to HomeActivity
                    Toast.makeText(this, "Welcome back!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, HomeActivity.class);
                    startActivity(intent);
                }
                finish();
            }
            else {
                Toast.makeText(this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
            }
        });

        tvRegister.setOnClickListener(view -> {
            Intent intent = new Intent(Login.this, Register.class);
            startActivity(intent);
        });
    }

    private void checkIfUserLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        String savedUsername = sharedPreferences.getString("username", null);
        String savedEmail = sharedPreferences.getString("email", null);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        // If user credentials exist and user is marked as logged in, redirect to HomeActivity
        if (savedUsername != null && savedEmail != null && isLoggedIn) {
            Intent intent = new Intent(Login.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void saveUserCredentials(String username, String email) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.putString("email", email);
        editor.putBoolean("isLoggedIn", true); // Mark user as logged in
        editor.apply();
    }

    // Method to clear user session (call this when user logs out)
    public static void clearUserSession(android.content.Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear(); // Clear all stored data
        editor.apply();
    }
}