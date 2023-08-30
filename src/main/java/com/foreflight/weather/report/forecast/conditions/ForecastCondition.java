package com.foreflight.weather.report.forecast.conditions;

import com.foreflight.weather.report.current.wind.Wind;
import com.foreflight.weather.report.forecast.period.Period;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Data;

@Data
@Embeddable
public class ForecastCondition {

    private String text;
    @Embedded
    private Wind wind;
    @Embedded
    private Period period;
    private Double temperature;
}
