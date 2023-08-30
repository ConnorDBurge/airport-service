package com.foreflight.weather.report.forecast;

import com.foreflight.weather.report.forecast.conditions.ForecastCondition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ForecastTest {

    private Forecast underTest;
    @Mock private List<ForecastCondition> conditions;

    @BeforeEach
    void setUp() {
        underTest = Forecast.builder()
                .conditions(conditions)
                .build();
    }

    @Test
    void getConditions() {
        List<ForecastCondition> actual = underTest.getConditions();
        assertEquals(conditions, actual);
    }

    @Test
    void builder() {
        List<ForecastCondition> mockConditions = List.of(ForecastCondition.builder().build());
        Forecast forecast = Forecast.builder()
                .conditions(mockConditions)
                .build();

        assertEquals(mockConditions, forecast.getConditions());
    }

    @Test
    void builder_noArgs() {
        Forecast forecast = Forecast.builder().build();
        assertNull(forecast.getConditions());
    }

    @Test
    void no_args_constructor() {
        Forecast forecast = new Forecast();
        assertNull(forecast.getConditions());
    }
}
