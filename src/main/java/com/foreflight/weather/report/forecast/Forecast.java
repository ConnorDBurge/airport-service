package com.foreflight.weather.report.forecast;

import com.foreflight.weather.report.forecast.conditions.ForecastCondition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Forecast {

    private String ident; // Reporting airport ident
    private List<ForecastCondition> conditions;
    private List<String> remarks = new ArrayList<>();
    private Double lat;
    private Double lon;

    public void addRemark(String remark) {
        remarks.add(remark);
    }
}
