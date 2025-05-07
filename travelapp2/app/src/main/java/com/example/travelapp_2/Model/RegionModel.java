package com.example.travelapp_2.Model;

import java.io.Serializable;

public class RegionModel implements Serializable {
    private String name;
    private String coordinates;
    private String description;

    public RegionModel(String name, String coordinates, String description) {
        this.name = name;
        this.coordinates = coordinates;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public String getDescription() {
        return description;
    }
} 