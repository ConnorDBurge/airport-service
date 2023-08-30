package com.foreflight.weather.report.current;

import com.foreflight.weather.report.current.cloudlayer.CloudLayer;
import com.foreflight.weather.report.current.visibility.Visibility;
import com.foreflight.weather.report.current.wind.Wind;
import lombok.*;

import java.util.List;
import java.util.Optional;

@Getter
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
                .visibility(Optional.ofNullable(conditions.getVisibility())
                        .map(Visibility::getDistanceSm)
                        .orElse(null))
                .windSpeed(Optional.ofNullable(conditions.getWind())
                        .map(Wind::getSpeedKts)
                        .orElse(null))
                .windDirection(Optional.ofNullable(conditions.getWind())
                        .map(Wind::getDirection)
                        .orElse(null))
                .build();
    }
}
