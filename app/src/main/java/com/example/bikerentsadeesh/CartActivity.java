package com.example.bikerentsadeesh;

import static java.security.AccessController.getContext;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private RecyclerView rvCartItem;
    private CartAdapter cartAdapter;
    private ImageView ivBack;
    private List<RentedBike> rentedBikes;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(view ->onBackPressed());
        rvCartItem = findViewById(R.id.rvCartItem);
        dbHelper = new DBHelper(this);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        String loggedInUserEmail = sharedPreferences.getString("email", null);

        if (loggedInUserEmail != null) {
            rentedBikes = dbHelper.getRentalDetailsByEmail(loggedInUserEmail);
        }
        else {
            rentedBikes = new ArrayList<>();
        }
        if (rentedBikes.isEmpty()) {
            Toast.makeText(this, "No rented bikes found", Toast.LENGTH_SHORT).show();
        };
        cartAdapter = new CartAdapter(this, rentedBikes);
        rvCartItem.setLayoutManager(new LinearLayoutManager(this));
        rvCartItem.setAdapter(cartAdapter);
    }
}
