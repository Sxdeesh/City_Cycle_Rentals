package com.example.bikerentsadeesh;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class EditProfilePhotoActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int GALLERY_REQUEST_CODE = 101;

    private ImageView ivProfileImage, ivBack;
    private TextView tvUsername, tvEmail;
    private Button btnChangePhoto, btnSave;
    private DBHelper dbHelper;
    private String currentEmail;
    private Bitmap selectedImageBitmap;
    private boolean isImageChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_photo);

        initializeViews();
        setupClickListeners();
        loadUserData();
    }

    private void initializeViews() {
        ivProfileImage = findViewById(R.id.ivProfileImage);
        ivBack = findViewById(R.id.ivBack);
        tvUsername = findViewById(R.id.tvUsername);
        tvEmail = findViewById(R.id.tvEmail);
        btnChangePhoto = findViewById(R.id.btnChangePhoto);
        btnSave = findViewById(R.id.btnSave);
        dbHelper = new DBHelper(this);
    }

    private void setupClickListeners() {
        ivBack.setOnClickListener(v -> finish());

        btnChangePhoto.setOnClickListener(v -> showImagePickerDialog());

        btnSave.setOnClickListener(v -> saveProfilePhoto());

        ivProfileImage.setOnClickListener(v -> showImagePickerDialog());
    }

    private void loadUserData() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        currentEmail = sharedPreferences.getString("email", "");

        if (!currentEmail.isEmpty()) {
            // Get username from database
            String username = dbHelper.getUsernameByEmail(currentEmail);
            tvUsername.setText(username);
            tvEmail.setText(currentEmail);

            // Load profile image
            byte[] imageData = dbHelper.getUserImageByEmail(currentEmail);
            if (imageData != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                ivProfileImage.setImageBitmap(bitmap);
                selectedImageBitmap = bitmap;
            } else {
                ivProfileImage.setImageResource(R.drawable.animated);
            }
        }
    }

    private void showImagePickerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Profile Photo");
        builder.setItems(new String[]{"Camera", "Gallery"}, (dialog, which) -> {
            if (which == 0) {
                openCamera();
            } else {
                openGallery();
            }
        });
        builder.show();
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
        } else {
            Toast.makeText(this, "Camera not available", Toast.LENGTH_SHORT).show();
        }
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (galleryIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
        } else {
            Toast.makeText(this, "Gallery not available", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == CAMERA_REQUEST_CODE) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                if (bitmap != null) {
                    selectedImageBitmap = bitmap;
                    ivProfileImage.setImageBitmap(bitmap);
                    isImageChanged = true;
                    btnSave.setVisibility(View.VISIBLE);
                }
            } else if (requestCode == GALLERY_REQUEST_CODE) {
                Uri imageUri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    selectedImageBitmap = bitmap;
                    ivProfileImage.setImageBitmap(bitmap);
                    isImageChanged = true;
                    btnSave.setVisibility(View.VISIBLE);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void saveProfilePhoto() {
        if (selectedImageBitmap != null && isImageChanged) {
            // Convert bitmap to byte array
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            selectedImageBitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
            byte[] imageBytes = byteArrayOutputStream.toByteArray();

            // Update image in database
            boolean success = dbHelper.updateUserImage(currentEmail, imageBytes);

            if (success) {
                Toast.makeText(this, "Profile photo updated successfully", Toast.LENGTH_SHORT).show();
                btnSave.setVisibility(View.GONE);
                isImageChanged = false;

                // Update SharedPreferences if needed
                SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                // You might want to store some flag here
                editor.apply();

                // Set result to notify calling activity
                setResult(RESULT_OK);
            } else {
                Toast.makeText(this, "Failed to update profile photo", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No changes to save", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        if (isImageChanged) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Unsaved Changes");
            builder.setMessage("You have unsaved changes. Do you want to save before leaving?");
            builder.setPositiveButton("Save", (dialog, which) -> {
                saveProfilePhoto();
                finish();
            });
            builder.setNegativeButton("Discard", (dialog, which) -> finish());
            builder.setNeutralButton("Cancel", null);
            builder.show();
        } else {
            super.onBackPressed();
        }
    }
}