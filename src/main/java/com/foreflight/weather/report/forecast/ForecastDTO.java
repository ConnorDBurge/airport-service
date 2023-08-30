package com.foreflight.weather.report.forecast;

import com.foreflight.weather.report.forecast.conditions.ForecastConditionDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ForecastDTO {

    private List<ForecastConditionDTO> conditions;

    public static ForecastDTO fromEntity(Forecast forecast) {
        return ForecastDTO.builder()
                .conditions(ForecastConditionDTO.fromEntities(forecast.getConditions()))
                .build();
    }
}
