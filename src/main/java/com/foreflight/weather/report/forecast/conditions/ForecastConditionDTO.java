package com.foreflight.weather.report.forecast.conditions;

import com.foreflight.weather.report.current.wind.Wind;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
public class ForecastConditionDTO {

    private String text;
    private Wind wind;
    private String dateStart;
    private Double temperature; // Not available in the API under /weather/report/forecast/conditions
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");

    public static ForecastConditionDTO fromEntity(ForecastCondition forecastCondition) {
        LocalDateTime dateTime = LocalDateTime.parse(forecastCondition.getPeriod().getDateStart(), formatter);
        return ForecastConditionDTO.builder()
                .text(forecastCondition.getText())
                .wind(forecastCondition.getWind())
                .temperature(forecastCondition.getTemperature())
                .dateStart(String.format("%02d:%02d", dateTime.getHour(), dateTime.getMinute()))
                .build();
    }

    public static List<ForecastConditionDTO> fromEntities(List<ForecastCondition> forecastConditions) {
        if (forecastConditions == null || forecastConditions.size() < 2) {
            return Collections.emptyList();
        }
        return forecastConditions.stream()
                .skip(1)
                .limit(2)
                .map(ForecastConditionDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
