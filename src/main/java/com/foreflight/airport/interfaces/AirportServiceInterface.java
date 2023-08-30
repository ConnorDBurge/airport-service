package com.foreflight.airport.interfaces;

import com.foreflight.airport.AirportDTO;

import java.util.List;
import java.util.Optional;

public interface AirportServiceInterface {

    Optional<List<AirportDTO>> getAll(String indents);
}