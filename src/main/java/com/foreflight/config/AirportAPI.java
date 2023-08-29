package com.foreflight.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

@Configuration
@Getter
public class AirportAPI {

    @Value("${airport-api.username}")
    private String username;

    @Value("${airport-api.password}")
    private String password;

    @Value("${airport-api.url}")
    private String apiUrl;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public HttpEntity<String> createAuthenticatedHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(username, password);
        headers.set("ff-coding-exercise", "1");
        return new HttpEntity<>(headers);
    }
}
