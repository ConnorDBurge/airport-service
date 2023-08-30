package com.foreflight.weather;

import com.foreflight.weather.report.current.CurrentDTO;
import com.foreflight.weather.report.forecast.ForecastDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherDTO {

    private CurrentDTO current;
    private ForecastDTO forecast;

    public static WeatherDTO fromEntity(Weather weather) {
        return WeatherDTO.builder()
                .current(Optional.ofNullable(weather.getReport().getCurrent())
                        .map(CurrentDTO::fromEntity)
                        .orElse(null))
                .forecast(Optional.ofNullable(weather.getReport().getForecast())
                        .map(ForecastDTO::fromEntity)
                        .orElse(null))
                .build();
    }
}
