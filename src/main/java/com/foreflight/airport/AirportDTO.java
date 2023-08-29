package com.foreflight.airport;

import com.foreflight.airport.runway.RunwayDTO;
import com.foreflight.weather.Weather;
import com.foreflight.weather.WeatherDTO;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AirportDTO {

    private String ident;
    private String name;
    private Float latitude;
    private Float longitude;
    private List<RunwayDTO> runways;
    private WeatherDTO weather;

    protected static AirportDTO fromEntity(Airport airport) {
        return AirportDTO.builder()
                .ident(airport.getIcao())
                .name(airport.getName())
                .latitude(airport.getLatitude())
                .longitude(airport.getLongitude())
                .runways(RunwayDTO.fromEntities(airport.getRunways()))
                .weather(WeatherDTO.fromEntity(airport.getWeather()))
                .build();
    }

    protected static List<AirportDTO> fromEntities(List<Airport> airports) {
        return airports.stream()
                .map(AirportDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
