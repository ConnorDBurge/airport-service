package com.foreflight.weather.report.forecast;

import com.foreflight.weather.report.forecast.conditions.ForecastCondition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Forecast {
    private List<ForecastCondition> conditions;
}
