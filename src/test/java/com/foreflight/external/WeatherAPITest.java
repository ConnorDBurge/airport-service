package com.foreflight.external;

import com.foreflight.exception.WeatherNotFoundException;
import com.foreflight.weather.Weather;
import com.foreflight.weather.report.Report;
import com.foreflight.weather.report.current.Current;
import com.foreflight.weather.report.current.cloudlayer.CloudLayer;
import com.foreflight.weather.report.current.visibility.Visibility;
import com.foreflight.weather.report.current.wind.Wind;
import com.foreflight.weather.report.forecast.Forecast;
import com.foreflight.weather.report.forecast.conditions.ForecastCondition;
import com.foreflight.weather.report.forecast.period.Period;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherAPITest {

    @Mock
    private RestTemplate restTemplate;
    private WeatherAPI underTest;

    @BeforeEach
    void setUp() {
        underTest = new WeatherAPI(restTemplate, "apiUrl");
    }

    @Test
    void findWeather() {
        ResponseEntity<Weather> expectedResponse = ResponseEntity.ok(Weather.builder()
                .report(Report.builder()
                        .current(Current.builder()
                                .ident("KFFC")
                                .temperature(1.0)
                                .relativeHumidity(2.0)
                                .visibility(Visibility.builder()
                                        .distanceSm(3.0)
                                        .build())
                                .cloudLayers(List.of(CloudLayer.builder()
                                        .coverage("coverage")
                                        .build()))
                                .wind(Wind.builder()
                                        .speedKts(2.0)
                                        .direction(3)
                                        .build())
                                .build())
                        .forecast(Forecast.builder()
                                .conditions(List.of(
                                        ForecastCondition.builder()
                                                .text("FM301500 05012G20KT P6SM VCSH SCT007 BKN012")
                                                .period(Period.builder()
                                                        .dateStart("2021-01-01T15:00:00Z")
                                                        .build())
                                                .temperature(1.0)
                                                .wind(Wind.builder()
                                                        .speedKts(2.0)
                                                        .direction(3)
                                                        .build())
                                                .build()
                                ))
                                .build())
                        .build())
                .build());

        when(restTemplate.exchange(
                "apiUrl" + "KFFC",
                HttpMethod.GET,
                underTest.getHttpEntity(), Weather.class)).thenReturn(expectedResponse);

        ResponseEntity<Weather> actualResponse = underTest.findWeather("KFFC");

        verify(restTemplate).exchange(
                "apiUrl" + "KFFC",
                HttpMethod.GET,
                underTest.getHttpEntity(), Weather.class);

        assertThat(actualResponse).isEqualTo(expectedResponse);

        assertThat(Objects.requireNonNull(actualResponse.getBody()).getReport()
                .getCurrent().getTemperature())
                .isEqualTo(1.0);
        assertThat(Objects.requireNonNull(actualResponse.getBody()).getReport()
                .getCurrent().getTemperature())
                .isNotEqualTo(2.0);

        assertThat(Objects.requireNonNull(actualResponse.getBody()).getReport()
                .getForecast().getConditions().get(0).getText())
                .isEqualTo("FM301500 05012G20KT P6SM VCSH SCT007 BKN012");
        assertThat(Objects.requireNonNull(actualResponse.getBody()).getReport()
                .getForecast().getConditions().get(0).getText())
                .isNotEqualTo("FM301500 05012G20KT P6SM VCSH SCT007 BKN013");
    }

    @Test
    void willThrowWeatherNotFoundException() {
        when(restTemplate.exchange(
                "apiUrl" + "KFFC",
                HttpMethod.GET,
                underTest.getHttpEntity(), Weather.class))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        try {
            underTest.findWeather("KFFC");
        } catch (WeatherNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("Weather data not found for identifier: KFFC");
        }
    }

    @Test
    void getHttpEntity() {
        assertThat(underTest.getHttpEntity()).isNotNull();
    }

    @Test
    void getRestTemplate() {
        RestTemplate actual = underTest.getRestTemplate();
        assertThat(actual).isEqualTo(restTemplate);
    }

    @Test
    void getApiUrl() {
        String actual = underTest.getApiUrl();
        assertThat(actual).isEqualTo("apiUrl");
    }
}
