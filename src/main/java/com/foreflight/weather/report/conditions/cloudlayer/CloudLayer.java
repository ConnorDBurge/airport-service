package com.foreflight.weather.report.conditions.cloudlayer;

import lombok.Data;

@Data
public class CloudLayer {
    private String coverage;
    private String type;
    private Double altitudeFt;
    private Boolean ceiling;
}
