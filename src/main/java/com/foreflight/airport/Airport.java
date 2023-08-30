package com.foreflight.airport;

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
    private Float latitude;
    private Float longitude;
    private List<Runway> runways;
    @Setter
    private Weather weather;
}
