package com.foreflight.airport;

import com.foreflight.airport.runway.Runway;
import com.foreflight.weather.Weather;
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
    @Mock private Runway runway;
    @Mock private Weather weather;

    @BeforeEach
    void setUp() {
        underTest = Airport.builder()
                .icao("KFFC")
                .name("Atlanta Regional Airport - Falcon Field")
                .latitude(33.35725F)
                .longitude(-84.57172F)
                .runways(List.of(runway))
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
        Float actual = underTest.getLatitude();
        assertEquals(33.35725F, actual);
    }

    @Test
    void getLongitude() {
        Float actual = underTest.getLongitude();
        assertEquals(-84.57172F, actual);
    }

    @Test
    void getRunways() {
        List<Runway> actual = underTest.getRunways();
        assertEquals(List.of(runway), actual);
    }

    @Test
    void getWeather() {
        Weather actual = underTest.getWeather();
        assertEquals(weather, actual);
    }

    @Test
    void builder() {
        Runway mockRunway = Runway.builder().build();
        Weather mockWeather = Weather.builder().build();
        Airport airport = Airport.builder()
                .icao("KFFC")
                .name("Atlanta Regional Airport - Falcon Field")
                .latitude(33.35725F)
                .longitude(-84.57172F)
                .runways(List.of(mockRunway))
                .weather(mockWeather)
                .build();
        assertEquals("KFFC", airport.getIcao());
        assertEquals("Atlanta Regional Airport - Falcon Field", airport.getName());
        assertEquals(33.35725F, airport.getLatitude());
        assertEquals(-84.57172F, airport.getLongitude());
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

    @Test
    void setWeather() {
        underTest.setWeather(weather);
        assertEquals(weather, underTest.getWeather());
    }
}
