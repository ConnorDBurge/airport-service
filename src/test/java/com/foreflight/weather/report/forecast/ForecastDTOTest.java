package com.foreflight.weather.report.forecast;

import com.foreflight.weather.report.current.wind.Wind;
import com.foreflight.weather.report.forecast.conditions.ForecastCondition;
import com.foreflight.weather.report.forecast.conditions.ForecastConditionDTO;
import com.foreflight.weather.report.forecast.period.Period;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ForecastDTOTest {

    private ForecastDTO underTest;
    @Mock private List<ForecastConditionDTO> conditions;

    @BeforeEach
    void setUp() {
        underTest = ForecastDTO.builder()
                .conditions(conditions)
                .build();
    }

    @Test
    void fromEntity_Dto_Returns_Next_Two_Forecast_Conditions() {
        Forecast mockForecast = Forecast.builder()
                .conditions(List.of(ForecastCondition.builder()
                                .period(Period.builder()
                                        .dateStart("2023-08-30T18:00:00+0000")
                                        .build())
                                .temperature(1.0)
                                .text("text1")
                                .wind(Wind.builder().build())
                                .build(),
                        ForecastCondition.builder() // Included in DTO
                                .period(Period.builder()
                                        .dateStart("2023-08-30T20:00:00+0000")
                                        .build())
                                .temperature(2.0)
                                .text("text2")
                                .wind(Wind.builder().build())
                                .build(),
                        ForecastCondition.builder() // Included in DTO
                                .period(Period.builder()
                                        .dateStart("2023-08-30T22:00:00+0000")
                                        .build())
                                .temperature(3.0)
                                .text("text3")
                                .wind(Wind.builder().build())
                                .build()
                )).build();

        ForecastDTO dto = ForecastDTO.fromEntity(mockForecast);

        assertNotNull(dto.getConditions());
        assertEquals(2, dto.getConditions().size());
        assertThat(dto.getConditions().get(0).getTemperature()).isEqualTo(2.0);
        assertThat(dto.getConditions().get(0).getText()).isEqualTo("text2");
        assertThat(dto.getConditions().get(1).getTemperature()).isEqualTo(3.0);
        assertThat(dto.getConditions().get(1).getText()).isEqualTo("text3");
    }

    @Test
    void fromEntity_Dto_Returns_One_Forecast_Condition() {
        Forecast mockForecast = Forecast.builder()
                .conditions(List.of(ForecastCondition.builder()
                                .period(Period.builder()
                                        .dateStart("2023-08-30T18:00:00+0000")
                                        .build())
                                .temperature(1.0)
                                .text("text1")
                                .wind(Wind.builder().build())
                                .build(),
                        ForecastCondition.builder() // Included in DTO
                                .period(Period.builder()
                                        .dateStart("2023-08-30T20:00:00+0000")
                                        .build())
                                .temperature(2.0)
                                .text("text2")
                                .wind(Wind.builder().build())
                                .build()
                )).build();

        ForecastDTO dto = ForecastDTO.fromEntity(mockForecast);

        assertNotNull(dto.getConditions());
        assertEquals(1, dto.getConditions().size());
        assertThat(dto.getConditions().get(0).getTemperature()).isEqualTo(2.0);
        assertThat(dto.getConditions().get(0).getText()).isEqualTo("text2");
    }

    @Test
    void getConditions() {
        List<ForecastConditionDTO> actual = underTest.getConditions();
        assertEquals(conditions, actual);
    }

    @Test
    void builder() {
        ForecastConditionDTO mockCondition = ForecastConditionDTO.builder().build();
        ForecastDTO forecastDTO = ForecastDTO.builder()
                .conditions(List.of(mockCondition))
                .build();

        assertEquals(List.of(mockCondition), forecastDTO.getConditions());
    }

    @Test
    void builder_noArgs() {
        ForecastDTO forecastDTO = ForecastDTO.builder().build();
        assertNull(forecastDTO.getConditions());
    }

    @Test
    void noArgsConstructor() {
        ForecastDTO forecastDTO = new ForecastDTO();
        assertNull(forecastDTO.getConditions());
    }
}
