package com.example.travelapp_2.Model;

public class HotelModel {
    private String name;
    private String address;
    private String region;
    private double latitude;
    private double longitude;
    private String description;
    private String imageUrl;
    private double rating;

    public HotelModel() {
        // Firebase uchun bo'sh konstruktor
    }

    public HotelModel(String name, String address, String region, double latitude, double longitude,
                     String description, String imageUrl, double rating) {
        this.name = name;
        this.address = address;
        this.region = region;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        this.imageUrl = imageUrl;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getRegion() {
        return region;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public double getRating() {
        return rating;
    }
} 