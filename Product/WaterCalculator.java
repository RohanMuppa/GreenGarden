package com.example.gg2;

public class WaterCalculator {
    private int sunExposure;
    private int gardenSize;
    private double waterUsage;
    double regularGardenWaterUsage;

    public WaterCalculator(int sunExposure, int gardenSize) {
        this.sunExposure = sunExposure;
        this.gardenSize = gardenSize;
        this.waterUsage = calculateWaterUsage();
        this.regularGardenWaterUsage = 323.0 * gardenSize * 0.264172; // convert liters to gallons
    }

    public double calculateWaterUsage() {
        // Calculate water usage based on sun exposure and garden size
        double sunExposureFactor = 1.0;
        if (sunExposure > 8) {
            sunExposureFactor = 1.4;
        } else if (sunExposure > 6) {
            sunExposureFactor = 1.2;
        } else if (sunExposure > 4) {
            sunExposureFactor = 1.0;
        } else if (sunExposure > 2) {
            sunExposureFactor = 0.8;
        } else {
            sunExposureFactor = 0.6;
        }

        double gardenSizeFactor = 1.0;
        if (gardenSize > 1000) {
            gardenSizeFactor = 1.5;
        } else if (gardenSize > 500) {
            gardenSizeFactor = 1.2;
        } else if (gardenSize > 250) {
            gardenSizeFactor = 1.1;
        } else if (gardenSize > 100) {
            gardenSizeFactor = 1.05;
        }        waterUsage = (sunExposureFactor + gardenSize * gardenSizeFactor * 2.31);
        return Math.abs(25 * waterUsage);

    }

    public double calculateWaterSavingsPercent() {
        // Calculate water savings percentage based on water usage
        double savingsPercent = (1.0 - (waterUsage / regularGardenWaterUsage)) * 100;
        return savingsPercent;
    }

    public double calculateWaterSavingsGallons() {
        return Math.abs(regularGardenWaterUsage - waterUsage);
    }

    public double getRegularGardenWaterUsage() {
        return regularGardenWaterUsage;
    }
}

