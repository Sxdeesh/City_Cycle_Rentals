package com.example.bikerentsadeesh;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class Rent_Activity extends AppCompatActivity {

    private EditText etRentDate, etReturnDate, etEmail;
    private Spinner spBikeType;

    private ImageView ivBack;
    private Button btnRent;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_rent);

        etRentDate = findViewById(R.id.etRentDate);
        etReturnDate = findViewById(R.id.etReturnDate);
        etEmail = findViewById(R.id.etEmail);
        btnRent = findViewById(R.id.btnRent);
        dbHelper = new DBHelper(this);
        spBikeType = findViewById(R.id.spBikeType);
        ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(view -> onBackPressed());

        String[] bikeTypes = {"Select a bike type", "Mountain Bike", "Road Bike", "Hybrid Bike", "Electric Bike", "City Bike", "Folding Bike", "Cargo Bike", "BMX Bike", "Track Bike", "Touring Bike"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bikeTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBikeType.setAdapter(adapter);


        btnRent.setOnClickListener(view -> insertRental());

        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "Email");
        etEmail.setText(email);
        etEmail.setFocusable(false);
        etEmail.setClickable(false);
        etRentDate.setFocusable(false);
        etRentDate.setClickable(true);
        etReturnDate.setFocusable(false);
        etReturnDate.setClickable(true);

        etRentDate.setOnClickListener(view -> showDatePickerDialog(etRentDate));
        etReturnDate.setOnClickListener(view -> showDatePickerDialog(etReturnDate));


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void insertRental(){
        String bikeType = spBikeType.getSelectedItem().toString().trim();
        String rentalDate = etRentDate.getText().toString().trim();
        String returnDate = etReturnDate.getText().toString().trim();
        String email = etEmail.getText().toString().trim();

        if (bikeType.equals("Select a bike type") || rentalDate.isEmpty() || returnDate.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
        } else {
            boolean result = dbHelper.insertRental(bikeType, rentalDate, returnDate, email);
            if (result) {
                Toast.makeText(this, "Rental Successful", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Rental Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void showDatePickerDialog(EditText editText) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Format the date as YYYY-MM-DD
                    String selectedDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
                    editText.setText(selectedDate);
                },
                year, month, day
        );

        datePickerDialog.show();
    }

}