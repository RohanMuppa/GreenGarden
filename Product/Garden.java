package com.example.gg2;

import java.io.BufferedReader;
import java.util.*;
import java.util.HashMap;
import javafx.collections.ObservableList;
import java.io.FileReader;
import java.io.IOException;

public class Garden {

    private int size;
    private int zipCode;
    private int sunExposure;
    private int budget;
    private ObservableList<String> existingPlantTypes;
    private int maintenanceLevel;

    private double totalCost = 0.0;

    public ArrayList<Plant> newPlants = new ArrayList<>();



    public Garden(int size, int zipCode, int sunExposure, int budget, ObservableList<String> existingPlantTypes, int maintenanceLevel) {
        this.size = size;
        this.zipCode = zipCode;
        this.sunExposure = sunExposure;
        this.budget = budget;
        this.maintenanceLevel = maintenanceLevel;
        this.existingPlantTypes = existingPlantTypes;
    }

    public void selectPlants() {
        double totalCost = 0.0;
        List<Plant> plantsToFilter = readPlantsFromCsvFile("/Users/Rohan/Desktop/P5, Muppa, Rohan/Product/Plants.csv"); // read plants from CSV file. Replace this path with your own path

        for (int i = 0; i < plantsToFilter.size(); i++) {
            Plant plant = plantsToFilter.get(i);
            // Filter by nearby zip codes
            if (Math.abs(plant.getZipcode() - zipCode) > 500) {
                continue;
            }

            // Filter by compatibility with existing plants
            boolean isCompatible = true;
            for (int j = 0; j < existingPlantTypes.size(); i++) {
                if (!isCompatibleWith(plant, existingPlantTypes, plantsToFilter)) {
                    isCompatible = false;
                    break;
                }
            }

            // Filter by sun exposure
            int plantSunExposure = plant.getSunExposure();
            if (sunExposure >= plantSunExposure){
                continue;
            }

            // Filter by maintenance needed
            if (maintenanceLevel <= plant.getWaterDemand()){
                continue;
            }

            //Calculate totalCost and stop when budget is exceeded
            totalCost += plant.getCost();
            newPlants.add(plant);
            if (totalCost > budget) {
                newPlants.remove(plant);
                totalCost = totalCost - plant.getCost();
            }
        }
    }

    public boolean isCompatibleWith(Plant plant, ObservableList<String> existingPlants, List<Plant> filteredPlants) {
        //Shows the compatibility between the existing plant types (more general) to the specific plant types of the new plants
        HashMap<String, List<String>> compatibility = new HashMap<>();
        compatibility.put("Annuals", new ArrayList<>(List.of("Petunia", "Marigold", "Zinnia")));
        compatibility.put("Perennials", new ArrayList<>(List.of("Hosta", "Lavender", "Daylily")));
        compatibility.put("Bulbs", new ArrayList<>(List.of("Daffodil", "Tulip", "Crocus")));
        compatibility.put("Shrubs", new ArrayList<>(List.of("Azalea", "Lilac", "Rhododendron")));
        compatibility.put("Trees", new ArrayList<>(List.of("Maple", "Oak", "Birch")));
        compatibility.put("Climbers", new ArrayList<>(List.of("Clematis", "Ivy", "Honeysuckle")));
        compatibility.put("Herbs", new ArrayList<>(List.of("Basil", "Rosemary", "Thyme")));
        compatibility.put("Vegetables", new ArrayList<>(List.of("Tomato", "Cucumber", "Pepper")));
        compatibility.put("Fruits", new ArrayList<>(List.of("Strawberry", "Blueberry", "Raspberry")));
        compatibility.put("Grasses", new ArrayList<>(List.of("Bamboo", "Ornamental grass", "Pampas grass")));
        compatibility.put("Succulents", new ArrayList<>(List.of("Aloe vera", "Jade plant", "Cactus")));
        compatibility.put("Cacti", new ArrayList<>(List.of("Barrel cactus", "Christmas cactus", "Prickly pear")));
        compatibility.put("Ferns", new ArrayList<>(List.of("Boston fern", "Maidenhair fern", "Bird's nest fern")));
        compatibility.put("Palms", new ArrayList<>(List.of("Areca palm", "Date palm", "Coconut palm")));
        compatibility.put("Ornamental Grasses", new ArrayList<>(List.of("Japanese blood grass", "Pampas grass", "Fountain grass")));

        String newPlantType = plant.getType();

        // Check compatibility with existing plants
        for (int i = 0; i < existingPlants.size(); i++) {
            String existingPlantType = existingPlants.get(i);
            if (compatibility.containsKey(existingPlantType)) {
                List<String> compatibleTypes = compatibility.get(existingPlantType);
                if (!compatibleTypes.contains(newPlantType)) {
                    return false;
                }
            }
        }

        // Check compatibility with other plants in filteredPlants list
        for (int i = 0; i < filteredPlants.size(); i++) {
            String filteredPlantType = filteredPlants.get(i).getType();
            if (compatibility.containsKey(filteredPlantType)) {
                List<String> compatibleTypes = compatibility.get(filteredPlantType);
                if (!compatibleTypes.contains(newPlantType)) {
                    return false;
                }
            }
        }

        return true;
    }

