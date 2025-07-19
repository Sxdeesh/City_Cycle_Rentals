package com.example.bikerentsadeesh;

public class Services {
    private int ServicesImage;
    private String Service;
    private String Description;

    public Services(int servicesImage, String service, String description) {
        ServicesImage = servicesImage;
        Service = service;
        Description = description;
    }
    public int getServicesImage() {
        return ServicesImage;
    }
    public String getService() {
        return Service;
    }
    public String getDescription() {
        return Description;
    }
}
