package com.foreflight;

import com.foreflight.external.AirportAPI;
import com.foreflight.external.WeatherAPI;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;

@TestConfiguration
public class TestConfig {

    @MockBean private AirportAPI airportAPI; // External API
    @MockBean private WeatherAPI weatherAPI; // External API
}
