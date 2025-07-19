package com.example.bikerentsadeesh;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Edit_contact extends AppCompatActivity {

    private ImageView ivBack;
    private EditText etNewContact;
    private Button btnUpdateContact;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_contact);

        etNewContact = findViewById(R.id.etContact);
        btnUpdateContact = findViewById(R.id.btnUpdateContact);
        dbHelper = new DBHelper(this);
        ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(view -> onBackPressed());
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "Email");
        String contact = dbHelper.getPhoneByEmail(email);
        etNewContact.setText(contact);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        update();
    }
    public void update(){
        btnUpdateContact.setOnClickListener(view -> {
            String newContact = etNewContact.getText().toString().trim();

            if(newContact.isEmpty()){
                Toast.makeText(Edit_contact.this, "Please enter a contact number", Toast.LENGTH_SHORT).show();
                return;
            }
            SharedPreferences sharedPreferences =getSharedPreferences("UserPreferences", MODE_PRIVATE);
            String userEmail = sharedPreferences.getString("email", null);

             if (userEmail == null){
                 Toast.makeText(Edit_contact.this, "User email not found", Toast.LENGTH_SHORT).show();
                 return;
             }
             boolean updated = dbHelper.updatePhoneNumber(userEmail, newContact);

             if (updated){
                 SharedPreferences.Editor editor = sharedPreferences.edit();
                 editor.putString("contact", newContact);
                 editor.apply();

                 Toast.makeText(Edit_contact.this, "Contact number updated successfully", Toast.LENGTH_SHORT).show();
                 finish();
             }else {
                 Toast.makeText(Edit_contact.this, "Contact number update failed", Toast.LENGTH_SHORT).show();
             }
        });
    }
}