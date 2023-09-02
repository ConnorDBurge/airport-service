package com.foreflight.airport;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.foreflight.airport.runway.Runway;
import com.foreflight.weather.Weather;
import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Airport {

    private String icao;
    private String name;
    private Double latitude;
    private Double longitude;
    private List<Runway> runways;
    @Setter
    private Weather weather;
    private Integer magneticVariationEast;
    @JsonProperty("magneticVariationWest")
    private Integer magneticVariation;
}
