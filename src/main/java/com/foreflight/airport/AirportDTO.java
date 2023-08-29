package com.foreflight.airport;

import com.foreflight.airport.runway.RunwayDTO;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AirportDTO {

    private String ident;
    private String name;
    private Float latitude;
    private Float longitude;
    private List<RunwayDTO> runways;

    protected static AirportDTO fromEntity(@NonNull Airport airport) {
        return AirportDTO.builder()
                .ident(airport.getIcao())
                .name(airport.getName())
                .latitude(airport.getLatitude())
                .longitude(airport.getLongitude())
                .runways(RunwayDTO.fromEntities(airport.getRunways()))
                .build();
    }

    protected static List<AirportDTO> fromEntities(@NonNull List<Airport> airports) {
        return airports.stream()
                .map(AirportDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
