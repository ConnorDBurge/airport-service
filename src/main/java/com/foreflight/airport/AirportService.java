package com.foreflight.airport;

import com.foreflight.airport.interfaces.AirportServiceInterface;
import com.foreflight.external.AirportAPI;
import com.foreflight.external.WeatherAPI;
import com.foreflight.weather.Weather;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.foreflight.util.WindCalculator.calculateWindComponents;
import static com.foreflight.util.WindCalculator.processRunways;

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
                weather.setLat(airport.getLatitude());
                weather.setLon(airport.getLongitude());

                airport.setWeather(weather);
                processRunways(airport);
                airports.add(airport);
            }
        }

        return AirportDTO.fromEntities(airports);
    }
}
