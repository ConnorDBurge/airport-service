package com.foreflight.weather;

import com.foreflight.config.WeatherAPI;
import com.foreflight.weather.report.Report;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {

    private WeatherService underTest;
    @Mock private WeatherAPI weatherAPI;

    @BeforeEach
    void setUp() {
        underTest = new WeatherService(weatherAPI);
    }

    @Test
    void testGetAll_one() {
        String idents = "KFFC";

        Weather mockWeather1 = Weather.builder()
                .ident("KFFC")
                .report(Report.builder().build())
                .build();

        when(weatherAPI.findWeather("KFFC")).thenReturn(ResponseEntity.of(Optional.of(mockWeather1)));

        List<WeatherDTO> result = underTest.getAll(idents);

        assertEquals(1, result.size());
        assertEquals("KFFC", result.get(0).getIdent());
    }

    @Test
    void testGetAll_two() {
        String idents = "KFFC,KATL";

        Weather mockWeather1 = Weather.builder()
                .ident("KFFC")
                .report(Report.builder().build())
                .build();

        Weather mockWeather2 = Weather.builder()
                .ident("KATL")
                .report(Report.builder().build())
                .build();

        when(weatherAPI.findWeather("KFFC")).thenReturn(ResponseEntity.of(Optional.of(mockWeather1)));
        when(weatherAPI.findWeather("KATL")).thenReturn(ResponseEntity.of(Optional.of(mockWeather2)));

        List<WeatherDTO> result = underTest.getAll(idents);

        assertEquals(2, result.size());
        assertEquals("KFFC", result.get(0).getIdent());
        assertEquals("KATL", result.get(1).getIdent());
    }
}