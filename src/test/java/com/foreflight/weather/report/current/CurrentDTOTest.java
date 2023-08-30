package com.foreflight.weather.report.current;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CurrentDTOTest {

    private CurrentDTO underTest;

    @BeforeEach
    void setUp() {
        underTest = CurrentDTO.builder()
                .temperature(1.0)
                .relativeHumidity(1.0)
                .cloudCoverage("cloudCoverage")
                .visibility(10.0)
                .windSpeed(1.0)
                .windDirection(250)
                .build();
    }

    @Test
    void fromEntity() {
        Current current = Current.builder()
                .temperature(1.0)
                .relativeHumidity(1.0)
                .cloudLayers(null)
                .visibility(null)
                .wind(null)
                .build();

        CurrentDTO actual = CurrentDTO.fromEntity(current);
        assertEquals(1.0, actual.getTemperature());
        assertEquals(1.0, actual.getRelativeHumidity());
        assertNull(actual.getCloudCoverage());
        assertNull(actual.getVisibility());
        assertNull(actual.getWindSpeed());
        assertNull(actual.getWindDirection());
    }

    @Test
    void getTemperature() {
        Double actual = underTest.getTemperature();
        assertEquals(1.0, actual);
    }

    @Test
    void getRelativeHumidity() {
        Double actual = underTest.getRelativeHumidity();
        assertEquals(1.0, actual);
    }

    @Test
    void getCloudCoverage() {
        String actual = underTest.getCloudCoverage();
        assertEquals("cloudCoverage", actual);
    }

    @Test
    void getVisibility() {
        Double actual = underTest.getVisibility();
        assertEquals(10.0, actual);
    }

    @Test
    void getWindSpeed() {
        Double actual = underTest.getWindSpeed();
        assertEquals(1.0, actual);
    }

    @Test
    void getWindDirection() {
        Integer actual = underTest.getWindDirection();
        assertEquals(250, actual);
    }

    @Test
    void builder() {
        CurrentDTO currentDTO = CurrentDTO.builder()
                .temperature(1.0)
                .relativeHumidity(1.0)
                .cloudCoverage("cloudCoverage")
                .visibility(10.0)
                .windSpeed(1.0)
                .windDirection(250)
                .build();

        assertEquals(1.0, currentDTO.getTemperature());
        assertEquals(1.0, currentDTO.getRelativeHumidity());
        assertEquals("cloudCoverage", currentDTO.getCloudCoverage());
        assertEquals(10.0, currentDTO.getVisibility());
        assertEquals(1.0, currentDTO.getWindSpeed());
        assertEquals(250, currentDTO.getWindDirection());
    }

    @Test
    void builder_noArgs() {
        CurrentDTO currentDTO = CurrentDTO.builder().build();
        assertNull(currentDTO.getTemperature());
        assertNull(currentDTO.getRelativeHumidity());
        assertNull(currentDTO.getCloudCoverage());
        assertNull(currentDTO.getVisibility());
        assertNull(currentDTO.getWindSpeed());
        assertNull(currentDTO.getWindDirection());
    }

    @Test
    void no_args_constructor() {
        CurrentDTO currentDTO = new CurrentDTO();
        assertNull(currentDTO.getTemperature());
        assertNull(currentDTO.getRelativeHumidity());
        assertNull(currentDTO.getCloudCoverage());
        assertNull(currentDTO.getVisibility());
        assertNull(currentDTO.getWindSpeed());
        assertNull(currentDTO.getWindDirection());
    }
}
