package com.foreflight.weather.report.current;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.foreflight.weather.report.current.cloudlayer.CloudLayer;
import com.foreflight.weather.report.current.visibility.Visibility;
import com.foreflight.weather.report.current.wind.Wind;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Data;

import java.util.List;

@Data
@Embeddable
public class Current {

    private String ident;
    @JsonProperty("tempC")
    private Double temperature;
    private Double relativeHumidity;
    @Embedded
    private Visibility visibility;
    @ElementCollection
    private List<CloudLayer> cloudLayers;
    @Embedded
    private Wind wind;
}
