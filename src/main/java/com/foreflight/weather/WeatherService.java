package com.foreflight.weather;

import com.foreflight.external.WeatherAPI;
import com.foreflight.weather.interfaces.WeatherServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WeatherService implements WeatherServiceInterface {

    private final WeatherAPI weatherAPI;

    @Override
    public List<WeatherDTO> getAll(String idents) {
        String[] identifiers = idents.split(",");
        List<Weather> weather = new ArrayList<>();

        for (String identifier : identifiers) {
            Optional<Weather> weatherOpt = Optional.ofNullable(weatherAPI.findWeather(identifier).getBody());

            if (weatherOpt.isPresent()) {
                Weather identWeather = weatherOpt.get();
                identWeather.setIdent(identifier);
                weather.add(identWeather);
            }
        }

        return WeatherDTO.fromEntities(weather);
    }
}
