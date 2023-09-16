package com.foreflight.weather;

import com.fasterxml.jackson.databind.JsonNode;
import com.foreflight.TestConfig;
import com.foreflight.exception.WeatherNotFoundException;
import com.foreflight.external.WeatherAPI;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Import(TestConfig.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WeatherControllerExceptionsIT {

    @Autowired private WebTestClient webTestClient;
    @Autowired private WeatherAPI weatherAPI;
    private final String WEATHER_URI = "/v1/weather/";

    @Test
    void willThrowWeatherNotFoundException() {
        String ident = "INVALID_AIRPORT";

        when(weatherAPI.findWeather(ident)).thenThrow(new WeatherNotFoundException("Weather data not found for identifier: " + ident));

        webTestClient.get()
                .uri(WEATHER_URI + ident)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isNotFound() // Expecting 404 status due to the WeatherNotFoundException
                .expectBody(JsonNode.class) // Expecting a JSON response
                .value(response -> {
                    verify(weatherAPI, times(1)).findWeather(ident);
                    assertThat(response.get("statusCode").asInt()).isEqualTo(404);
                    assertThat(response.get("message").asText()).isEqualTo("Weather data not found for identifier: " + ident);
                });
    }
}
