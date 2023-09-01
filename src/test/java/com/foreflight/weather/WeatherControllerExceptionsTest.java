package com.foreflight.weather;

import com.fasterxml.jackson.databind.JsonNode;
import com.foreflight.config.WebTestClientConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@Import(WebTestClientConfig.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WeatherControllerExceptionsTest {

    @Autowired
    private WebTestClient webTestClient;
    private final String WEATHER_URI = "/v1/weather/";

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        this.webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:" + port).build();
    }

    @Test
    void willThrowWeatherNotFoundException() {
        String ident = "INVALID_AIRPORT";

        webTestClient.get()
                .uri(WEATHER_URI + ident)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isNotFound() // Expecting 404 status due to the WeatherNotFoundException
                .expectBody(JsonNode.class) // Expecting a JSON response
                .value(response -> {
                    assertThat(response.get("statusCode").asInt()).isEqualTo(404);
                    assertThat(response.get("message").asText()).isEqualTo("Weather data not found for identifier: " + ident);
                });
    }
}
