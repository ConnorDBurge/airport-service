package com.foreflight.weather;

import com.foreflight.TestConfig;
import com.foreflight.external.WeatherAPI;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Import(TestConfig.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WeatherControllerValidTest {

    @Autowired private WebTestClient webTestClient;
    @Autowired private WeatherAPI weatherAPI;
    private final String WEATHER_URI = "/v1/weather/";

    @Test
    void canGetWeather() {
        String ident = "KFFC";

        Weather mockedWeather = Weather.builder()
                .ident("KFFC")
                .build();

        ResponseEntity<Weather> weatherResponse = ResponseEntity.ok(mockedWeather);
        when(weatherAPI.findWeather(ident)).thenReturn(weatherResponse);

        List<WeatherDTO> weather = webTestClient.get()
                .uri(WEATHER_URI + ident)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<WeatherDTO>() {
                })
                .returnResult()
                .getResponseBody();

        verify(weatherAPI).findWeather(ident);

        assertThat(weather).hasSize(1);
        WeatherDTO returnedWeather = weather.get(0);
        assertThat(returnedWeather.getIdent()).isEqualTo(ident);
    }

    @Test
    void canGetWeatherForMultipleAirports() {
        String idents = "KFFC,KATL";

        Weather mockedWeather1 = Weather.builder()
                .ident("KFFC")
                .build();

        Weather mockedWeather2 = Weather.builder()
                .ident("KATL")
                .build();

        ResponseEntity<Weather> weatherResponse1 = ResponseEntity.ok(mockedWeather1);
        ResponseEntity<Weather> weatherResponse2 = ResponseEntity.ok(mockedWeather2);

        when(weatherAPI.findWeather("KFFC")).thenReturn(weatherResponse1);
        when(weatherAPI.findWeather("KATL")).thenReturn(weatherResponse2);

        List<WeatherDTO> weather = webTestClient.get()
                .uri(WEATHER_URI + idents)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<WeatherDTO>() {
                })
                .returnResult()
                .getResponseBody();

        verify(weatherAPI).findWeather("KFFC");
        verify(weatherAPI).findWeather("KATL");

        assertThat(weather).hasSize(2);
        WeatherDTO returnedWeather1 = weather.get(0);
        assertThat(returnedWeather1.getIdent()).isEqualTo("KFFC");
        WeatherDTO returnedWeather2 = weather.get(1);
        assertThat(returnedWeather2.getIdent()).isEqualTo("KATL");
    }
}
