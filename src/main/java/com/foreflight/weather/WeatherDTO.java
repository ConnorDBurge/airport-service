package com.foreflight.weather;

import com.foreflight.weather.report.current.CurrentDTO;
import com.foreflight.weather.report.forecast.ForecastDTO;
import lombok.*;

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
        WeatherDTOBuilder weatherDTO =  WeatherDTO.builder();

        if (weather.getReport().getCurrent() == null) {
            weatherDTO.remarks(List.of("Current weather not observed"));
        } else {
            weatherDTO.remarks(new ArrayList<>());
        }

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
