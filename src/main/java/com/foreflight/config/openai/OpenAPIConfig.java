package com.foreflight.config.openai;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
public class OpenAPIConfig {

    @Bean
    @Profile({"local-postgres", "local-h2", "local"})
    public CommandLineRunner writeOpenApiSpecToFile(RestTemplate restTemplate) {
        return args -> {
            String openApiContent = restTemplate.getForObject("http://localhost:8080/v1", String.class);
            Files.write(Paths.get("src/main/resources/openapi/open-api.json"), openApiContent.getBytes());
            System.out.println("OpenAPI specification written to src/main/resources/openapi/openapi.json");
        };
    }

    @Bean
    public OpenAPI usersMicroserviceOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("ForeFlight Interview API")
                        .description("ForeFlight Interview Airport and Weather API")
                        .version("1.0"));
    }
}
