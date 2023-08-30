package com.foreflight.weather.report.current.wind;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WindTest {

    private Wind underTest;

    @BeforeEach
    void setUp() {
        underTest = Wind.builder()
                .speedKts(1.0)
                .direction(250)
                .from(130)
                .variable(true)
                .build();
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
        assertEquals(130, actual);
    }

    @Test
    void getVariable() {
        Boolean actual = underTest.getVariable();
        assertTrue(actual);
    }

    @Test
    void builder() {
        Wind wind = Wind.builder()
                .speedKts(1.0)
                .direction(250)
                .from(130)
                .variable(true)
                .build();

        assertEquals(1.0, wind.getSpeedKts());
        assertEquals(250, wind.getDirection());
        assertEquals(130, wind.getFrom());
        assertTrue(wind.getVariable());
    }

    @Test
    void builder_noArgs() {
        Wind wind = Wind.builder().build();
        assertNull(wind.getSpeedKts());
        assertNull(wind.getDirection());
        assertNull(wind.getFrom());
        assertNull(wind.getVariable());
    }

    @Test
    void no_args_constructor() {
        Wind wind = new Wind();
        assertNull(wind.getSpeedKts());
        assertNull(wind.getDirection());
        assertNull(wind.getFrom());
        assertNull(wind.getVariable());
    }
}
