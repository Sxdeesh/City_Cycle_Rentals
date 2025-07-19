package com.example.bikerentsadeesh;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Edit_profile_details extends AppCompatActivity {

    private DBHelper dbHelper;
    private TextView tvUsername, tvEmail, tvContact, tvGender;
    private ImageView ivUsername, ivContact, ivProfileImage, ivBack, ivPhotoEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_profile_details);

        ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(view ->onBackPressed());
        ivProfileImage = findViewById(R.id.ivProfileImage);
        tvUsername = findViewById(R.id.tvUsername);
        ivPhotoEdit = findViewById(R.id.ivPhotoEdit);
        tvEmail = findViewById(R.id.tvEmail);
        tvContact = findViewById(R.id.tvContact);
        tvGender = findViewById(R.id.tvGender);
        ivUsername = findViewById(R.id.ivUsername);
        ivContact = findViewById(R.id.ivContact);
        dbHelper = new DBHelper(this);

        ivUsername.setOnClickListener(view -> {
            Intent intent = new Intent(Edit_profile_details.this, Edit_username.class);
            startActivity(intent);
        });
        ivContact.setOnClickListener(view -> {
            Intent intent = new Intent(Edit_profile_details.this, Edit_contact.class);
            startActivity(intent);
        });

        ivPhotoEdit.setOnClickListener(view -> {
            Intent intent = new Intent(Edit_profile_details.this, EditProfilePhotoActivity.class);
            startActivity(intent);
        });

        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "User");
        String email = sharedPreferences.getString("email", "Email");
        String gender = dbHelper.getGenderByEmail(email);
        String contact = dbHelper.getPhoneByEmail(email);

        if (email != null) {
            byte[] imageData = dbHelper.getUserImageByEmail(email);
            if (imageData != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                ivProfileImage.setImageBitmap(bitmap);
            } else {
                ivProfileImage.setImageResource(R.drawable.animated);
            }
        }

        tvUsername.setText(username);
        tvEmail.setText(email);
        tvContact.setText(contact);
        tvGender.setText(gender);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        refreshUserDetails();
    }

    private void refreshUserDetails() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "User");
        String email = sharedPreferences.getString("email", "Email");
        String gender = dbHelper.getGenderByEmail(email);
        String contact = dbHelper.getPhoneByEmail(email);

        if (email != null) {
            byte[] imageData = dbHelper.getUserImageByEmail(email);
            if (imageData != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                ivProfileImage.setImageBitmap(bitmap);
            } else {
                ivProfileImage.setImageResource(R.drawable.animated);
            }
        }
        tvUsername.setText(username);
        tvEmail.setText(email);
        tvContact.setText(contact);
        tvGender.setText(gender);
    }


}