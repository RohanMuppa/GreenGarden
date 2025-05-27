package com.example.gg2;

public class Plant {
    private String name;
    private int waterDemand;
    private int sunExposure;

    private int zipcode;

    private double cost;

    private String type;

    public Plant(String name, int sunExposure, int waterDemand, double cost, int zipcode, String type) {
        this.name = name;
        this.waterDemand = waterDemand;
        this.sunExposure = sunExposure;
        this.zipcode = zipcode;
        this.cost = cost;
        this.type = type;
    }

    public int getWaterDemand() {
        return waterDemand;
    }

    public int getZipcode() {
        return zipcode;
    }

    public int getSunExposure() {
        return sunExposure;
    }

    public String getType() {
        return type;
    }

    public double getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }
}
