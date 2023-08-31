package com.foreflight.airport;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/airports")
public class AirportController {

    private final AirportService airportService;

    @GetMapping("/{idents}")
    public ResponseEntity<List<AirportDTO>> getAirports(@PathVariable(name = "idents") String idents) {
        return ResponseEntity.ok(airportService.getAll(idents));
    }
}
