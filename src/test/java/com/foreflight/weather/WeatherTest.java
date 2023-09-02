package com.foreflight.weather;

import com.foreflight.weather.report.Report;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class WeatherTest {

    private Weather underTest;
    private final Report report = Report.builder().build();

    @BeforeEach
    void setUp() {
        underTest = Weather.builder()
                .report(report)
                .build();
    }

    @Test
    void getReport() {
        Report actual = underTest.getReport();
        assertEquals(report, actual);
    }

    @Test
    public void builder() {
        Report mockReport = Report.builder().build();
        Weather weather = Weather.builder()
                .report(mockReport)
                .build();
        assertEquals(mockReport, weather.getReport());
    }

    @Test
    public void builder_noArgs() {
        Weather weather = Weather.builder().build();
        assertNull(weather.getReport());
    }

    @Test
    public void no_args_constructor() {
        Weather weather = new Weather();
        assertNull(weather.getReport());
    }
}
