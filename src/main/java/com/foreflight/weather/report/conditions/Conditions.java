package com.foreflight.weather.report.conditions;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.foreflight.weather.report.conditions.cloudlayer.CloudLayer;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.util.List;

@Data
@Embeddable
public class Conditions {

    private String ident;
    @JsonProperty("tempC")
    private Double temperature;
    private Double relativeHumidity;
    private List<CloudLayer> cloudLayers;
}
