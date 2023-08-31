package com.foreflight.airport;

import com.foreflight.config.WebTestClientConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import(WebTestClientConfig.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AirportControllerValidTest {

    @Autowired
    private WebTestClient webTestClient;
    private final String AIRPORT_URI = "/v1/airports/";

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        this.webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:" + port).build();
    }

    @Test
    void canGetAirport() {
        String ident = "KFFC";

        List<AirportDTO> airports = webTestClient.get()
                .uri(AIRPORT_URI + ident)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<AirportDTO>() {
                })
                .returnResult()
                .getResponseBody();

        assertThat(airports).hasSize(1);
        AirportDTO returnedAirport = airports.get(0);
        assertThat(returnedAirport.getIdent()).isEqualTo(ident);
        assertThat(returnedAirport.getName()).isEqualTo("Atlanta Regional Falcon Field");
    }

    @Test
    void canGetMultipleAirports() {
        String idents = "KFFC,KATL";

        List<AirportDTO> airports = webTestClient.get()
                .uri(AIRPORT_URI + idents)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<AirportDTO>() {
                })
                .returnResult()
                .getResponseBody();

        assertThat(airports).hasSize(2);
        AirportDTO kffc = airports.get(0);
        assertThat(kffc.getIdent()).isEqualTo("KFFC");
        assertThat(kffc.getName()).isEqualTo("Atlanta Regional Falcon Field");
        AirportDTO katl = airports.get(1);
        assertThat(katl.getIdent()).isEqualTo("KATL");
        assertThat(katl.getName()).isEqualTo("Hartsfield - Jackson Atlanta International");
    }
}
