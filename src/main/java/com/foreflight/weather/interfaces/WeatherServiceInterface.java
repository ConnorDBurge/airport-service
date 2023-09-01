package com.foreflight.weather.interfaces;

import com.foreflight.weather.WeatherDTO;

import java.util.List;

public interface WeatherServiceInterface {

    List<WeatherDTO> getAll(String idents);
}