    public static List<Plant> readPlantsFromCsvFile(String path) {
        List<Plant> plants = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String name = data[1];
                int sunNeeded = Integer.parseInt(data[2]);
                int waterNeeded = Integer.parseInt(data[3]);
                double cost = Double.parseDouble(data[4]);
                int zipcode = Integer.parseInt(data[5]);
                String type = data[6];
                Plant plant = new Plant(name, sunNeeded, waterNeeded, cost, zipcode, type);
                plants.add(plant);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return plants;
    }

    public String generateReport() {
        selectPlants();
        WaterCalculator calculator = new WaterCalculator(sunExposure, size);

        double costOfPlants = getTotalCostOfPlants();
        double co2Sequestered = getCO2Sequestered();
        double waterSaved = calculator.calculateWaterSavingsGallons();
        double waterSavingsPercent = calculator.calculateWaterSavingsPercent();
        StringBuilder reportBuilder = new StringBuilder();
        reportBuilder.append("Environmental Report:\n\n");
        reportBuilder.append("- Total Cost of Plants: $" + String.format("%.2f", costOfPlants) + "\n");
        reportBuilder.append("- CO2 Sequestered: " + String.format("%.2f", co2Sequestered) + " kg\n");
        reportBuilder.append("- Water Saved: " + String.format("%.2f gallons\n", waterSaved));
        reportBuilder.append(String.format("- Water Savings: %.2f%%\n", waterSavingsPercent));
        reportBuilder.append(getGardenRecommendations());
        return reportBuilder.toString();
    }

