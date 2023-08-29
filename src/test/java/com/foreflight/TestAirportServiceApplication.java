package com.foreflight;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestAirportServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(AirportServiceApplication::main).with(TestAirportServiceApplication.class).run(args);
    }

}
