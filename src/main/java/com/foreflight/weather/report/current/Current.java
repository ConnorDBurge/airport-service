package com.foreflight.weather.report.current;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.foreflight.weather.report.current.cloudlayer.CloudLayer;
import com.foreflight.weather.report.current.visibility.Visibility;
import com.foreflight.weather.report.current.wind.Wind;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Current {

    private String ident;
    @JsonProperty("tempC")
    private Double temperature;
    private Double relativeHumidity;
    private Visibility visibility;
    private List<CloudLayer> cloudLayers;
    private Wind wind;
}
