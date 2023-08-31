package com.foreflight.config;

import com.foreflight.airport.Airport;
import com.foreflight.exception.AirportNotFoundException;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Getter
@Configuration
public class AirportAPI {

    private final HttpEntity<String> httpEntity;
    private final RestTemplate restTemplate;
    private final String username;
    private final String password;
    private final String apiUrl;

    public AirportAPI(RestTemplate restTemplate,
                      @Value("${api.airport.username}") String username,
                      @Value("${api.airport.password}") String password,
                      @Value("${api.airport.url}") String apiUrl) {
        this.restTemplate = restTemplate;
        this.username = username;
        this.password = password;
        this.apiUrl = apiUrl;
        this.httpEntity = createAuthenticatedHttpEntity();
    }

    private HttpEntity<String> createAuthenticatedHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(this.username, this.password);
        headers.set("ff-coding-exercise", "1");
        return new HttpEntity<>(headers);
    }

    public ResponseEntity<Airport> findAirport(String identifier) {
        try {
            return restTemplate.exchange(
                    this.apiUrl + identifier,
                    HttpMethod.GET,
                    httpEntity, Airport.class);
        } catch (HttpClientErrorException ex) {
            throw new AirportNotFoundException("Airport data not found for identifier: " + identifier);
        }
    }
}
