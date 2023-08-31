package com.foreflight.config;

import com.foreflight.airport.Airport;
import com.foreflight.exception.AirportNotFoundException;
import com.foreflight.exception.WeatherNotFoundException;
import com.foreflight.weather.Weather;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Configuration
@Getter
public class WeatherAPI {

    private final HttpEntity<String> httpEntity;
    private final RestTemplate restTemplate;
    private final String apiUrl;

    public WeatherAPI(RestTemplate restTemplate, @Value("${api.weather.url}") String apiUrl) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
        this.httpEntity = createAuthenticatedHttpEntity();
    }

    private HttpEntity<String> createAuthenticatedHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("ff-coding-exercise", "1");
        return new HttpEntity<>(headers);
    }

    public ResponseEntity<Weather> findWeather(String identifier) {
        try {
            return restTemplate.exchange(
                    this.apiUrl + identifier,
                    HttpMethod.GET,
                    httpEntity, Weather.class);
        } catch (HttpClientErrorException ex) {
            throw new WeatherNotFoundException("Weather data not found for identifier: " + identifier);
        }
    }
}
