package com.example.bikerentsadeesh;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Users.db";
    private static final int DATABASE_VERSION = 2;
    public static final String TABLE_USERS ="users";
    public static final String TABLE_RENTALS = "rentals";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_IMAGE = "profile_image";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_RENTAL_ID = "rental_id";
    public static final String COLUMN_BIKE_TYPE = "bike_type";
    public static final String COLUMN_RENTAL_DATE = "rental_date";
    public static final String COLUMN_RETURN_DATE = "return_date";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableUsers = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_GENDER + " TEXT, " +
                COLUMN_PHONE + " TEXT, " +
                COLUMN_IMAGE + " BLOB, " +
                COLUMN_PASSWORD + " TEXT)";
        db.execSQL(createTableUsers);
        String createTableRentals = "CREATE TABLE " + TABLE_RENTALS + " (" +
                COLUMN_RENTAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_BIKE_TYPE + " TEXT, " +
                COLUMN_RENTAL_DATE + " TEXT, " +
                COLUMN_RETURN_DATE + " TEXT, " +
                COLUMN_EMAIL + " TEXT)";
        db.execSQL(createTableRentals);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RENTALS);
        onCreate(db);
    }
    public boolean insertRental(String bikeType, String rentalDate, String returnDate, String email){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BIKE_TYPE, bikeType);
        values.put(COLUMN_RENTAL_DATE, rentalDate);
        values.put(COLUMN_RETURN_DATE, returnDate);
        values.put(COLUMN_EMAIL, email);

        long result = db.insert(TABLE_RENTALS, null, values);
        return result != -1;
    }
    public boolean insertUser(String username, String email, String gender, String phone, String password, byte[] image){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_GENDER, gender);
        values.put(COLUMN_PHONE, phone);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_IMAGE, image);

        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }
    public boolean checkUser(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_EMAIL + " = ? AND " + COLUMN_PASSWORD + " = ?",
                new String[]{email, password});
        boolean exists = cursor.getCount()>0;
        cursor.close();
        return exists;
    }
    public boolean isEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_EMAIL + " = ?", new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }
    public String getUsernameByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT username FROM users WHERE email=?", new String[]{email});

        if (cursor.moveToFirst()) {
            String username = cursor.getString(0);
            cursor.close();
            return username;
        } else {
            cursor.close();
            return "User";
        }
    }
    public String getPhoneByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT phone FROM users WHERE email=?", new String[]{email});
        if (cursor.moveToFirst()) {
            String phone = cursor.getString(0);
            cursor.close();
            return phone;
        } else {
            cursor.close();
            return "User";
        }
    }
    public  String getGenderByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT gender FROM users WHERE email=?", new String[]{email});
        if (cursor.moveToFirst()) {
            String gender = cursor.getString(0);
            cursor.close();
            return gender;
        } else {
            cursor.close();
            return "User";
        }
    }
    public byte[] getUserImageByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_IMAGE + " FROM " + TABLE_USERS + " WHERE " + COLUMN_EMAIL + " = ?", new String[]{email});

        if (cursor.moveToFirst()) {
            byte[] image = cursor.getBlob(0);
            cursor.close();
            return image;
        } else {
            cursor.close();
            return null;
        }
    }
    public boolean updateUsername(String email, String newUsername) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, newUsername);

        int rowsAffected = db.update("users", values, "email = ?", new String[]{email});
        db.close();

        return rowsAffected > 0;
    }
    public boolean updatePhoneNumber(String email, String newContact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PHONE, newContact);

        int rowsaffected = db.update("users", values, "email = ?", new String[]{email});
        db.close();

        return  rowsaffected>0;
    }
    public List<RentedBike> getRentalDetailsByEmail(String email) {
        List<RentedBike> rentedBikes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT bike_type, rental_date, return_date FROM rentals WHERE email=?", new String[]{email});

        if (cursor.moveToFirst()) {
            do {
                String bikeType = cursor.getString(cursor.getColumnIndexOrThrow("bike_type"));
                String rentalDate = cursor.getString(cursor.getColumnIndexOrThrow("rental_date"));
                String returnDate = cursor.getString(cursor.getColumnIndexOrThrow("return_date"));

                RentedBike bike = new RentedBike(bikeType, rentalDate, returnDate);
                rentedBikes.add(bike);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return rentedBikes;
    }
    // Add this method to your existing DBHelper class

    public boolean updateUserImage(String email, byte[] newImage) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_IMAGE, newImage);

        int rowsAffected = db.update(TABLE_USERS, values, COLUMN_EMAIL + " = ?", new String[]{email});
        db.close();

        return rowsAffected > 0;
    }


}