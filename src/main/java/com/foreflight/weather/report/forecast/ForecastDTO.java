package com.foreflight.weather.report.forecast;

import com.foreflight.weather.report.forecast.conditions.ForecastConditionDTO;
import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ForecastDTO {

    private String ident; // Reporting airport ident
    private List<ForecastConditionDTO> conditions;

    public static ForecastDTO fromEntity(Forecast forecast) {
        return ForecastDTO.builder()
                .ident(forecast.getIdent().toUpperCase())
                .conditions(ForecastConditionDTO.fromEntities(forecast.getConditions()))
                .build();
    }
}
