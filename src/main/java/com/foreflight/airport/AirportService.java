package com.foreflight.airport;

import com.foreflight.airport.interfaces.AirportServiceInterface;
import com.foreflight.config.AirportAPI;
import com.foreflight.config.WeatherAPI;
import com.foreflight.weather.Weather;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AirportService implements AirportServiceInterface {

    private final AirportAPI airportAPI;
    private final WeatherAPI weatherAPI;

    @Override
    public Optional<List<AirportDTO>> getAll(String idents) {
        String[] identifiers = idents.split(",");
        List<Airport> airports = new ArrayList<>();

        for (String identifier : identifiers) {
            Weather weather = weatherAPI.findWeather(identifier).getBody();
            Airport airport = airportAPI.findAirport(identifier).getBody();
            Objects.requireNonNull(airport).setWeather(weather);
            airports.add(airport);
        }

        return Optional.of(AirportDTO.fromEntities(airports));
    }
}
