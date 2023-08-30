package com.foreflight.weather.report.forecast;

import com.foreflight.weather.report.forecast.conditions.ForecastConditionDTO;
import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ForecastDTO {

    private List<ForecastConditionDTO> conditions;

    public static ForecastDTO fromEntity(Forecast forecast) {
        return ForecastDTO.builder()
                .conditions(ForecastConditionDTO.fromEntities(forecast.getConditions()))
                .build();
    }
}
