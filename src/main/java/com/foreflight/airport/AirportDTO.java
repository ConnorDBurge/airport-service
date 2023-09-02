package com.foreflight.airport;

import com.foreflight.airport.runway.RunwayDTO;
import com.foreflight.weather.WeatherDTO;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AirportDTO {

    private String ident;
    private String name;
    private Double latitude;
    private Double longitude;
    private Integer magneticVariation;
    private List<RunwayDTO> runways;
    private WeatherDTO weather;

    protected static AirportDTO fromEntity(Airport airport) {
        return AirportDTO.builder()
                .ident(airport.getIcao())
                .name(airport.getName())
                .latitude(airport.getLatitude())
                .longitude(airport.getLongitude())
                .magneticVariation(airport.getMagneticVariation())
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
