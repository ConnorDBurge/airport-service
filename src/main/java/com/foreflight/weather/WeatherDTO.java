package com.foreflight.weather;

import com.foreflight.weather.report.current.CurrentDTO;
import com.foreflight.weather.report.forecast.ForecastDTO;
import lombok.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeatherDTO {

    private String ident;
    private CurrentDTO current;
    private ForecastDTO forecast;

    public static WeatherDTO fromEntity(Weather weather) {
        return WeatherDTO.builder()
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
