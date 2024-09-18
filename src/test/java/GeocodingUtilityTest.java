import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class GeocodingUtilityTest {

    @Test
    public void testGetLocationByZip_Valid() throws IOException {
        // Assert the output is correct for a valid ZIP code
        String result = GeocodingUtility.getLocationByZip("10001"); // ZIP code for New York,NY
        assertTrue(result.contains("Latitude: 40.7484") && result.contains("Longitude: -73.9967"));
    }

    @Test
    public void testGetLocationByCityState_Valid() throws IOException {
        // Assert the output is correct for a valid city/state combination
        String result = GeocodingUtility.getLocationByCityState("Los Angeles", "CA");
        assertTrue(result.contains("Latitude: 34.0536909") && result.contains("Longitude: -118.242766"));
    }

    @Test
    public void testGetLocationByZip_Invalid() throws IOException {
        // Invalid ZIP code should return an error
        String result = GeocodingUtility.getLocationByZip("99999");
        assertTrue(result.contains("Error fetching data"));
    }

    @Test
    public void testGetLocationByCityState_Invalid() throws IOException {
        // Invalid city/state code should return an error
        String result = GeocodingUtility.getLocationByCityState("InvalidCity", "XX");
        assertTrue(result.contains("No results found"));
    }

    @Test
    public void testMultipleLocations() throws IOException {
        // Simulate calling the app with multiple valid locations
        String[] locations = {"Los Angeles,CA", "10001", "Madison,WI"};

        for (String location : locations) {
            if (GeocodingUtility.isZipCode(location)) {
                String result = GeocodingUtility.getLocationByZip(location);
                assertTrue(result.contains("Latitude") || result.contains("Error"));
            } else if (GeocodingUtility.isCityState(location)) {
                String[] parts = location.split(",");
                String city = parts[0].trim();
                String state = parts[1].trim();
                String result = GeocodingUtility.getLocationByCityState(city, state);
                assertTrue(result.contains("Latitude") || result.contains("Error"));
            }
        }
    }
    @Test
    public void testMultipleLocationsWithInvalidInput() throws IOException {
        // Simulate calling the app with multiple valid and invalid locations
        String[] locations = {"Los Angeles,CA", "00000", "Madison,WI"};

        for (String location : locations) {
            if (GeocodingUtility.isZipCode(location)) {
                String result = GeocodingUtility.getLocationByZip(location);
                assertTrue( result.contains("Error"));
            } else if (GeocodingUtility.isCityState(location)) {
                String[] parts = location.split(",");
                String city = parts[0].trim();
                String state = parts[1].trim();
                String result = GeocodingUtility.getLocationByCityState(city, state);
                assertTrue(result.contains("Latitude") || result.contains("Error"));
            }
        }
    }
}
