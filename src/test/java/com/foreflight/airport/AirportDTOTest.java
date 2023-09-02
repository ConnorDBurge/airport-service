package com.foreflight.airport;

import com.foreflight.airport.runway.RunwayDTO;
import com.foreflight.weather.WeatherDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class AirportDTOTest {

    private AirportDTO underTest;
    private final List<RunwayDTO> runways = List.of(RunwayDTO.builder().build());
    private final WeatherDTO weather = WeatherDTO.builder().build();

    @BeforeEach
    void setUp() {
        underTest = AirportDTO.builder()
                .ident("KFFC")
                .name("Atlanta Regional Airport - Falcon Field")
                .latitude(33.35725)
                .longitude(-84.57172)
                .runways(runways)
                .weather(weather)
                .build();
    }

    @Test
    void fromEntity() {
        Airport airport = Airport.builder()
                .icao("KFFC")
                .name("Atlanta Regional Airport - Falcon Field")
                .build();
        AirportDTO airportDTO = AirportDTO.fromEntity(airport);
        assertEquals("KFFC", airportDTO.getIdent());
        assertEquals("Atlanta Regional Airport - Falcon Field", airportDTO.getName());
    }

    @Test
    void fromEntities() {
        Airport kffc = Airport.builder()
                .icao("KFFC")
                .name("Atlanta Regional Airport - Falcon Field")
                .build();
        Airport kauo = Airport.builder()
                .icao("KAUO")
                .name("Auburn University Regional Airport")
                .build();

        List<AirportDTO> airportDTOs = AirportDTO.fromEntities(List.of(kffc, kauo));
        assertEquals(2, airportDTOs.size());
        assertEquals("KFFC", airportDTOs.get(0).getIdent());
        assertEquals("Atlanta Regional Airport - Falcon Field", airportDTOs.get(0).getName());
        assertEquals("KAUO", airportDTOs.get(1).getIdent());
        assertEquals("Auburn University Regional Airport", airportDTOs.get(1).getName());
    }

    @Test
    void getIdent() {
        String actual = underTest.getIdent();
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
        List<RunwayDTO> actual = underTest.getRunways();
        assertEquals(runways, actual);
    }

    @Test
    void getWeather() {
        WeatherDTO actual = underTest.getWeather();
        assertEquals(weather, actual);
    }

    @Test
    void builder() {
        RunwayDTO mockRunway = RunwayDTO.builder().build();
        WeatherDTO mockWeather = WeatherDTO.builder().build();
        AirportDTO airport = AirportDTO.builder()
                .ident("KFFC")
                .name("Atlanta Regional Airport - Falcon Field")
                .latitude(33.35725)
                .longitude(-84.57172)
                .runways(List.of(mockRunway))
                .weather(mockWeather)
                .build();
        assertEquals("KFFC", airport.getIdent());
        assertEquals("Atlanta Regional Airport - Falcon Field", airport.getName());
        assertEquals(33.35725, airport.getLatitude());
        assertEquals(-84.57172, airport.getLongitude());
        assertEquals(List.of(mockRunway), airport.getRunways());
        assertEquals(mockWeather, airport.getWeather());
    }

    @Test
    void builder_noArgs() {
        AirportDTO airport = AirportDTO.builder().build();
        assertNull(airport.getIdent());
        assertNull(airport.getName());
        assertNull(airport.getLatitude());
        assertNull(airport.getLongitude());
        assertNull(airport.getRunways());
        assertNull(airport.getWeather());
    }

    @Test
    void no_args_constructor() {
        AirportDTO airport = new AirportDTO();
        assertNull(airport.getIdent());
        assertNull(airport.getName());
        assertNull(airport.getLatitude());
        assertNull(airport.getLongitude());
        assertNull(airport.getRunways());
        assertNull(airport.getWeather());
    }
}