    public double getTotalCostOfPlants() {
        double totalCost = 0.0;
        for (int i = 0; i < newPlants.size(); i++) {
            totalCost += newPlants.get(i).getCost();
        }
        return totalCost;
    }
    public double getCO2Sequestered() {
        double co2SequesteredPerPlant = 0.1; // Estimate of CO2 sequestered per plant (in kg/year)
        int numPlants = newPlants.size();
        double co2Sequestered = co2SequesteredPerPlant * numPlants * this.size;
        return co2Sequestered;
    }
    public String getGardenRecommendations() {
        StringBuilder sb = new StringBuilder();

        Map<String, Integer> plantCounts = new HashMap<>();

        // Count existing plants in garden
        for (int i = 0; i < existingPlantTypes.size(); i++) {
                String plantType = existingPlantTypes.get(i);
                plantCounts.put(plantType, plantCounts.getOrDefault(plantType, 0) + 1);
            }

        // Count new plants to be added
        for (int i = 0; i < newPlants.size(); i++) {
            Plant plant = newPlants.get(i);
            if (plant != null) {
                String plantType = plant.getType();
                plantCounts.put(plantType, plantCounts.getOrDefault(plantType, 0) + 1);
            }
        }

        sb.append("\n\nBased on the types of plants in your garden, we recommend:\n");
        if (plantCounts.getOrDefault("Ferns", 0) > 12) {
            // Recommend using rainwater to water ferns as they prefer soft water
            sb.append("- Collecting rainwater to water your ferns as they prefer soft water.\n");
        }
        if (plantCounts.getOrDefault("Palms", 0) > 3) {
            // Recommend using a slow-release fertilizer for palm trees to avoid overfertilization
            sb.append("- Using a slow-release fertilizer for your palm trees to avoid overfertilization.\n");
        }
        if (plantCounts.getOrDefault("Grasses", 0) > 10) {
            // Recommend using organic fertilizer for ornamental grasses to avoid chemical buildup
            sb.append("- Using organic fertilizer for your ornamental grasses to avoid chemical buildup.\n");
        }
        if (plantCounts.getOrDefault("Climbers", 0) > 5) {
            // Recommend using trellises to support climbers and prevent damage to walls and fences
            sb.append("- Using trellises to support your climbers and prevent damage to walls and fences.\n");
        }
        if (plantCounts.getOrDefault("Fruits", 0) > 4) {
            // Recommend using natural pest control methods for fruit trees to avoid harmful chemicals
            sb.append("- Using natural pest control methods for your fruit trees to avoid harmful chemicals.\n");
        }
        if (plantCounts.getOrDefault("Annuals", 0) > 20) {
            // Recommend using organic fertilizer for annuals to avoid chemical buildup
            sb.append("- Using organic fertilizer for your annuals to avoid chemical buildup.\n");
        }
        if (plantCounts.getOrDefault("Perennials", 0) > 15) {
            // Recommend using companion planting to improve soil health and reduce pest problems for perennials
            sb.append("- Using companion planting to improve soil health and reduce pest problems for your perennials.\n");
        }
        if (plantCounts.getOrDefault("Bulbs", 0) > 25) {
            // Recommend using bone meal as a natural fertilizer for bulbs
            sb.append("- Using bone meal as a natural fertilizer for your bulbs.\n");
        }
        if (plantCounts.getOrDefault("Ornamental Grasses", 0) > 8) {
            // Recommend trimming ornamental grasses to a height of 2-3 feet in late winter or early spring
            sb.append("- Trimming your ornamental grasses to a height of 2-3 feet in late winter or early spring.\n");
        }
        // Recommend sustainable practices based on plant types
        if (plantCounts.containsKey("Vegetables") || plantCounts.getOrDefault("Herbs", 0) > 18) {
            // Recommend starting a compost bin to reduce waste and improve soil health
            sb.append("- Starting a compost bin to generate organic fertilizer for your vegetable and herb plants.\n");
        }
        else if (plantCounts.containsKey("Trees") || plantCounts.containsKey("Shrubs")) {
            // Recommend using mulch to conserve water and reduce weed growth around trees and shrubs
            sb.append("- Using mulch around your trees and shrubs to conserve water and reduce weed growth.\n");
        }
        else if (plantCounts.containsKey("Succulents") || plantCounts.containsKey("Cacti")) {
            // Recommend using a drip irrigation system to conserve water and prevent overwatering of succulents and cacti
            sb.append("- Using a drip irrigation system to water your succulents or cacti to prevent overwatering.\n");
        }
        return sb.toString();
    }
    public String createMaintenanceSchedule(int maintenanceLevel) {
        int daysPerWeek = 7;
        int weeksPerMonth = 4;
        int totalDaysInMonth = daysPerWeek * weeksPerMonth;

        WaterCalculator calculator = new WaterCalculator(sunExposure, size);
        int numPlants = newPlants.size();
        int waterPerWeek = Math.toIntExact(Math.round(calculator.calculateWaterUsage() * daysPerWeek));
        int waterPerMonth = waterPerWeek * weeksPerMonth;

        int waterPerPlantPerWeek = waterPerWeek / numPlants;
        int waterPerPlantPerMonth = waterPerPlantPerWeek * weeksPerMonth;

        int maintenanceDaysPerMonth = (int) Math.ceil(numPlants * (double) maintenanceLevel / 100);
        int wateringsPerMonth = (int) Math.ceil(waterPerMonth / (double) waterPerPlantPerMonth);

        StringBuilder scheduleBuilder = new StringBuilder();
        scheduleBuilder.append("Maintenance Schedule:\n\n");
        scheduleBuilder.append("- Water plants " + wateringsPerMonth + " times per month\n");
        scheduleBuilder.append("- Spend " + maintenanceDaysPerMonth + " day(s) per month on maintenance (fertilizing, pruning, etc) \n");

        return scheduleBuilder.toString();
    }


    public String listPlantNames() {
        StringBuilder sb = new StringBuilder();
        sb.append("Plants in your garden:\n");
        Map<String, Integer> plantCounts = new HashMap<>();
        for (int i = 0; i < newPlants.size(); i++) {
            Plant plant = newPlants.get(i);
            String plantName = plant.getName();
            plantCounts.put(plantName, plantCounts.getOrDefault(plantName, 0) + 1);
        }
        for (String plantName : plantCounts.keySet()) {
            int count = plantCounts.get(plantName);
            sb.append("- ").append(plantName).append(" (x").append(count).append(")\n");
        }
        return sb.toString();
    }
    public List<Plant> getNewPlants(){
        return newPlants;
    }
}




