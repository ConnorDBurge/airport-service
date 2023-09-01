package com.foreflight.airport;

import com.foreflight.airport.interfaces.AirportServiceInterface;
import com.foreflight.external.AirportAPI;
import com.foreflight.external.WeatherAPI;
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
    public List<AirportDTO> getAll(String idents) {
        String[] identifiers = idents.split(",");
        List<Airport> airports = new ArrayList<>();

        for (String identifier : identifiers) {
            Optional<Airport> airportOpt = Optional.ofNullable(airportAPI.findAirport(identifier).getBody());
            Optional<Weather> weatherOpt = Optional.ofNullable(weatherAPI.findWeather(identifier).getBody());

            if (airportOpt.isPresent() && weatherOpt.isPresent()) {
                Airport airport = airportOpt.get();
                Weather weather = weatherOpt.get();
                weather.setIdent(identifier);
                airport.setWeather(weather);
                airports.add(airport);
            }
        }

        return AirportDTO.fromEntities(airports);
    }
}
