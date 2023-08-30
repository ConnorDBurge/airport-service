package com.foreflight.weather.report.forecast;

import com.foreflight.weather.report.forecast.conditions.ForecastCondition;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Forecast {

    @ElementCollection
    private List<ForecastCondition> conditions;
}
