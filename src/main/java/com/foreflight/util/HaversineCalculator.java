package com.foreflight.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Getter
@Component
public class HaversineCalculator {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class HaversineComponents {
        private int distance;
        private String bearing;
    }

    /**
     * Calculates the distance and bearing between two points on Earth using the Haversine formula.
     *
     * @param lat1 Latitude of first point
     * @param lon1 Longitude of first point
     * @param lat2 Latitude of second point
     * @param lon2 Longitude of second point
     * @return Distance and bearing between two points
     */
    public static HaversineComponents haversineDistanceAndBearing(double lat1, double lon1, double lat2, double lon2) {
        double R = 3440.065;  // Radius of Earth in nautical miles

        // Convert latitude and longitude from degrees to radians
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        // Haversine formula for distance
        double dLat = lat2Rad - lat1Rad;
        double dLon = lon2Rad - lon1Rad;
        double a = Math.pow(Math.sin(dLat / 2), 2) + Math.cos(lat1Rad) * Math.cos(lat2Rad) * Math.pow(Math.sin(dLon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c;

        // Bearing formula
        double x = Math.sin(dLon) * Math.cos(lat2Rad);
        double y = Math.cos(lat1Rad) * Math.sin(lat2Rad) - Math.sin(lat1Rad) * Math.cos(lat2Rad) * Math.cos(dLon);
        double initialBearing = Math.atan2(x, y);
        double initialBearingDegrees = Math.toDegrees(initialBearing);
        double compassBearing = (initialBearingDegrees + 360) % 360;

        // Convert bearing to cardinal direction
        String[] directions = {"N", "NE", "E", "SE", "S", "SW", "W", "NW"};
        int index = (int) Math.round(compassBearing / 45) % 8;
        int finalDistance = (int) Math.round(distance);

        return new HaversineComponents(finalDistance, directions[index]);
    }

    // Left for debugging purposes
    public static void main(String[] args) {
        double lat1 = 32.61511102609208;
        double lon1 = -85.43400006834719;  // Auburn University Airport
        double lat2 = 32.516333026013065;
        double lon2 = -84.93886106795108;  // Columbus Airport

        HaversineComponents result = haversineDistanceAndBearing(lat1, lon1, lat2, lon2);

        int distance = result.getDistance();
        String bearing = result.getBearing();
        String airport = "KAUO";

        System.out.printf("Forecast reported %d nm %s of %s", distance, bearing, airport);
    }
}
