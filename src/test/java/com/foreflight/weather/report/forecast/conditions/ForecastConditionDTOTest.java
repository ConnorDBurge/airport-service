package com.foreflight.weather.report.forecast.conditions;

import com.foreflight.weather.report.current.wind.Wind;
import com.foreflight.weather.report.current.wind.WindDTO;
import com.foreflight.weather.report.forecast.period.Period;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ForecastConditionDTOTest {

    private ForecastConditionDTO underTest;
    private WindDTO wind;

    @BeforeEach
    void setUp() {
        wind = WindDTO.builder().build();
        underTest = ForecastConditionDTO.builder()
                .text("text")
                .wind(wind)
                .dateStart("2023-08-30T18:00:00+0000")
                .temperature(1.0)
                .build();
    }

    @Test
    void fromEntity() {
        Wind wind = Wind.builder().build();
        ForecastCondition mockForecastCondition = ForecastCondition.builder()
                .text("text")
                .wind(wind)
                .period(Period.builder()
                        .dateStart("2023-08-30T18:00:00+0000")
                        .build())
                .temperature(1.0)
                .build();

        ForecastConditionDTO dto = ForecastConditionDTO.fromEntity(mockForecastCondition);

        assertEquals(mockForecastCondition.getText(), dto.getText());
        assertEquals("18:00", dto.getDateStart());
        assertEquals(mockForecastCondition.getTemperature(), dto.getTemperature());
    }

    @Test
    void fromEntities() {
        List<ForecastCondition> mockForecastConditions = List.of(
                ForecastCondition.builder()
                        .text("text1")
                        .wind(Wind.builder().build())
                        .period(Period.builder()
                                .dateStart("2023-08-30T18:00:00+0000")
                                .build())
                        .temperature(1.0)
                        .build(),
                ForecastCondition.builder()
                        .text("text2")
                        .wind(Wind.builder().build())
                        .period(Period.builder()
                                .dateStart("2023-08-30T20:00:00+0000")
                                .build())
                        .temperature(1.0)
                        .build(),
                ForecastCondition.builder()
                        .text("text3")
                        .wind(Wind.builder().build())
                        .period(Period.builder()
                                .dateStart("2023-08-30T22:00:00+0000")
                                .build())
                        .temperature(1.0)
                        .build()
        );

        List<ForecastConditionDTO> dtos = ForecastConditionDTO.fromEntities(mockForecastConditions);

        assertEquals(2, dtos.size());
        assertEquals("20:00", dtos.get(0).getDateStart());
        assertEquals("22:00", dtos.get(1).getDateStart());
    }

    @Test
    void getText() {
        String actual = underTest.getText();
        assertEquals("text", actual);
    }

    @Test
    void getWind() {
        WindDTO actual = underTest.getWind();
        assertEquals(wind, actual);
    }

    @Test
    void getDateStart() {
        String actual = underTest.getDateStart();
        assertEquals("2023-08-30T18:00:00+0000", actual);
    }

    @Test
    void getTemperature() {
        Double actual = underTest.getTemperature();
        assertEquals(1.0, actual);
    }

    @Test
    void builder() {
        ForecastConditionDTO forecastCondition = ForecastConditionDTO.builder()
                .text("text")
                .wind(WindDTO.builder().build())
                .dateStart("2023-08-30T18:00:00+0000")
                .temperature(1.0)
                .build();

        assertEquals("text", forecastCondition.getText());
        assertEquals("2023-08-30T18:00:00+0000", forecastCondition.getDateStart());
        assertEquals(1.0, forecastCondition.getTemperature());
    }

    @Test
    void builder_noArgs() {
        ForecastConditionDTO forecastCondition = ForecastConditionDTO.builder().build();
        assertNull(forecastCondition.getText());
        assertNull(forecastCondition.getWind());
        assertNull(forecastCondition.getDateStart());
        assertNull(forecastCondition.getTemperature());
    }

    @Test
    void noArgsConstructor() {
        ForecastConditionDTO forecastCondition = new ForecastConditionDTO();
        assertNull(forecastCondition.getText());
        assertNull(forecastCondition.getWind());
        assertNull(forecastCondition.getDateStart());
        assertNull(forecastCondition.getTemperature());
    }
}
