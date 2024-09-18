
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;

import io.restassured.response.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class GeocodingUtility {


        private static final String API_KEY = "f897a99d971b5eef57be6fafa0d83239"; //hardcoded here, should be stored as env variable instead
        private static final String BASE_URL_CITY_STATE = "http://api.openweathermap.org/geo/1.0/direct";
        private static final String BASE_URL_ZIP = "http://api.openweathermap.org/geo/1.0/zip";

        public static void main(String[] args) throws IOException {
            Scanner scan = new Scanner(System.in);
            List<String> locations = new ArrayList<>(Arrays.asList(args));
            if (locations.isEmpty()) {
                System.out.println("Please provide locations as input: ");
                String s = scan.nextLine();
                locations.add(s);
            }


            for (String location : locations) {
                if (isZipCode(location)) {
                    getLocationByZip(location);
                } else if (isCityState(location)) {
                    String[] parts = location.split(",");
                    if (parts.length == 2) {
                        String city = parts[0].trim();
                        String state = parts[1].trim();
                        getLocationByCityState(city, state);
                    } else {
                        System.out.println("Invalid city/state format. Use 'city,state' format for input: " + location);
                    }
                } else {
                    System.out.println("Invalid input format: " + location);
                }
            }
        }

    public static boolean isZipCode(String input) {

            return input.matches("\\d+");
    }

    public static boolean isCityState(String input) {

        return input.contains(",") && input.split(",")[0].trim().length() > 0;
    }



    public static String getLocationByCityState(String city, String state) throws IOException {

        Response response = RestAssured.given()
                .queryParam("q", city + "," + state + ",US")
                .queryParam("limit", 1)
                .queryParam("appid", API_KEY)
                .when()
                .get(BASE_URL_CITY_STATE);

        if (response.getStatusCode() == 200) {
            return processApiResponse(response.asString(), city + ", " + state);
        } else {
            return "Error fetching data for " + city + ", " + state + ": " + response.getStatusCode();
        }
    }

    public static String getLocationByZip(String zipCode) throws IOException {

        Response response = RestAssured.given()
                .queryParam("zip", zipCode + ",US")
                .queryParam("appid", API_KEY)
                .when()
                .get(BASE_URL_ZIP);

        if (response.getStatusCode() == 200) {

            return processApiResponse(response.asString(), zipCode);
        } else {
            return "Error fetching data for zip code " + zipCode + ": " + response.getStatusCode();
        }
    }

    private static String processApiResponse(String responseBody, String query) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(responseBody);
        if (rootNode.isArray() && rootNode.size() > 0) {
            JsonNode location = rootNode.get(0);
            double lat = location.get("lat").asDouble();
            double lon = location.get("lon").asDouble();
            String name = location.get("name").asText();
            System.out.println("Location: " + query + " -> Latitude: " + lat + ", Longitude: " + lon + ", Place Name: " + name);
            return "Location: " + query + " -> Latitude: " + lat + ", Longitude: " + lon + ", Place Name: " + name;
        } else if (rootNode.isObject()) {
            double lat = rootNode.get("lat").asDouble();
            double lon = rootNode.get("lon").asDouble();
            String name = rootNode.get("name").asText();
            System.out.println("Location: " + query + " -> Latitude: " + lat + ", Longitude: " + lon + ", Place Name: " + name);
            return "Location: " + query + " -> Latitude: " + lat + ", Longitude: " + lon + ", Place Name: " + name;
        } else {
            return "No results found for: " + query;
        }
    }

}