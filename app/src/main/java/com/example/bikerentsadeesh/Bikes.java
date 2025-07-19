package com.example.bikerentsadeesh;

import android.widget.Button;

public class Bikes {

    private int BikesImage;
    private String BikeName, BikePrice;

    public Bikes(int bikeImage, String bikeName, String bikePrice) {
        BikesImage = bikeImage;
        BikeName = bikeName;
        BikePrice = bikePrice;
    }

    public int getBikesImage(){
        return BikesImage;
    }
    public String getBikeName(){
        return BikeName;
    }

    public String getBikePrice(){
        return BikePrice;
    }

}
