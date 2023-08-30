package com.foreflight.airport.runway;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RunwayDTO {

    private String ident;
    private String name;
    private String recipName;

    public static RunwayDTO fromEntity(Runway runway) {
        return RunwayDTO.builder()
                .ident(runway.getIdent())
                .name(runway.getName())
                .recipName(runway.getRecipName())
                .build();
    }

    public static List<RunwayDTO> fromEntities(List<Runway> runway) {
        return runway.stream()
                .map(RunwayDTO::fromEntity)
                .collect(java.util.stream.Collectors.toList());
    }
}
