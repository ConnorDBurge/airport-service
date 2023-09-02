package com.foreflight.util;

import com.foreflight.airport.Airport;
import com.foreflight.airport.runway.Runway;
import com.foreflight.weather.report.Report;
import com.foreflight.weather.report.current.Current;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
     * Marks the best runways for landing and takeoff
     *
     * @param runways     the runways to mark
     * @param maxHeadWind the maximum headwind
     */
    public static void markBestRunways(List<Runway> runways, double maxHeadWind) {
        for (Runway runway : runways) {
            runway.setBestRunway(runway.getHeadWind() != null && runway.getHeadWind() == maxHeadWind);
        }
    }

    /**
     * Calculates the crosswind and headwind components for each runway
     *
     * @param airport the airport to calculate the wind components for
     */
    public static void calculateWindComponents(Airport airport) {
        Optional<Report> report = Optional.ofNullable(airport.getWeather().getReport());
        if (report.isEmpty()) {
            return;
        }

        Current current = report.map(Report::getCurrent).orElse(null);
        if (current == null ||
                current.getWind().getSpeedKts() == null ||
                current.getWind().getFrom() == null) {
            for (Runway runway : airport.getRunways()) {
                runway.setCrossWind(null);
                runway.setHeadWind(null);
            }
            return;
        }

        List<Runway> runways = airport.getRunways();
        List<Runway> recipRunways = new ArrayList<>(runways);
        double maxHeadWind = Double.NEGATIVE_INFINITY;
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

            if (runway.getHeadWind() != null) {
                maxHeadWind = Math.max(maxHeadWind, runway.getHeadWind());
            }
        }

        markBestRunways(recipRunways, maxHeadWind);
        airport.setRunways(recipRunways.stream().sorted().collect(Collectors.toList()));
    }
}
