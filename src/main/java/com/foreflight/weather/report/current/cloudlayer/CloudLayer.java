package com.foreflight.weather.report.current.cloudlayer;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CloudLayer {
    private String coverage;
    private String type;
    private Double altitudeFt;
    private Boolean ceiling;
}
