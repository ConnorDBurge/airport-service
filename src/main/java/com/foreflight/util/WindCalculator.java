package com.foreflight.util;

import com.foreflight.airport.Airport;
import com.foreflight.airport.runway.Runway;
import com.foreflight.weather.report.Report;
import com.foreflight.weather.report.current.Current;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.xml.transform.Source;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.Validate.notNull;

@Component
public class WindCalculator {

    public static final double TO_RADIANS = Math.PI / 180.0;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class WindComponents {
        public Double headWind = null;
        public Double crossWind = null;
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
    public static void processRunways(Airport airport) {
        Optional<Report> report = Optional.ofNullable(airport.getWeather().getReport());
        if (report.isEmpty()) {
            return;
        }

        List<Runway> runways = airport.getRunways();
        List<Runway> recipRunways = new ArrayList<>(runways);

        Current current = report.map(Report::getCurrent).orElse(null);
        boolean noWind = isNull(current) || isNull(current.getWind());
        if (noWind) {
            airport.getWeather().addRemark("Wind is not reported or is unknown");
        }

        double maxHeadWind = Double.NEGATIVE_INFINITY;
        for (Runway runway : runways) {
            WindComponents windComponents = noWind
                    ? new WindComponents()
                    : calculateWindComponents(
                        airport.getWeather().getReport().getCurrent().getWind().getSpeedKts(),
                        airport.getWeather().getReport().getCurrent().getWind().getFrom(),
                        runway.getMagneticHeading(),
                        airport.getMagneticVariation());

            runway.setCrossWind(windComponents.getCrossWind());
            runway.setHeadWind(windComponents.getHeadWind());

            WindCalculator.WindComponents recipWindComponents = noWind
                    ? new WindComponents()
                    : calculateWindComponents(
                        airport.getWeather().getReport().getCurrent().getWind().getSpeedKts(),
                        airport.getWeather().getReport().getCurrent().getWind().getFrom(),
                        runway.getRecipMagneticHeading(),
                        airport.getMagneticVariation());

            Runway recip = Runway.builder()
                    .ident(runway.getIdent())
                    .name(runway.getRecipName())
                    .magneticHeading(runway.getRecipMagneticHeading())
                    .crossWind(recipWindComponents.getCrossWind())
                    .headWind(recipWindComponents.getHeadWind())
                    .build();

            recipRunways.add(recip);

            if (!isNull(runway.getHeadWind()) && !isNull(recip.getHeadWind())) {
                maxHeadWind = Math.max(maxHeadWind, runway.getHeadWind());
                maxHeadWind = Math.max(maxHeadWind, recip.getHeadWind());
            }
        }

        markBestRunways(recipRunways, maxHeadWind);
        airport.setRunways(recipRunways.stream().sorted().collect(Collectors.toList()));
    }
}
