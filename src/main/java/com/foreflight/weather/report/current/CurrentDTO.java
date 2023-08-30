package com.foreflight.weather.report.current;

import com.foreflight.weather.report.current.cloudlayer.CloudLayer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrentDTO {

    private Double temperature;
    private Double relativeHumidity;
    private String cloudCoverage;
    private Double visibility;
    private Double windSpeed;
    private Integer windDirection;

    public static CurrentDTO fromEntity(Current conditions) {
        String greatestCoverage = null;
        List<CloudLayer> cloudLayers = conditions.getCloudLayers();

        if (cloudLayers != null && !cloudLayers.isEmpty()) {
            greatestCoverage = cloudLayers.get(cloudLayers.size() - 1).getCoverage();
        }

        return CurrentDTO.builder()
                .temperature(conditions.getTemperature())
                .relativeHumidity(conditions.getRelativeHumidity())
                .cloudCoverage(greatestCoverage)
                .visibility(conditions.getVisibility().getDistanceSm())
                .windSpeed(conditions.getWind().getSpeedKts())
                .windDirection(conditions.getWind().getDirection())
                .build();
    }
}
