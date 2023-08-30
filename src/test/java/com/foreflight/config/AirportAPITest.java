package com.foreflight.config;

import com.foreflight.airport.Airport;
import com.foreflight.airport.runway.Runway;
import com.foreflight.weather.Weather;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AirportAPITest {

    @Mock
    private RestTemplate restTemplate;
    private AirportAPI underTest;

    @BeforeEach
    void setUp() {
        underTest = new AirportAPI(restTemplate, "username", "password", "apiUrl");
    }

    @Test
    void findAirport() {
        ResponseEntity<Airport> expectedResponse = ResponseEntity.ok(
                new Airport(
                        "KFFC",
                        "Atlanta Regional Airport - Falcon Field",
                        1.0f,
                        2.0f,
                        List.of(new Runway()),
                        new Weather()
                ));

        when(restTemplate.exchange(
                "apiUrl" + "KFFC",
                HttpMethod.GET,
                underTest.getHttpEntity(), Airport.class)).thenReturn(expectedResponse);

        ResponseEntity<Airport> actualResponse = underTest.findAirport("KFFC");
        verify(restTemplate).exchange(
                "apiUrl" + "KFFC",
                HttpMethod.GET,
                underTest.getHttpEntity(), Airport.class);

        assertThat(actualResponse).isEqualTo(expectedResponse);
        assertThat(Objects.requireNonNull(actualResponse.getBody()).getIcao()).isEqualTo("KFFC");
        assertThat(Objects.requireNonNull(actualResponse.getBody()).getIcao()).isNotEqualTo("KATL");
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
    void getUsername() {
        String actual = underTest.getUsername();
        assertThat(actual).isEqualTo("username");
    }

    @Test
    void getPassword() {
        String actual = underTest.getPassword();
        assertThat(actual).isEqualTo("password");
    }

    @Test
    void getApiUrl() {
        String actual = underTest.getApiUrl();
        assertThat(actual).isEqualTo("apiUrl");
    }
}
