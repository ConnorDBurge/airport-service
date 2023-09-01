package com.foreflight.config;

import com.foreflight.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

@Import(TestConfig.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OpenAPIControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testRedirectRootToSwaggerUi() {
        webTestClient.get().uri("/")
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().valueMatches("Location", ".*/swagger-ui/index.html$");
    }

    @Test
    void testRedirectVersionToSwaggerUi() {
        webTestClient.get().uri("/v1")
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().valueMatches("Location", ".*/swagger-ui/index.html$");
    }
}
