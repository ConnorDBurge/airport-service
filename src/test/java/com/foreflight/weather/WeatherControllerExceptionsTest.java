package com.foreflight.weather;

import com.fasterxml.jackson.databind.JsonNode;
import com.foreflight.config.AirportAPI;
import com.foreflight.config.WeatherAPI;
import com.foreflight.exception.WeatherNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WeatherControllerExceptionsTest {

    @Autowired
    private WebTestClient webTestClient;
    private final String WEATHER_URI = "/v1/weather/";
    @MockBean private AirportAPI airportAPI; // External API
    @MockBean private WeatherAPI weatherAPI; // External API

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
