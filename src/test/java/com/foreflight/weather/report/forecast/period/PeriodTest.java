package com.foreflight.weather.report.forecast.period;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PeriodTest {

    private Period underTest;

    @BeforeEach
    void setUp() {
        underTest = Period.builder()
                .dateStart("2023-08-30T18:00:00+0000")
                .dateEnd("2023-08-30T20:00:00+0000")
                .build();
    }

    @Test
    void getDateStart() {
        String actual = underTest.getDateStart();
        assertEquals("2023-08-30T18:00:00+0000", actual);
    }

    @Test
    void getDateEnd() {
        String actual = underTest.getDateEnd();
        assertEquals("2023-08-30T20:00:00+0000", actual);
    }

    @Test
    void builder() {
        Period period = Period.builder()
                .dateStart("2023-08-30T18:00:00+0000")
                .dateEnd("2023-08-30T20:00:00+0000")
                .build();

        assertEquals("2023-08-30T18:00:00+0000", period.getDateStart());
        assertEquals("2023-08-30T20:00:00+0000", period.getDateEnd());
    }

    @Test
    void builder_noArgs() {
        Period period = Period.builder().build();
        assertNull(period.getDateStart());
        assertNull(period.getDateEnd());
    }

    @Test
    void no_args_constructor() {
        Period period = new Period();
        assertNull(period.getDateStart());
        assertNull(period.getDateEnd());
    }
}
