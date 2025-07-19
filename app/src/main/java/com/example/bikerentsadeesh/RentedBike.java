package com.example.bikerentsadeesh;

public class RentedBike {
    private String bikeType;
    private String rentalDate;
    private String returnDate;

    public RentedBike(String bikeType, String rentalDate, String returnDate) {
        this.bikeType = bikeType;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
    }

    public String getBikeType() { return bikeType; }
    public String getRentalDate() { return rentalDate; }
    public String getReturnDate() { return returnDate; }
}

