package com.foreflight.airport.interfaces;

import com.foreflight.airport.AirportDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface AirportServiceInterface {

    List<AirportDTO> getAll(String indents);
}
