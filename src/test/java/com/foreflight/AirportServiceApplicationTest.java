package com.foreflight;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AirportServiceApplicationTest {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    void contextLoads() {}

    @Test
    void restTemplateBeanIsAvailable() {
        assertThat(restTemplate).isNotNull();
    }
}
