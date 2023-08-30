package com.foreflight.weather.report.current.cloudlayer;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class CloudLayer {
    private String coverage;
    private String type;
    private Double altitudeFt;
    private Boolean ceiling;
}
