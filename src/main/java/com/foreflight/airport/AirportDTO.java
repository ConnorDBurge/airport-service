package com.foreflight.airport;

import com.foreflight.airport.runway.RunwayDTO;
import com.foreflight.weather.WeatherDTO;
import lombok.*;

import java.util.List;
import java.util.Optional;
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
    private List<RunwayDTO> runways;
    private WeatherDTO weather;

    protected static AirportDTO fromEntity(Airport airport) {
        Optional<Airport> optAirport = Optional.ofNullable(airport);
        return AirportDTO.builder()
                .ident(optAirport.map(Airport::getIcao).orElse(null))
                .name(optAirport.map(Airport::getName).orElse(null))
                .latitude(optAirport.map(Airport::getLatitude).orElse(null))
                .longitude(optAirport.map(Airport::getLongitude).orElse(null))
                .runways(optAirport.map(a -> RunwayDTO.fromEntities(a.getRunways())).orElse(List.of()))
                .weather(optAirport.map(a -> WeatherDTO.fromEntity(a.getWeather())).orElse(null))
                .build();
    }

    protected static List<AirportDTO> fromEntities(List<Airport> airports) {
        return airports.stream()
                .map(AirportDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
