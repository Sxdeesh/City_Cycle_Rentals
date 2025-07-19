package com.example.bikerentsadeesh;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Register extends AppCompatActivity {

    private EditText etname, etemail, etpw, etconfirmpw, etPhone;
    private TextView tvlogin;
    private Button btnregister;
    private RadioGroup rgGender;
    private ImageView ivProfile;
    private DBHelper dbHelper;
    private byte[] profileImage;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        etname = findViewById(R.id.etname);
        etemail = findViewById(R.id.etemail);
        etpw = findViewById(R.id.etpw);
        etconfirmpw = findViewById(R.id.etconfirmpw);
        tvlogin = findViewById(R.id.tvlogin);
        btnregister = findViewById(R.id.btnregister);
        rgGender = findViewById(R.id.rgGender);
        etPhone = findViewById(R.id.etPhone);
        ivProfile = findViewById(R.id.ivProfile);
        dbHelper = new DBHelper(this);

        ivProfile.setOnClickListener(v -> openGallery());

        btnregister.setOnClickListener(v -> registerUser());

        tvlogin.setOnClickListener(v -> {
            Intent intent = new Intent(Register.this, Login.class);
            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.register), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            ivProfile.setImageURI(imageUri);
            profileImage = convertImageToByteArray(imageUri);
        }
    }

    private byte[] convertImageToByteArray(Uri imageUri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            return stream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void registerUser() {
        String username = etname.getText().toString().trim();
        String email = etemail.getText().toString().trim();
        String password = etpw.getText().toString().trim();
        String confirmPassword = etconfirmpw.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        int genderId = rgGender.getCheckedRadioButtonId();
        String gender = genderId != -1 ? ((RadioButton) findViewById(genderId)).getText().toString() : "";

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || gender.isEmpty() || profileImage == null) {
            Toast.makeText(this, "Please fill in all fields and select an image", Toast.LENGTH_SHORT).show();
        } else if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords must match", Toast.LENGTH_SHORT).show();
        } else if (password.length()<6) {
            etpw.setError("Password must be at least 6 characters long");
            Toast.makeText(this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show();
        } else if (username.length()<4) {
            etname.setError("Username must be at least 4 characters long");
            Toast.makeText(this, "Username must be at least 4 characters long", Toast.LENGTH_SHORT).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etemail.setError("Invalid email format");
            Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show();
        } else if (dbHelper.isEmailExists(email)) {
            etemail.setError("Email already registered");
            Toast.makeText(this, "Email already registered. Please use a different email.", Toast.LENGTH_SHORT).show();
        } else {
            boolean result = dbHelper.insertUser(username, email, gender, phone, password, profileImage);
            if (result) {
                Toast.makeText(this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Register.this, Login.class));
            } else {
                Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
