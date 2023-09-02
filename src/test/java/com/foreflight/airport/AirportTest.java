package com.foreflight.airport;

import com.foreflight.airport.runway.Runway;
import com.foreflight.weather.Weather;
import com.foreflight.weather.WeatherDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AirportTest {

    private Airport underTest;
    private final List<Runway> runways = List.of(Runway.builder().build());
    private final Weather weather = Weather.builder().build();

    @BeforeEach
    void setUp() {
        underTest = Airport.builder()
                .icao("KFFC")
                .name("Atlanta Regional Airport - Falcon Field")
                .latitude(33.35725)
                .longitude(-84.57172)
                .runways(runways)
                .weather(weather)
                .build();
    }

    @Test
    void getIcao() {
        String actual = underTest.getIcao();
        assertEquals("KFFC", actual);
    }

    @Test
    void getName() {
        String actual = underTest.getName();
        assertEquals("Atlanta Regional Airport - Falcon Field", actual);
    }

    @Test
    void getLatitude() {
        Double actual = underTest.getLatitude();
        assertEquals(33.35725, actual);
    }

    @Test
    void getLongitude() {
        Double actual = underTest.getLongitude();
        assertEquals(-84.57172, actual);
    }

    @Test
    void getRunways() {
        List<Runway> actual = underTest.getRunways();
        assertEquals(runways, actual);
    }

    @Test
    void getWeather() {
        Weather actual = underTest.getWeather();
        assertEquals(weather, actual);
    }

    @Test
    void setWeather() {
        underTest.setWeather(weather);
        assertEquals(weather, underTest.getWeather());
    }

    @Test
    void builder() {
        Runway mockRunway = Runway.builder().build();
        Weather mockWeather = Weather.builder().build();
        Airport airport = Airport.builder()
                .icao("KFFC")
                .name("Atlanta Regional Airport - Falcon Field")
                .latitude(33.35725)
                .longitude(-84.57172)
                .runways(List.of(mockRunway))
                .weather(mockWeather)
                .build();
        assertEquals("KFFC", airport.getIcao());
        assertEquals("Atlanta Regional Airport - Falcon Field", airport.getName());
        assertEquals(33.35725, airport.getLatitude());
        assertEquals(-84.57172, airport.getLongitude());
        assertEquals(List.of(mockRunway), airport.getRunways());
        assertEquals(mockWeather, airport.getWeather());
    }

    @Test
    void builder_noArgs() {
        Airport airport = Airport.builder().build();
        assertNull(airport.getIcao());
        assertNull(airport.getName());
        assertNull(airport.getLatitude());
        assertNull(airport.getLongitude());
        assertNull(airport.getRunways());
        assertNull(airport.getWeather());
    }

    @Test
    void no_args_constructor() {
        Airport airport = new Airport();
        assertNull(airport.getIcao());
        assertNull(airport.getName());
        assertNull(airport.getLatitude());
        assertNull(airport.getLongitude());
        assertNull(airport.getRunways());
        assertNull(airport.getWeather());
    }
}
