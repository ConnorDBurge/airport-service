package com.foreflight.weather;

import com.foreflight.weather.report.conditions.cloudlayer.CloudLayer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherDTO {

    private Double temperature;
    private Double relativeHumidity;
    private String cloudCoverage;
    private Integer visibility;
    private Integer windSpeed;
    private Integer windDirection;

    public static WeatherDTO fromEntity(Weather weather) {
        String greatestCoverage = null;
        List<CloudLayer> cloudLayers = weather.getReport().getConditions().getCloudLayers();
        if (cloudLayers != null && !cloudLayers.isEmpty()) {
            greatestCoverage = cloudLayers.get(cloudLayers.size() - 1).getCoverage();
        }

        return WeatherDTO.builder()
                .temperature(weather.getReport().getConditions().getTemperature())
                .relativeHumidity(weather.getReport().getConditions().getRelativeHumidity())
                .cloudCoverage(greatestCoverage)
                .build();
    }
}
