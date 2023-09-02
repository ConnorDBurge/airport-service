package com.foreflight.airport.runway;

import lombok.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RunwayDTO {

    private String ident;
    private String name;
    private Integer magneticHeading;
    private Double crossWind;
    private Double headWind;
    private boolean bestRunway;

    public static RunwayDTO fromEntity(Runway runway) {
        return Optional.ofNullable(runway)
                .map(r -> RunwayDTO.builder()
                        .ident(r.getIdent())
                        .name(r.getName())
                        .magneticHeading(r.getMagneticHeading())
                        .crossWind(r.getCrossWind())
                        .headWind(r.getHeadWind())
                        .bestRunway(r.isBestRunway())
                        .build())
                .orElseThrow(() -> new IllegalArgumentException("Runway cannot be null"));
    }

    public static List<RunwayDTO> fromEntities(List<Runway> runways) {
        return Optional.ofNullable(runways)
                .orElse(Collections.emptyList())
                .stream()
                .filter(Objects::nonNull)
                .map(RunwayDTO::fromEntity)
                .collect(java.util.stream.Collectors.toList());
    }
}
