# GreenGarden

> **IB Computer Science Internal Assessment (IA) Project**
> *IB Computer Science SL | March 2023*

A JavaFX desktop application for sustainable garden planning that helps users design eco-friendly gardens with personalized plant recommendations, environmental impact reports, and maintenance schedules.

---

## Table of Contents

- [About the Project](#about-the-project)
- [The Problem](#the-problem)
- [The Solution](#the-solution)
- [Features](#features)
- [Screenshots](#screenshots)
- [Technical Architecture](#technical-architecture)
- [Installation](#installation)
- [Usage](#usage)
- [Success Criteria](#success-criteria)
- [Documentation](#documentation)
- [Future Development](#future-development)
- [License](#license)

---

## About the Project

**GreenGarden** was developed as an IB Computer Science Internal Assessment (IA) project to address real-world sustainability challenges in home gardening.

| Role | Name |
|------|------|
| **Developer** | Rohan Muppa |
| **Client** | Latha Battina |
| **Advisor** | Peter Donaldson |
| **Instructor** | Ms. Nishiwaki |

---

## The Problem

The client, Latha Battina, is a recreational gardener who faces challenges managing her backyard garden:

- **Limited knowledge** about sustainable gardening practices
- **Difficulty identifying plants** suitable for local climate and conditions
- **No existing tool** to manage the garden efficiently and sustainably
- **Desire for organic produce** with minimal environmental impact
- **Need for guidance** on garden planning and maintenance

---

## The Solution

GreenGarden is a sustainable garden planning program that enables users to **plan, track, and improve** their sustainable gardens by:

1. Accepting user inputs about local climate and garden conditions
2. Recommending plants native to the area to promote biodiversity
3. Creating lists of tools and supplies needed for maintenance
4. Reporting potential environmental impact of the garden
5. Generating personalized maintenance schedules

---

## Features

### Core Functionality

| Feature | Description |
|---------|-------------|
| **Plant Recommendations** | Filters plants from a database based on zip code, sun exposure, maintenance level, and budget |
| **Environmental Report** | Calculates CO2 sequestered and water saved compared to traditional gardens |
| **Budget Management** | Ensures total plant cost stays within user-specified budget |
| **Compatibility Check** | Verifies new plants are compatible with existing garden plants |
| **Maintenance Schedule** | Generates watering and care schedules based on maintenance preference |
| **Garden Tips** | Provides specific recommendations based on plant types (fertilizers, pest control, etc.) |

### Input Parameters

- **Garden Size** (square meters)
- **Zip Code** (for location-based filtering)
- **Sun Exposure** (Less than 4 hours, 4-6 hours, 6-8 hours, More than 8 hours)
- **Maintenance Level** (Low, Moderate, High)
- **Budget** (USD)
- **Existing Plant Types** (15 categories including Annuals, Perennials, Herbs, Vegetables, etc.)

### Supported Plant Categories

```
Annuals | Perennials | Bulbs | Shrubs | Trees | Climbers
Herbs | Vegetables | Fruits | Grasses | Succulents | Cacti
Ferns | Palms | Ornamental Grasses
```

---

## Screenshots

### Main Application Interface

The application uses a clean GridPane layout with intuitive input fields:

![Application UI](Documentation/Crit_C_Development.pdf)

*See `Documentation/Crit_C_Development.pdf` (Page 2) for UI screenshots*

**UI Components:**
- Text fields for garden size, zip code, and budget
- Dropdown menus for sun exposure and maintenance level
- Checkbox list for selecting existing plant types
- Submit button to generate recommendations

### Output Reports

The application generates three sequential reports:

1. **Environmental Report**
   - Total cost of recommended plants
   - CO2 sequestered (kg)
   - Water saved (gallons)
   - Water savings percentage
   - Personalized garden recommendations

2. **Maintenance Schedule**
   - Watering frequency per month
   - Maintenance days required

3. **Plant List**
   - Complete list of recommended plants with quantities

---

## Technical Architecture

### Technology Stack

| Component | Technology |
|-----------|------------|
| Language | Java |
| UI Framework | JavaFX |
| IDE | IntelliJ IDEA |
| Data Storage | CSV File |

### Project Structure

```
GreenGarden/
├── Product/
│   ├── Main.java           # JavaFX application entry point & UI
│   ├── Garden.java         # Core garden logic and algorithms
│   ├── Plant.java          # Plant data model
│   ├── WaterCalculator.java # Water usage calculations
│   └── Plants.csv          # Plant database (required)
└── Documentation/
    ├── Crit_A_Planning.pdf
    ├── Crit_B_Design.pdf
    ├── Crit_B_Record_Of_Tasks.pdf
    ├── Crit_C_Development.pdf
    ├── Crit_E_Evaluation.pdf
    └── Appendix.pdf
```

### Class Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                            Main                                  │
│  (JavaFX Application)                                           │
├─────────────────────────────────────────────────────────────────┤
│  + main(args: String[]): void                                   │
│  + start(primaryStage: Stage): void                             │
│  - showAlert(message: String, type: AlertType): void            │
└─────────────────────────────────────────────────────────────────┘
                              │
                              │ creates
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                           Garden                                 │
├─────────────────────────────────────────────────────────────────┤
│  - size: int                                                    │
│  - zipCode: int                                                 │
│  - sunExposure: int                                             │
│  - budget: int                                                  │
│  - maintenanceLevel: int                                        │
│  - existingPlantTypes: ObservableList<String>                   │
│  + newPlants: ArrayList<Plant>                                  │
├─────────────────────────────────────────────────────────────────┤
│  + selectPlants(): void                                         │
│  + isCompatibleWith(plant, existing, filtered): boolean         │
│  + readPlantsFromCsvFile(path: String): List<Plant>             │
│  + generateReport(): String                                     │
│  + getTotalCostOfPlants(): double                               │
│  + getCO2Sequestered(): double                                  │
│  + getGardenRecommendations(): String                           │
│  + createMaintenanceSchedule(level: int): String                │
│  + listPlantNames(): String                                     │
│  + getNewPlants(): List<Plant>                                  │
└─────────────────────────────────────────────────────────────────┘
                              │
              ┌───────────────┴───────────────┐
              │                               │
              ▼                               ▼
┌──────────────────────────┐    ┌──────────────────────────┐
│          Plant           │    │     WaterCalculator      │
├──────────────────────────┤    ├──────────────────────────┤
│  - name: String          │    │  - sunExposure: int      │
│  - waterDemand: int      │    │  - gardenSize: int       │
│  - sunExposure: int      │    │  - waterUsage: double    │
│  - zipcode: int          │    │  - regularGardenWater:   │
│  - cost: double          │    │      Usage: double       │
│  - type: String          │    ├──────────────────────────┤
├──────────────────────────┤    │  + calculateWaterUsage() │
│  + getWaterDemand(): int │    │  + calculateWaterSavings │
│  + getZipcode(): int     │    │      Percent(): double   │
│  + getSunExposure(): int │    │  + calculateWaterSavings │
│  + getType(): String     │    │      Gallons(): double   │
│  + getCost(): double     │    └──────────────────────────┘
│  + getName(): String     │
└──────────────────────────┘
```

### Key Algorithms

#### 1. Plant Selection Algorithm (`selectPlants()`)

```
START
├── Load plants from CSV database
├── FOR each plant in database:
│   ├── Filter by zip code (within 500 range)
│   ├── Check compatibility with existing plants
│   ├── Filter by sun exposure requirements
│   ├── Filter by maintenance/water demand
│   ├── Add to recommendations if passes all filters
│   └── Remove if exceeds budget
└── END
```

#### 2. Plant Compatibility Check (`isCompatibleWith()`)

Uses a **HashMap** to map general plant categories to specific compatible plant types:

```java
HashMap<String, List<String>> compatibility = new HashMap<>();
compatibility.put("Annuals", List.of("Petunia", "Marigold", "Zinnia"));
compatibility.put("Perennials", List.of("Hosta", "Lavender", "Daylily"));
// ... etc.
```

### Data Structures Used

| Structure | Purpose |
|-----------|---------|
| **HashMap** | Plant compatibility mapping, plant type counting |
| **ArrayList** | Storing recommended plants (dynamic sizing) |
| **ObservableList** | JavaFX UI binding for existing plant types |
| **StringBuilder** | Efficient report generation |

### Techniques Demonstrated

- **JavaFX GridPanes and Labels** - Structured UI layout
- **Try-Catch Blocks** - Input validation and error handling
- **File I/O** - CSV parsing with BufferedReader
- **Encapsulation** - Private fields with public getters
- **Linear Search** - Plant filtering algorithm

---

## Installation

### Prerequisites

- **Java JDK 11+** (with JavaFX support)
- **JavaFX SDK** (if not bundled with JDK)
- **IntelliJ IDEA** (recommended) or any Java IDE

### Step 1: Clone the Repository

```bash
git clone https://github.com/yourusername/GreenGarden.git
cd GreenGarden
```

### Step 2: Set Up JavaFX

#### Option A: Using JDK with bundled JavaFX (e.g., Liberica JDK Full, Azul Zulu JDK FX)

1. Download and install a JDK with JavaFX included
2. Set `JAVA_HOME` to the JDK installation directory

#### Option B: Separate JavaFX SDK

1. Download JavaFX SDK from [openjfx.io](https://openjfx.io/)
2. Extract to a known location (e.g., `/path/to/javafx-sdk`)
3. Add VM options when running:

```bash
--module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml
```

### Step 3: Configure the Plant Database

1. Create a `Plants.csv` file with the following format:

```csv
ID,Name,SunNeeded,WaterNeeded,Cost,Zipcode,Type
1,Petunia,2,1,5.99,12345,Annuals
2,Lavender,3,1,8.99,12345,Perennials
...
```

2. Update the file path in `Garden.java`:

```java
// Line 36 in Garden.java - Update this path:
List<Plant> plantsToFilter = readPlantsFromCsvFile("/your/path/to/Plants.csv");
```

### Step 4: Compile and Run

#### Using Command Line:

```bash
cd Product
javac --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls *.java
java --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls Main
```

#### Using IntelliJ IDEA:

1. Open the project in IntelliJ
2. Configure Project SDK (File > Project Structure > Project)
3. Add JavaFX library (File > Project Structure > Libraries)
4. Set VM options in Run Configuration:
   ```
   --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls
   ```
5. Run `Main.java`

---

## Usage

1. **Launch the application** - Run `Main.java`

2. **Enter garden details:**
   - Garden size in square meters
   - Your zip code
   - Select sun exposure level
   - Choose maintenance preference
   - Enter your budget in USD
   - Check existing plant types in your garden

3. **Click Submit** to generate recommendations

4. **View results** in sequential popup dialogs:
   - Environmental Report (CO2, water savings, tips)
   - Maintenance Schedule
   - Recommended Plant List

---

## Success Criteria

All success criteria from the IB IA were met and validated by the client:

| Criteria | Status | Evidence |
|----------|--------|----------|
| Handle multiple inputs (maintenance, sun exposure, budget, zip code) | ✅ Met | Client confirmed ability to input all parameters |
| Generate plants for each garden | ✅ Met | Program generates customized shopping list |
| Recommend at least 1 native plant | ✅ Met | Plants filtered within zip code radius |
| Generate environmental report (CO2, water saved) | ✅ Met | Report displays all metrics |
| Recommend specific tips based on plant needs | ✅ Met | Dynamic recommendations based on plant types |
| Predict 10%+ water reduction | ✅ Met | Client reported ~15% water savings prediction |
| Provide watering schedule | ✅ Met | Monthly maintenance schedule generated |
| Total cost under budget | ✅ Met | Budget constraint enforced in algorithm |

---

## Documentation

Complete IB IA documentation is available in the `Documentation/` folder:

| Document | Description |
|----------|-------------|
| `Crit_A_Planning.pdf` | Problem definition, client needs, proposed solution, success criteria |
| `Crit_B_Design.pdf` | Flowcharts, UML diagrams, UI flow, test plans |
| `Crit_B_Record_Of_Tasks.pdf` | Development timeline with 57 documented tasks |
| `Crit_C_Development.pdf` | Technical implementation, code explanations, algorithms |
| `Crit_E_Evaluation.pdf` | Success criteria evaluation, client feedback, future developments |
| `Appendix.pdf` | Supporting materials and references |

---

## Future Development

Based on client feedback, the following enhancements are planned:

1. **Detailed 7-day maintenance schedule** - Provide specific daily tasks instead of monthly overview

2. **Short-term environmental metrics** - Add monthly/yearly data in addition to long-term (20+ years) projections

3. **Enhanced plant database** - Expand CSV with more plants and regional data

4. **Data persistence** - Save garden configurations for future sessions

5. **API integration** - Connect to plant databases for real-time information

---

## Technical Notes

### CSV File Format

The plant database CSV requires the following columns:

| Column | Type | Description |
|--------|------|-------------|
| ID | int | Unique identifier |
| Name | String | Plant name |
| SunNeeded | int | Sun exposure requirement (1-4) |
| WaterNeeded | int | Water demand level (1-3) |
| Cost | double | Price in USD |
| Zipcode | int | Native region zip code |
| Type | String | Plant category |

### Water Calculation Formula

```
Water Usage = 25 × (sunExposureFactor + gardenSize × gardenSizeFactor × 2.31)

Where:
- sunExposureFactor: 0.6 to 1.4 based on sun hours
- gardenSizeFactor: 1.0 to 1.5 based on garden size
- Regular garden baseline: 323 L/m² × 0.264172 (liters to gallons)
```

---

## Acknowledgments

- **Client (Latha Battina)** - For providing requirements and feedback
- **Advisor (Peter Donaldson)** - For technical guidance
- **Ms. Nishiwaki** - IB Computer Science instructor

---

## License

This project was created for educational purposes as part of the IB Diploma Programme Computer Science Internal Assessment.

---

<p align="center">
  <i>Developed with sustainability in mind</i>
</p>
