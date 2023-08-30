package com.foreflight.weather.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.foreflight.weather.report.current.Current;
import com.foreflight.weather.report.forecast.Forecast;
import jakarta.persistence.Embeddable;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Report {

    @JsonProperty("conditions")
    private Current current;

    private Forecast forecast;
}
