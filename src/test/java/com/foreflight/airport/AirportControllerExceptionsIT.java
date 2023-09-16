package com.foreflight.airport;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AirportControllerExceptionsIT {

    @Autowired private WebTestClient webTestClient;
    private final String AIRPORT_URI = "/v1/airports/";

    @Test
    void willThrowAirportNotFoundException() {
        String ident = "INVALID_AIRPORT";

        webTestClient.get()
                .uri(AIRPORT_URI + ident)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isNotFound() // Expecting 404 status due to the AirportNotFoundException
                .expectBody(JsonNode.class) // Expecting a JSON response
                .value(response -> {
                    assertThat(response.get("statusCode").asInt()).isEqualTo(404);
                    assertThat(response.get("message").asText()).isEqualTo("Airport data not found for identifier: " + ident);
                });
    }
}
