package com.foreflight.weather;

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
class WeatherControllerValidTest {

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
    void canGetWeather() {
        String ident = "KFFC";

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

        assertThat(weather).hasSize(1);
        WeatherDTO returnedWeather = weather.get(0);
        assertThat(returnedWeather.getIdent()).isEqualTo(ident);
    }

    @Test
    void canGetWeatherForMultipleAirports() {
        String idents = "KFFC,KATL";

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

        assertThat(weather).hasSize(2);
        WeatherDTO returnedWeather1 = weather.get(0);
        assertThat(returnedWeather1.getIdent()).isEqualTo("KFFC");
        WeatherDTO returnedWeather2 = weather.get(1);
        assertThat(returnedWeather2.getIdent()).isEqualTo("KATL");
    }
}
