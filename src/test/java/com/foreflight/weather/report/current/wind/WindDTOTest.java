package com.foreflight.weather.report.current.wind;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WindDTOTest {

    private WindDTO underTest;

    @BeforeEach
    void setUp() {
        underTest = WindDTO.builder()
                .speedKts(1.0)
                .direction(250)
                .from(250)
                .variable(false)
                .build();
    }

    @Test
    void fromEntity() {
        Wind wind = Wind.builder()
                .speedKts(1.0)
                .direction(250)
                .from(250)
                .variable(false)
                .build();

        WindDTO actual = WindDTO.fromEntity(wind);
        assertEquals(1.0, actual.getSpeedKts());
        assertEquals(250, actual.getDirection());
        assertEquals(250, actual.getFrom());
        assertFalse(actual.getVariable());
    }

    @Test
    void getSpeedKts() {
        Double actual = underTest.getSpeedKts();
        assertEquals(1.0, actual);
    }

    @Test
    void getDirection() {
        Integer actual = underTest.getDirection();
        assertEquals(250, actual);
    }

    @Test
    void getFrom() {
        Integer actual = underTest.getFrom();
        assertEquals(250, actual);
    }

    @Test
    void getVariable() {
        Boolean actual = underTest.getVariable();
        assertFalse(actual);
    }

    @Test
    void builder() {
        WindDTO actual = WindDTO.builder()
                .speedKts(1.0)
                .direction(250)
                .from(250)
                .variable(false)
                .build();
        assertEquals(1.0, actual.getSpeedKts());
        assertEquals(250, actual.getDirection());
        assertEquals(250, actual.getFrom());
        assertFalse(actual.getVariable());
    }

    @Test
    void builder_noArgs() {
        WindDTO actual = WindDTO.builder().build();
        assertNull(actual.getSpeedKts());
        assertNull(actual.getDirection());
        assertNull(actual.getFrom());
        assertNull(actual.getVariable());
    }

    @Test
    void noArgsConstructor() {
        WindDTO actual = new WindDTO();
        assertNull(actual.getSpeedKts());
        assertNull(actual.getDirection());
        assertNull(actual.getFrom());
        assertNull(actual.getVariable());
    }
}
