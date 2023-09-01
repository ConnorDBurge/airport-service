package com.foreflight.weather;

import com.foreflight.weather.report.Report;
import com.foreflight.weather.report.current.Current;
import com.foreflight.weather.report.current.cloudlayer.CloudLayer;
import com.foreflight.weather.report.current.visibility.Visibility;
import com.foreflight.weather.report.current.wind.Wind;
import com.foreflight.weather.report.forecast.Forecast;
import com.foreflight.weather.report.forecast.conditions.ForecastCondition;
import com.foreflight.weather.report.forecast.period.Period;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class WeatherDTOTest {

    @Test
    void fromEntity_WithValidWeather_ReturnsExpectedDTO() {
        Weather weather = Weather.builder()
                .ident("KFFC")
                .report(Report.builder()
                        .current(Current.builder()
                                .temperature(1.0)
                                .ident("KFFC")
                                .relativeHumidity(2.0)
                                .cloudLayers(List.of(CloudLayer.builder()
                                        .coverage("coverage")
                                        .build()))
                                .visibility(Visibility.builder().distanceSm(3.0).build())
                                .wind(Wind.builder()
                                        .speedKts(2.0)
                                        .direction(3)
                                        .build())
                                .build())
                        .forecast(Forecast.builder()
                                .ident("KATL")
                                .conditions(List.of(ForecastCondition.builder()
                                        .wind(Wind.builder()
                                                .speedKts(2.0)
                                                .direction(3)
                                                .build())
                                        .text("text")
                                        .period(Period.builder()
                                                .dateStart("dateStart")
                                                .dateEnd("dateEnd")
                                                .build())
                                        .temperature(1.0)

                                        .build()))
                                .build())
                        .build())
                .build();

        WeatherDTO dto = WeatherDTO.fromEntity(weather);

        assertThat(dto.getCurrent().getRelativeHumidity()).isEqualTo(weather.getReport().getCurrent().getRelativeHumidity());
        assertThat(dto.getCurrent().getTemperature()).isEqualTo(weather.getReport().getCurrent().getTemperature());
        assertThat(dto.getCurrent().getCloudCoverage()).isEqualTo(weather.getReport().getCurrent().getCloudLayers().get(0).getCoverage());
        assertThat(dto.getCurrent().getVisibility()).isEqualTo(weather.getReport().getCurrent().getVisibility().getDistanceSm());
        assertThat(dto.getCurrent().getWindSpeed()).isEqualTo(weather.getReport().getCurrent().getWind().getSpeedKts());
        assertThat(dto.getCurrent().getWindDirection()).isEqualTo(weather.getReport().getCurrent().getWind().getDirection());
    }

    @Test
    void fromEntity_WithNullCurrent_ReturnsDTOWithNullCurrent() {
        Weather weather = Weather.builder()
                .ident("KFFC")
                .report(Report.builder()
                        .forecast(Forecast.builder()
                                .ident("KATL")
                                .build())
                        .build())
                .build();

        WeatherDTO dto = WeatherDTO.fromEntity(weather);

        assertNull(dto.getCurrent());
        assertNotNull(dto.getForecast());
    }

    @Test
    void fromEntity_WithNullForecast_ReturnsDTOWithNullForecast() {
        Weather weather = Weather.builder()
                .ident("KFFC")
                .report(Report.builder()
                        .current(Current.builder().build())
                        .build())
                .build();

        WeatherDTO dto = WeatherDTO.fromEntity(weather);

        assertNotNull(dto.getCurrent());
        assertNull(dto.getForecast());
    }

    @Test
    void fromEntity_WithNullWeatherReport_ReturnsDTOWithNullCurrentAndForecast() {
        Weather weather = Weather.builder()
                .ident("KFFC")
                .report(Report.builder().build())
                .build();

        WeatherDTO dto = WeatherDTO.fromEntity(weather);

        assertNull(dto.getCurrent());
        assertNull(dto.getForecast());
    }

    @Test
    void noArgsConstructor() {
        WeatherDTO dto = new WeatherDTO();

        assertNull(dto.getCurrent());
        assertNull(dto.getForecast());
    }
}
