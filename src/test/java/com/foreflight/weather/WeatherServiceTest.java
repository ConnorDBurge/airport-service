package com.foreflight.weather;

import com.foreflight.airport.Airport;
import com.foreflight.external.AirportAPI;
import com.foreflight.external.WeatherAPI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {

    private WeatherService underTest;
    @Mock private WeatherAPI mockWeatherAPI;
    @Mock private AirportAPI mockAirportAPI;

    @BeforeEach
    void setUp() {
        underTest = new WeatherService(mockWeatherAPI, mockAirportAPI);
    }

    @Test
    void testGetAll_one() {
        String idents = "KFFC";

        Weather mockWeather1 = Weather.builder()
                .ident("KFFC")
                .build();

        Airport mockAirport1 = Airport.builder()
                .icao("KFFC")
                .latitude(33.3573)
                .longitude(-84.5717)
                .build();

        when(mockWeatherAPI.findWeather("KFFC")).thenReturn(ResponseEntity.of(Optional.of(mockWeather1)));
        when(mockAirportAPI.findAirport("KFFC")).thenReturn(ResponseEntity.of(Optional.of(mockAirport1)));

        List<WeatherDTO> result = underTest.getAll(idents);

        assertEquals(1, result.size());
        assertEquals("KFFC", result.get(0).getIdent());
    }

    @Test
    void testGetAll_two() {
        String idents = "KFFC,KATL";

        Weather mockWeather1 = Weather.builder()
                .ident("KFFC")
                .build();

        Weather mockWeather2 = Weather.builder()
                .ident("KATL")
                .build();

        Airport mockAirport1 = Airport.builder()
                .icao("KFFC")
                .latitude(33.3573)
                .longitude(-84.5717)
                .build();

        Airport mockAirport2 = Airport.builder()
                .icao("KATL")
                .latitude(33.3573)
                .longitude(-84.5717)
                .build();

        when(mockWeatherAPI.findWeather("KFFC")).thenReturn(ResponseEntity.of(Optional.of(mockWeather1)));
        when(mockAirportAPI.findAirport("KFFC")).thenReturn(ResponseEntity.of(Optional.of(mockAirport1)));
        when(mockWeatherAPI.findWeather("KATL")).thenReturn(ResponseEntity.of(Optional.of(mockWeather2)));
        when(mockAirportAPI.findAirport("KATL")).thenReturn(ResponseEntity.of(Optional.of(mockAirport2)));

        List<WeatherDTO> result = underTest.getAll(idents);

        assertEquals(2, result.size());
        assertEquals("KFFC", result.get(0).getIdent());
        assertEquals("KATL", result.get(1).getIdent());
    }
}
