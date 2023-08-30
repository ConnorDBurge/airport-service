package com.foreflight.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.reactive.server.WebTestClient;

@TestConfiguration
public class WebTestClientConfig {

    @Autowired
    private TestRestTemplate restTemplate;

    @Bean
    public WebTestClient webTestClient() {
        String baseUrl = restTemplate.getRootUri();
        return WebTestClient.bindToServer().baseUrl(baseUrl).build();
    }
}
