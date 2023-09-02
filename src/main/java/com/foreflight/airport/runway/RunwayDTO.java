package com.foreflight.airport.runway;

import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RunwayDTO {

    private String ident;
    private String name;
    private Integer magneticHeading;
    private Double crossWindR;
    private Double headWind;
    private boolean bestRunway;

    public static RunwayDTO fromEntity(Runway runway) {
        return RunwayDTO.builder()
                .ident(runway.getIdent())
                .name(runway.getName())
                .magneticHeading(runway.getMagneticHeading())
                .crossWindR(runway.getCrossWindR())
                .headWind(runway.getHeadWind())
                .bestRunway(runway.isBestRunway())
                .build();
    }

    public static List<RunwayDTO> fromEntities(List<Runway> runway) {
        return runway.stream()
                .map(RunwayDTO::fromEntity)
                .collect(java.util.stream.Collectors.toList());
    }
}
