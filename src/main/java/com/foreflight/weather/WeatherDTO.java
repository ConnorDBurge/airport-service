package com.foreflight.weather;

import com.foreflight.util.HaversineCalculator;
import com.foreflight.weather.report.current.Current;
import com.foreflight.weather.report.current.CurrentDTO;
import com.foreflight.weather.report.current.wind.Wind;
import com.foreflight.weather.report.forecast.Forecast;
import com.foreflight.weather.report.forecast.ForecastDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static com.foreflight.util.HaversineCalculator.haversineDistanceAndBearing;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeatherDTO {

    private String ident;
    private List<String> remarks;
    private CurrentDTO current;
    private ForecastDTO forecast;

    public static WeatherDTO fromEntity(Weather weather) {
        if (Optional.ofNullable(weather).isEmpty()) {
            return WeatherDTO.builder().current(null).forecast(null).build();
        }

        if (Optional.ofNullable(weather.getReport()).isEmpty()) {
            return WeatherDTO.builder().ident(weather.getIdent()).current(null).forecast(null).build();
        }

        Optional<Current> currentReportOpt = Optional.ofNullable(weather.getReport().getCurrent());
        Optional<Wind> currentWindOpt = currentReportOpt.map(Current::getWind);
        Optional<Forecast> forecastReportOpt = Optional.ofNullable(weather.getReport().getForecast());
        setWindRemarks(currentReportOpt, currentWindOpt, weather);
        setForecastRemarks(forecastReportOpt, weather);

        return WeatherDTO.builder()
                .ident(weather.getIdent().toUpperCase())
                .current(Optional.ofNullable(weather.getReport().getCurrent())
                        .map(CurrentDTO::fromEntity)
                        .orElse(null))
                .forecast(Optional.ofNullable(weather.getReport().getForecast())
                        .map(ForecastDTO::fromEntity)
                        .orElse(null))
                .remarks(weather.getRemarks())
                .build();

    }

    public static List<WeatherDTO> fromEntities(List<Weather> weather) {
        return weather.stream()
                .map(WeatherDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public static void setWindRemarks(Optional<Current> currentReport, Optional<Wind> currentWind, Weather weather) {
        if (currentReport.isEmpty()) {
            weather.addRemark("Current weather not observed");
        }

        currentWind.ifPresent(wind -> {
            if (wind.getVariable()) {
                weather.addRemark("Current wind is variable at " + wind.getSpeedKts() + " knots");
            }
        });
    }

    public static void setForecastRemarks(Optional<Forecast> forecastReport, Weather weather) {
        if (forecastReport.isEmpty()) {
            weather.addRemark("Forecast not available");
        }

        forecastReport.ifPresent(forecast -> {
            if (forecast.getConditions().isEmpty()) {
                weather.addRemark("Forecast conditions not available");
                return;
            }

            if (!forecast.getIdent().equals(weather.getIdent())) {
                HaversineCalculator.HaversineComponents haversine = haversineDistanceAndBearing(
                        weather.getLat(),
                        weather.getLon(),
                        forecast.getLat(),
                        forecast.getLon());

                int distance = haversine.getDistance();
                String bearing = haversine.getBearing();

                forecast.addRemark(String.format(
                        "Reported %d nm %s of %s", distance, bearing, weather.getIdent().toUpperCase()));
            }
        });
    }
}
