# Geocoding Utility

## Overview

The Geocoding Utility is a Java application that allows you to fetch geographic coordinates (latitude and longitude,name) for given locations using the OpenWeatherMap API. It supports querying by city/state and zip code.

## Prerequisites

- **Java 11** or higher
- **Maven** 3.6 or higher

## Running the Application

1. **Build the Project**

   To build the project and resolve dependencies, run:

   ```bash
   mvn clean install
   
2. **Run the app**
  
- For City and State input, use the command :
   ```bash
   mvn exec:java -Dexec.mainClass="com.example.GeocodingUtility" -Dexec.args="'New York,NY'"

- For Zip Code input, use the command :
     ```bash
     mvn exec:java -Dexec.mainClass="com.example.GeocodingUtility" -Dexec.args="'10001'"

- For List of Locations input, use the command: 
   ```bash
    mvn exec:java -Dexec.mainClass="com.example.GeocodingUtility" -Dexec.args="'New York,NY' '08520'"

 NOTE: Ensure that the input format for locations is correct. For cities and states, use the format "City,State". For zip codes, use only the numeric zip code.

3. **Run the tests**
   ```bash
   mvn test
After running the tests, you can find the test reports in the target/surefire-reports directory.