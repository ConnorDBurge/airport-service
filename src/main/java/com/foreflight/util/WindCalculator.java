package com.foreflight.util;

import com.foreflight.airport.Airport;
import com.foreflight.airport.runway.Runway;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class WindCalculator {

    public static final double TO_RADIANS = Math.PI / 180.0;

    @Getter
    @AllArgsConstructor
    public static class WindComponents {
        public double headWind;
        public double crossWind;
    }

    /**
     * Calculate headwind and crosswind components
     *
     * @param windSpeed         The speed of the wind in knots
     * @param windDirectionTrue The direction of the wind in degrees true
     * @param runwayMagHeading  The magnetic heading of the runway
     * @param magneticVariation The magnetic variation in degrees
     * @return WindComponents object containing headwind and crosswind
     */
    public static WindComponents calculateWindComponents(double windSpeed, double windDirectionTrue, double runwayMagHeading, double magneticVariation) {
        double runwayTrueHeading = runwayMagHeading + magneticVariation;
        double angleDifference = windDirectionTrue - runwayTrueHeading;

        double angleDifferenceInRadians = angleDifference * TO_RADIANS;

        double headWind = windSpeed * Math.cos(angleDifferenceInRadians);
        double crossWind = windSpeed * Math.sin(angleDifferenceInRadians);

        return new WindComponents(headWind, crossWind);
    }

    /**
     * Calculates the crosswind and headwind components for each runway
     * @param airport the airport to calculate the wind components for
     */
    public static void calculateWindComponents(Airport airport) {
        if (airport.getWeather().getReport().getCurrent() == null) {
            for (Runway runway : airport.getRunways()) {
                runway.setCrossWind(null);
                runway.setHeadWind(null);
            }
            return;
        }

        List<Runway> runways = airport.getRunways();
        List<Runway> recipRunways = new ArrayList<>();
        for (Runway runway : runways) {
            WindComponents windComponents = calculateWindComponents(
                    airport.getWeather().getReport().getCurrent().getWind().getSpeedKts(),
                    airport.getWeather().getReport().getCurrent().getWind().getFrom(),
                    runway.getMagneticHeading(),
                    airport.getMagneticVariation());

            runway.setCrossWind(windComponents.getCrossWind());
            runway.setHeadWind(windComponents.getHeadWind());

            WindCalculator.WindComponents recipWindComponents = WindCalculator.calculateWindComponents(
                    airport.getWeather().getReport().getCurrent().getWind().getSpeedKts(),
                    airport.getWeather().getReport().getCurrent().getWind().getFrom(),
                    runway.getRecipMagneticHeading(),
                    airport.getMagneticVariation());

            recipRunways.add(Runway.builder()
                    .ident(runway.getIdent().replaceAll("(\\d+)-(\\d+)", "$2-$1"))
                    .name(runway.getRecipName())
                    .magneticHeading(runway.getRecipMagneticHeading())
                    .crossWind(recipWindComponents.getCrossWind())
                    .headWind(recipWindComponents.getHeadWind())
                    .build());
        }
        runways.addAll(recipRunways);
    }
}
