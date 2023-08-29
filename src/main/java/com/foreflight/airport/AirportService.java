package com.foreflight.airport;

import com.foreflight.airport.interfaces.BaseAirportService;
import com.foreflight.config.AirportAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AirportService implements BaseAirportService {

    private final RestTemplate restTemplate;
    private final AirportAPI airportAPI;

    @Override
    public Optional<List<AirportDTO>> getAll(String indents) {
        HttpEntity<String> requestEntity = airportAPI.createAuthenticatedHttpEntity();
        ResponseEntity<Airport> responseEntity = restTemplate.exchange(
                airportAPI.getApiUrl() + indents,
                HttpMethod.GET,
                requestEntity, Airport.class);

        Airport airport = responseEntity.getBody();
        return Optional.of(AirportDTO.fromEntities(List.of(airport)));
    }
}
