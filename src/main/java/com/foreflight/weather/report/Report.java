package com.foreflight.weather.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.foreflight.weather.report.current.Current;
import com.foreflight.weather.report.forecast.Forecast;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Report {

    @Embedded
    @JsonProperty("conditions")
    private Current current;

    @Embedded
    private Forecast forecast;
}
