package com.foreflight.weather;

import com.foreflight.weather.report.current.Current;
import com.foreflight.weather.report.current.CurrentDTO;
import com.foreflight.weather.report.current.wind.Wind;
import com.foreflight.weather.report.forecast.ForecastDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        WeatherDTOBuilder weatherDTO =  WeatherDTO.builder().remarks(new ArrayList<>());
        Optional<Current> currentReportOpt = Optional.ofNullable(weather.getReport().getCurrent());
        Optional<Wind> currentWindOpt = currentReportOpt.map(Current::getWind);

        if (currentReportOpt.isEmpty()) {
            weatherDTO.remarks.add("Current weather not observed");
        }

        currentWindOpt.ifPresent(currentWind -> {
            if (currentWind.getVariable()) {
                weatherDTO.remarks.add("Current wind is variable at " + currentWind.getSpeedKts() + " knots");
            }
        });

        return weatherDTO
                .ident(weather.getIdent().toUpperCase())
                .current(Optional.ofNullable(weather.getReport().getCurrent())
                        .map(CurrentDTO::fromEntity)
                        .orElse(null))
                .forecast(Optional.ofNullable(weather.getReport().getForecast())
                        .map(ForecastDTO::fromEntity)
                        .orElse(null))
                .build();

    }

    public static List<WeatherDTO> fromEntities(List<Weather> weather) {
        return weather.stream()
                .map(WeatherDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
