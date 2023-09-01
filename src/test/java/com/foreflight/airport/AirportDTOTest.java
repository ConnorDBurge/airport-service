package com.foreflight.airport;

import com.foreflight.airport.runway.Runway;
import com.foreflight.airport.runway.RunwayDTO;
import com.foreflight.weather.Weather;
import com.foreflight.weather.WeatherDTO;
import com.foreflight.weather.report.Report;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class AirportDTOTest {

    private AirportDTO underTest;
    @Mock private RunwayDTO runway;
    @Mock private WeatherDTO weather;
    @Mock private Runway runwayEntity;
    @Mock private Report report;

    @BeforeEach
    void setUp() {
        underTest = AirportDTO.builder()
                .ident("KFFC")
                .name("Atlanta Regional Airport - Falcon Field")
                .latitude(33.35725F)
                .longitude(-84.57172F)
                .runways(List.of(runway))
                .weather(weather)
                .build();
    }

    @Test
    void fromEntity() {
        Airport airport = Airport.builder()
                .icao("KFFC")
                .name("Atlanta Regional Airport - Falcon Field")
                .latitude(33.35725F)
                .longitude(-84.57172F)
                .runways(List.of(runwayEntity))
                .weather(Weather.builder()
                        .ident("KFFC")
                        .report(report).build())
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
                .latitude(33.35725F)
                .longitude(-84.57172F)
                .runways(List.of(runwayEntity))
                .weather(Weather.builder()
                        .ident("KFFC")
                        .report(report).build())
                .build();
        Airport kauo = Airport.builder()
                .icao("KAUO")
                .name("Auburn University Regional Airport")
                .latitude(32.61575F)
                .longitude(-85.43400F)
                .runways(List.of(runwayEntity))
                .weather(Weather.builder()
                        .ident("KAUO")
                        .report(report).build())
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
        List<RunwayDTO> actual = underTest.getRunways();
        assertEquals(List.of(runway), actual);
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
                .latitude(33.35725F)
                .longitude(-84.57172F)
                .runways(List.of(mockRunway))
                .weather(mockWeather)
                .build();
        assertEquals("KFFC", airport.getIdent());
        assertEquals("Atlanta Regional Airport - Falcon Field", airport.getName());
        assertEquals(33.35725F, airport.getLatitude());
        assertEquals(-84.57172F, airport.getLongitude());
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
