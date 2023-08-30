package com.foreflight.weather.report.forecast.conditions;

import com.foreflight.weather.report.current.wind.Wind;
import com.foreflight.weather.report.forecast.period.Period;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ForecastConditionTest {

    private ForecastCondition underTest;
    @Mock
    private Wind wind;
    @Mock
    private Period period;

    @BeforeEach
    void setUp() {
        underTest = ForecastCondition.builder()
                .text("text")
                .wind(wind)
                .period(period)
                .temperature(1.0)
                .build();
    }

    @Test
    void getText() {
        String actual = underTest.getText();
        assertEquals("text", actual);
    }

    @Test
    void getWind() {
        Wind actual = underTest.getWind();
        assertEquals(wind, actual);
    }

    @Test
    void getPeriod() {
        Period actual = underTest.getPeriod();
        assertEquals(period, actual);
    }

    @Test
    void getTemperature() {
        Double actual = underTest.getTemperature();
        assertEquals(1.0, actual);
    }

    @Test
    void builder() {
        ForecastCondition forecastCondition = ForecastCondition.builder()
                .text("text")
                .wind(wind)
                .period(period)
                .temperature(1.0)
                .build();

        assertEquals("text", forecastCondition.getText());
        assertEquals(wind, forecastCondition.getWind());
        assertEquals(period, forecastCondition.getPeriod());
        assertEquals(1.0, forecastCondition.getTemperature());
    }

    @Test
    void builder_noArgs() {
        ForecastCondition forecastCondition = ForecastCondition.builder().build();
        assertNull(forecastCondition.getText());
        assertNull(forecastCondition.getWind());
        assertNull(forecastCondition.getPeriod());
        assertNull(forecastCondition.getTemperature());
    }

    @Test
    void no_args_constructor() {
        ForecastCondition forecastCondition = new ForecastCondition();
        assertNull(forecastCondition.getText());
        assertNull(forecastCondition.getWind());
        assertNull(forecastCondition.getPeriod());
        assertNull(forecastCondition.getTemperature());
    }
}
