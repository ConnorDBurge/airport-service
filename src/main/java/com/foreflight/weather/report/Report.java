package com.foreflight.weather.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.foreflight.weather.report.current.Current;
import com.foreflight.weather.report.forecast.Forecast;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Report {

    @JsonProperty("conditions")
    private Current current;
    private Forecast forecast;
}
