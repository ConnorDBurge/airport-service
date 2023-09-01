package com.foreflight.airport;

import com.foreflight.airport.runway.Runway;
import com.foreflight.external.AirportAPI;
import com.foreflight.external.WeatherAPI;
import com.foreflight.weather.Weather;
import com.foreflight.weather.report.Report;
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
class AirportServiceTest {

    private AirportService underTest;
    @Mock private AirportAPI mockAirportAPI;
    @Mock private WeatherAPI mockWeatherAPI;

    @BeforeEach
    void setUp() {
        underTest = new AirportService(mockAirportAPI, mockWeatherAPI);
    }

    @Test
    public void testGetAll_One() {
        String idents = "KFFC";

        Weather mockWeather1 = Weather.builder()
                .report(Report.builder().build())
                .build();

        Airport mockAirport1 = Airport.builder()
                .icao("KFFC")
                .runways(List.of(Runway.builder().build()))
                .name("Atlanta Regional Airport - Falcon Field")
                .latitude(33.35725F)
                .longitude(-84.57172F)
                .build();

        when(mockWeatherAPI.findWeather("KFFC")).thenReturn(ResponseEntity.of(Optional.of(mockWeather1)));
        when(mockAirportAPI.findAirport("KFFC")).thenReturn(ResponseEntity.of(Optional.of(mockAirport1)));

        List<AirportDTO> result = underTest.getAll(idents);

        assertEquals(1, result.size());
        assertEquals("KFFC", result.get(0).getIdent());
        assertEquals("Atlanta Regional Airport - Falcon Field", result.get(0).getName());
    }

    @Test
    public void testGetAll_Two() {
        String idents = "KFFC,KAUO";

        Weather mockWeather1 = Weather.builder()
                .report(Report.builder().build())
                .build();

        Airport mockAirport1 = Airport.builder()
                .icao("KFFC")
                .runways(List.of(Runway.builder().build()))
                .name("Atlanta Regional Airport - Falcon Field")
                .latitude(33.35725F)
                .longitude(-84.57172F)
                .build();

        Weather mockWeather2 = Weather.builder()
                .report(Report.builder().build())
                .build();

        Airport mockAirport2 = Airport.builder()
                .icao("KAUO")
                .runways(List.of(Runway.builder().build()))
                .name("Auburn University Regional Airport")
                .latitude(32.61525F)
                .longitude(-85.43400F)
                .build();

        when(mockWeatherAPI.findWeather("KFFC")).thenReturn(ResponseEntity.of(Optional.of(mockWeather1)));
        when(mockAirportAPI.findAirport("KFFC")).thenReturn(ResponseEntity.of(Optional.of(mockAirport1)));
        when(mockWeatherAPI.findWeather("KAUO")).thenReturn(ResponseEntity.of(Optional.of(mockWeather2)));
        when(mockAirportAPI.findAirport("KAUO")).thenReturn(ResponseEntity.of(Optional.of(mockAirport2)));

        List<AirportDTO> result = underTest.getAll(idents);

        assertEquals(2, result.size());
        assertEquals("KFFC", result.get(0).getIdent());
        assertEquals("Atlanta Regional Airport - Falcon Field", result.get(0).getName());
        assertEquals("KAUO", result.get(1).getIdent());
        assertEquals("Auburn University Regional Airport", result.get(1).getName());
    }
}
