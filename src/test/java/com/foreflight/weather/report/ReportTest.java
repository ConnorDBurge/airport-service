package com.foreflight.weather.report;

import com.foreflight.weather.report.current.Current;
import com.foreflight.weather.report.forecast.Forecast;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ReportTest {

    private Report underTest;
    @Mock private Current current;
    @Mock private Forecast forecast;

    @BeforeEach
    void setUp() {
        underTest = Report.builder()
                .current(current)
                .forecast(forecast)
                .build();
    }

    @Test
    void getCurrent() {
        Current actual = underTest.getCurrent();
        assertEquals(current, actual);
    }

    @Test
    void getForecast() {
        Forecast actual = underTest.getForecast();
        assertEquals(forecast, actual);
    }

    @Test
    void builder() {
        Current mockCurrent = Current.builder().build();
        Forecast mockForecast = Forecast.builder().build();
        Report report = Report.builder()
                .current(mockCurrent)
                .forecast(mockForecast)
                .build();
        assertEquals(mockCurrent, report.getCurrent());
        assertEquals(mockForecast, report.getForecast());
    }

    @Test
    void builder_noArgs() {
        Report report = Report.builder().build();
        assertNull(report.getCurrent());
        assertNull(report.getForecast());
    }

    @Test
    void no_args_constructor() {
        Report report = new Report();
        assertNull(report.getCurrent());
        assertNull(report.getForecast());
    }
}
