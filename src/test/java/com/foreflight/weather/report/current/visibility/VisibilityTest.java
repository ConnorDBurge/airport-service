package com.foreflight.weather.report.current.visibility;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VisibilityTest {

    private Visibility underTest;

    @BeforeEach
    void setUp() {
        underTest = Visibility.builder()
                .distanceSm(1.0)
                .build();
    }

    @Test
    void getDistanceSm() {
        Double actual = underTest.getDistanceSm();
        assertEquals(1.0, actual);
    }

    @Test
    void builder() {
        Visibility visibility = Visibility.builder()
                .distanceSm(1.0)
                .build();

        assertEquals(1.0, visibility.getDistanceSm());
    }

    @Test
    void builder_noArgs() {
        Visibility visibility = Visibility.builder().build();
        assertNull(visibility.getDistanceSm());
    }

    @Test
    void no_args_constructor() {
        Visibility visibility = new Visibility();
        assertNull(visibility.getDistanceSm());
    }
}
