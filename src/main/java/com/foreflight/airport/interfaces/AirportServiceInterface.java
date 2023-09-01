package com.foreflight.airport.interfaces;

import com.foreflight.airport.AirportDTO;

import java.util.List;

public interface AirportServiceInterface {

    List<AirportDTO> getAll(String idents);
}
